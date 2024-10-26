package Objects;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.*;
import Interface.AdminAction;
import Interface.CustomerAction;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

public class VendingMachine implements AdminAction, CustomerAction {
    private final int maxCapacity;
    private int remainingCapacity;
    private final List<VendingMachineState> state;
    private final Map<Item, Integer> shelf;
    private final Map<Coin, Integer> spareCoins;
    private final Map<Coin, Integer> userCoins;
    private final Map<Coin, Integer> returnCoins;
    private BigDecimal machineBalance;
    private BigDecimal userBalance;
    private BigDecimal currentBalance;
    private Item selectedItem;
    private Item returnItem;

    public VendingMachine(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("Max capacity must be a positive number");
        }

        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
        this.state = new ArrayList<>(List.of(VendingMachineState.IDLE));
        this.shelf = new HashMap<>();
        this.spareCoins = new EnumMap<>(Coin.class);
        this.userCoins = new EnumMap<>(Coin.class);
        this.returnCoins = new EnumMap<>(Coin.class);
        this.machineBalance = BigDecimal.ZERO;
        this.userBalance = BigDecimal.ZERO;
        this.currentBalance = BigDecimal.ZERO;
        this.selectedItem = null;
        this.returnItem = null;
    }

    private VendingMachineState getState() {
        return state.getLast();
    }

    private void printState() {
        System.out.println("-----------------------------------------");
        System.out.println("Vending Machine State");
        System.out.println("State: " + getState());
        System.out.println("Capacity: " + maxCapacity);
        System.out.println("Remaining Capacity: " + remainingCapacity);
        System.out.println("Shelf: ");
        printShelf();
        printBalance("Machine", machineBalance, spareCoins);
    }

    private void printShelf() {
        System.out.println("--------------------------------------");
        System.out.printf("| %-4s | %-10s | %-5s | %-6s |%n", "Code", "Name", "Price", "Amount");
        System.out.println("--------------------------------------");
        var items = shelf.keySet().stream().sorted(Comparator.comparing(Item::code)).toList();
        for (var item : items) {
            var itemAmount = shelf.get(item);
            System.out.printf("| %-4s | %-10s | %5.2f | %-6d |%n", item.code(), item.name(), item.price(), itemAmount);
        }
        System.out.println("--------------------------------------");
    }

    private void printBalance(String label, BigDecimal balance, Map<Coin, Integer> coins) {
        System.out.printf("%s Balance: %.2f%n", label, balance);
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("----------------------------");
            System.out.printf("| %-15s | %-6s |%n", "Coin", "Amount");
            System.out.println("----------------------------");
            for (var entry : coins.entrySet()) {
                System.out.printf("| %-15s | %-6d |%n", entry.getKey(), entry.getValue());
            }
            System.out.println("----------------------------");
        }
    }

    @Override
    public void startOrReset() throws InvalidMachineStateException {
        if (getState() != VendingMachineState.IDLE && getState() != VendingMachineState.READY && getState() != VendingMachineState.PURCHASED_COMPLETED) {
            throw new InvalidMachineStateException("Can't start or reset now");
        }

        if (getState() != VendingMachineState.PURCHASED_COMPLETED) {
            System.out.println(">>> Admin Starting the system");
        }

        state.add(VendingMachineState.READY);
        userBalance = BigDecimal.ZERO;
        currentBalance = BigDecimal.ZERO;
        userCoins.clear();
        returnCoins.clear();

        System.out.println("-----------------------------------------");
        System.out.println("Welcome to the Vending Machine System");
        printCurrentState();
        printShelf();
    }

    private void printCurrentState() {
        System.out.println("-----------------------------------------");
        System.out.println("State: " + getState());
        if (selectedItem != null) {
            System.out.println("Selected item: " + selectedItem);
        }

        if (!returnCoins.isEmpty()) {
            System.out.println("clink-clink");
            printBalance("Return", currentBalance, returnCoins);
        } else {
            System.out.printf("Current balance: %.2f%n", currentBalance);
            printBalance("Customer", currentBalance, userCoins);
        }

    }

    @Override
    public void addCoins(Coin coin, int amount) throws InvalidMachineStateException {
        System.out.println(">>> Admin Adding " + amount + " " + coin + " coin" + (amount > 1 ? "s" : ""));
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + amount);
        machineBalance = machineBalance.add(coin.getValue().multiply(BigDecimal.valueOf(amount)));
        printState();
    }

    @Override
    public void withdrawCoins() throws InvalidMachineStateException {
        System.out.println(">>> Admin Withdrawing Coins");
        if (getState() != VendingMachineState.IDLE) {
            throw new InvalidMachineStateException("This is not a time to withdraw Coins, State should be Idle");
        }

        System.out.println("This is the amount that you withdrew");
        printBalance("Machine", machineBalance, spareCoins);

        machineBalance = BigDecimal.ZERO;
        spareCoins.clear();
    }

    @Override
    public void addItem(Item item, int amount) throws LimitExceededException, InvalidMachineStateException {
        System.out.println(">>> Admin Adding " + item + " to " + amount);
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        } else if (amount > remainingCapacity) {
            throw new LimitExceededException("Maximum capacity exceeded");
        }

        if (getState() != VendingMachineState.IDLE) {
            throw new InvalidMachineStateException("This is not a time to add Items, State should be Idle");
        }

        remainingCapacity -= amount;

        shelf.put(item, shelf.getOrDefault(item, 0) + amount);
        printState();
    }

    @Override
    public void breakToMaintenance() throws InvalidMachineStateException {
        System.out.println(">>> Admin Breaking out of maintenance");
        if (getState() != VendingMachineState.READY && getState() != VendingMachineState.IDLE) {
            throw new InvalidMachineStateException("This is not a time to break out of maintenance");
        }

        state.clear();
        state.add(VendingMachineState.IDLE);
        printState();
    }

    @Override
    public void insertCoin(@NotNull Coin coin) throws InvalidMachineStateException {
        System.out.println(">>> Customer inserting a " + coin + " coin");
        if (getState() != VendingMachineState.READY && getState() != VendingMachineState.COIN_INSERTED && getState() != VendingMachineState.ITEM_SELECTED) {
            throw new InvalidMachineStateException("This is not a time to insert Coins, State should be ready, coin inserted, or item selected");
        }
        state.add(VendingMachineState.COIN_INSERTED);
        userBalance = userBalance.add(coin.getValue());
        currentBalance = currentBalance.add(coin.getValue());
        userCoins.put(coin, userCoins.getOrDefault(coin, 0) + 1);
        printCurrentState();
    }

    @Override
    public void selectItem(String code) throws ItemNotFoundException, InvalidMachineStateException, OutOfShelfException {
        System.out.println(">>> Customer selecting item, " + code);
        if (getState() != VendingMachineState.READY && getState() != VendingMachineState.COIN_INSERTED && getState() != VendingMachineState.ITEM_SELECTED) {
            throw new InvalidMachineStateException("This is not a time to select item, State should be ready, coin inserted, or item selected");
        }

        var selectedItem = getItemByCode(code);

        if (selectedItem == null) {
            throw new ItemNotFoundException("Item not found");
        }

        if (shelf.get(selectedItem) < 1) {
            throw new OutOfShelfException("This item, " + selectedItem.code() + selectedItem.name() + ", is out of shelf.");
        }

        if (this.selectedItem != null) {
            currentBalance = currentBalance.add(this.selectedItem.price());
        }

        this.selectedItem = selectedItem;
        state.add(VendingMachineState.ITEM_SELECTED);
        currentBalance = currentBalance.subtract(selectedItem.price());
        printCurrentState();
    }

    private Item getItemByCode(String code) {
        return shelf.keySet().stream().filter(item -> item.code().equals(code)).findFirst().orElse(null);
    }

    @Override
    public void requestRefund() {

    }

    @Override
    public void requestPurchaseItem() throws PurchasedException {
        System.out.println(">>> Customer requesting purchase item");
        // Case: haven't selected item yet.
        if (selectedItem == null) {
            throw new PurchasedException("No item selected");
        }

        // Case: not enough money
        if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new PurchasedException("Not enough money");
        }

        // Case: Success
        state.add(VendingMachineState.PURCHASED);

        returnItem = selectedItem;
        selectedItem = null;

        shelf.put(returnItem, shelf.get(returnItem) -1 );

        for (var coin : userCoins.keySet()) {
            machineBalance = machineBalance.add(coin.getValue().multiply(BigDecimal.valueOf(userCoins.get(coin))));
            spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + userCoins.get(coin));
        }

        userCoins.clear();
        printCurrentState();

        if (returnItem != null) {
            System.out.println("Putting the item in return bucket......");
            System.out.println("clunk-clink");
            System.out.println("Return item: " + returnItem);
        }
    }

    @Override
    public void requestChange() throws InsufficientSpareChangeCoinsException {
        System.out.println(">>> Customer requesting changes");
        if (getState() != VendingMachineState.PURCHASED) {
            throw new InsufficientSpareChangeCoinsException("Can't request change yet");
        }

        if (currentBalance.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("No change");
        }
    }

    @Override
    public void collect() throws InvalidMachineStateException {
        System.out.println(">>> Customer collecting items and Change");
        state.add(VendingMachineState.PURCHASED_COMPLETED);
        startOrReset();
    }

    public void printError(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
