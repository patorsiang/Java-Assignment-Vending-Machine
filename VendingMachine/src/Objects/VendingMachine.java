package Objects;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.InvalidMachineStateException;
import Exceptions.LimitExceededException;
import Interface.AdminAction;

import java.math.BigDecimal;
import java.util.*;

public class VendingMachine implements AdminAction {
    private final int maxCapacity;
    private int remainingCapacity;
    private final List<VendingMachineState> state;
    private final Map<Item, Integer> shelf;
    private final Map<Coin, Integer> spareCoins;
    private BigDecimal machineBalance;

    public VendingMachine(int maxCapacity) {
        if (maxCapacity < 0) {
            throw new IllegalArgumentException("Max capacity must be a positive number");
        }

        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
        this.state = new ArrayList<>(List.of(VendingMachineState.IDLE));
        this.shelf = new HashMap<>();
        this.spareCoins = new EnumMap<>(Coin.class);
        this.machineBalance = BigDecimal.ZERO;
    }

    private VendingMachineState getState() {
        return state.getLast();
    }

    private void printState() {
        System.out.println("Vending Machine State");
        System.out.println("State: " + getState());
        System.out.println("Capacity: " + maxCapacity);
        System.out.println("Remaining Capacity: " + remainingCapacity);
        System.out.println("Shelf: ");
        printShelf();
        printBalance("Machine", machineBalance, spareCoins);
    }

    private void printShelf() {
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-4s | %-10s | %-5s | %-6s |%n", "Code", "Name", "Price", "Amount");
        System.out.printf("--------------------------------------%n");
        var items = shelf.keySet().stream().sorted(Comparator.comparing(Item::code)).toList();
        for (var item : items) {
            var itemAmount = shelf.get(item);
            System.out.printf("| %-4s | %-10s | %5.2f | %-6d |%n", item.code(), item.name(), item.price(), itemAmount);
        }
        System.out.printf("--------------------------------------%n");
    }

    private void printBalance(String label, BigDecimal balance, Map<Coin, Integer> coins) {
        System.out.printf("%s Balance: %.2f%n", label, balance);
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            System.out.printf("----------------------------%n");
            System.out.printf("| %-15s | %-6s |%n", "Coin", "Amount");
            System.out.printf("----------------------------%n");
            for (var entry : coins.entrySet()) {
                System.out.printf("| %-15s | %-6d |%n", entry.getKey(), entry.getValue());
            }
            System.out.printf("----------------------------%n");
        }
    }

    @Override
    public void addCoins(Coin coin, int amount) throws InvalidMachineStateException {
        System.out.println(">>> Admin Adding "+ amount + " " + coin + " coin"+ (amount > 1 ? "s" : ""));
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        if (getState() != VendingMachineState.IDLE) {
            throw new InvalidMachineStateException("This is not a time to add Coins, State should be Idle");
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
}
