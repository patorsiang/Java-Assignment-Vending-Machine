package Services;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.InvalidMachineStateException;
import Objects.Item;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

public abstract class VendingMachineService {

    protected boolean isAdmin;
    protected final int maxCapacity;
    protected int remainingCapacity;
    protected VendingMachineState state;
    protected final Map<Item, Integer> shelf;
    protected final Map<Coin, Integer> spareCoins;
    protected final Map<Coin, Integer> customerCoins;
    protected final Map<Coin, Integer> returnCoins;
    protected BigDecimal machineBalance;
    protected BigDecimal customerBalance;
    protected BigDecimal currentBalance;
    protected Item selectedItem;
    protected Item returnItem;

    protected VendingMachineService(int maxCapacity) {
        System.out.println("Initializing: Vending Machine with max " + maxCapacity + " items capacity");

        if (maxCapacity < 0) {
            throw new IllegalArgumentException("Max capacity must be a positive number");
        }

        this.isAdmin = false;
        this.maxCapacity = maxCapacity;
        this.remainingCapacity = maxCapacity;
        this.state = VendingMachineState.IDLE;
        this.shelf = new HashMap<>();
        this.spareCoins = new EnumMap<>(Coin.class);
        this.customerCoins = new EnumMap<>(Coin.class);
        this.returnCoins = new EnumMap<>(Coin.class);
        this.machineBalance = BigDecimal.ZERO;
        this.customerBalance = BigDecimal.ZERO;
        this.currentBalance = BigDecimal.ZERO;
        this.selectedItem = null;
        this.returnItem = null;
    }

    public void setAdminRole() {
        setIsAdmin(true);
    }

    public void setCustomerRole() {
        setIsAdmin(false);
    }

    private void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    protected void printShelf() {
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

    protected void printBalance(String label, BigDecimal balance, @NotNull Map<Coin, Integer> coins) {
        System.out.printf("%s Balance: %.2f%n", label, balance);
        if (!coins.isEmpty()) {
            System.out.println("----------------------------");
            System.out.printf("| %-15s | %-6s |%n", "Coin", "Amount");
            System.out.println("----------------------------");
            for (var entry : coins.entrySet()) {
                System.out.printf("| %-15s | %-6d |%n", entry.getKey(), entry.getValue());
            }
            System.out.println("----------------------------");
        }
    }

    protected void resetProcess() throws InvalidMachineStateException {
        if (!List.of(VendingMachineState.IDLE, VendingMachineState.READY, VendingMachineState.PURCHASED_COMPLETED).contains(state) && currentBalance.compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidMachineStateException("Can't start or reset now");
        }

        if (state == VendingMachineState.IDLE) {
            System.out.println(">>> Admin Starting the system");
        }

        state = VendingMachineState.READY;
        customerBalance = BigDecimal.ZERO;
        currentBalance = BigDecimal.ZERO;
        customerCoins.clear();
        returnCoins.clear();
        selectedItem = null;
        returnItem = null;

        System.out.println("-----------------------------------------");
        System.out.println("Welcome to the Vending Machine System");
        printCurrentState();
        printShelf();
    }

    protected void printCurrentState() {
        System.out.println("-----------------------------------------");
        System.out.println("State: " + state);
        if (selectedItem != null) {
            System.out.println("Selected item: " + selectedItem);
        }

        if (returnCoins.isEmpty()) {
            System.out.printf("Current balance: %.2f%n", currentBalance);
            printBalance("Customer", currentBalance, customerCoins);
        }
    }

    protected Item getItemByCode(String code) {
        return shelf.keySet().stream().filter(item -> item.code().equals(code)).findFirst().orElse(null);
    }

    protected void printState() {
        System.out.println("-----------------------------------------");
        System.out.println("Vending Machine State");
        System.out.println("State: " + state);
        System.out.println("Capacity: " + maxCapacity);
        System.out.println("Remaining Capacity: " + remainingCapacity);
        System.out.println("Shelf: ");
        printShelf();
        printBalance("Machine", machineBalance, spareCoins);
    }
}
