package Services;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.InvalidMachineStateException;
import Objects.Item;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;

/**
 * VendingMachineService is the utility tool that VendingMachine can use
 * providing shared functionality and common attributes
 */
public class VendingMachineService {

    protected boolean isAdmin; // represent admin role or not
    protected final int maxCapacity; // max capacity of the Vending Machine
    protected int remainingCapacity; // remaining capacity minus the item amount that is already stored in the machine
    protected VendingMachineState state; // state of machine
    protected final Map<Item, Integer> shelf; // like a shelf to show the item
    protected final Map<String, Item> codeToItemMap; // map with code and item
    protected final Map<Coin, Integer> spareCoins; // spare coin for returning change to a customer
    protected final Map<Coin, Integer> customerCoins; // coins of customer which inserted into machine
    protected final Map<Coin, Integer> returnCoins; // the space to note the change or refund to a customer
    protected BigDecimal machineBalance; // machine balance for display
    protected BigDecimal customerBalance; // total value that Customer inserted a coin
    protected BigDecimal currentBalance; // the total money that the Customer has in the machine, excluding the price of an item that the Customer selected
    protected Item selectedItem; // store the item that Customer selected
    protected Item returnItem; // store the item that Customer bought
    protected static final String unavailableMsg = "The Vending Machine is unavailable. Try again later.";
    /**
     * Construct VendingMachineService
     *
     * @param maxCapacity capacity limit capacity of vending machine
     */
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
        this.codeToItemMap = new HashMap<>();
        this.spareCoins = new EnumMap<>(Coin.class);
        this.customerCoins = new EnumMap<>(Coin.class);
        this.returnCoins = new EnumMap<>(Coin.class);
        this.machineBalance = BigDecimal.ZERO;
        this.customerBalance = BigDecimal.ZERO;
        this.currentBalance = BigDecimal.ZERO;
        this.selectedItem = null;
        this.returnItem = null;
    }

    /**
     * For the Customer to set its role
     */
    public void setAdminRole() {
        setIsAdmin(true);
    }

    /**
     * For the Customer to set its role
     */
    public void setCustomerRole() {
        setIsAdmin(false);
    }

    /**
     * manipulate the isAdmin attribute
     * @param isAdmin represents the admin role
     */
    private void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * show the shelf, list of items in the machine
     */
    protected void printShelf() {
        System.out.println("--------------------------------------");
        System.out.printf("| %-4s | %-10s | %-5s | %-6s |%n", "Code", "Name", "Price", "Amount");
        System.out.println("--------------------------------------");
        var items = shelf.keySet().stream().sorted(Comparator.comparing(Item::code)).toList();
        for (var item: items) {
            var itemAmount = shelf.get(item);
            System.out.printf("| %-4s | %-10s | %5.2f | %-6d |%n", item.code(), item.name(), item.price(), itemAmount);
        }
        System.out.println("--------------------------------------");
    }

    /**
     * the pattern to show any balance such as machine balance, customer current balance, customer balance
     *
     * @param label the title
     * @param balance the balance value
     * @param coins the list of coins
     */
    protected void printBalance(String label, BigDecimal balance, @NotNull Map<Coin, Integer> coins) {
        System.out.printf("%s Balance: %.2f%n", label, balance);
        if (!coins.isEmpty()) {
            System.out.println("----------------------------");
            System.out.printf("| %-15s | %-6s |%n", "Coin", "Amount");
            System.out.println("----------------------------");
            for (var entry: coins.entrySet()) {
                System.out.printf("| %-15s | %-6d |%n", entry.getKey(), entry.getValue());
            }
            System.out.println("----------------------------");
        }
    }

    /**
     * reset process to set state, customerBalance, currentBalance, customerCoins, returnCoins, selectedItem, returnItem to the state that is ready to continue selling the items
     *
     * @throws InvalidMachineStateException if some condition is not finished
     */
    protected void resetProcess() throws InvalidMachineStateException {
        if (!List.of(VendingMachineState.IDLE, VendingMachineState.READY, VendingMachineState.PURCHASED_COMPLETED).contains(state) || currentBalance.compareTo(BigDecimal.ZERO) != 0) {
            throw new InvalidMachineStateException("Can't start or reset now");
        }

        state = VendingMachineState.READY;
        customerBalance = BigDecimal.ZERO;
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
        //Print out the state
        System.out.println("State: " + state);

        //Print out the selected item
        if (selectedItem != null || !state.equals(VendingMachineState.PURCHASED)) {
            System.out.println("Selected item: " + selectedItem);
        }

        // print the Customer's current balance
        if (returnCoins.isEmpty()) {
            System.out.printf("Current balance: %.2f%n", currentBalance);
            printBalance("Customer", currentBalance, customerCoins);
        }
    }

    /**
     * Print state for Admin or Back end to look when breaking to maintenance
     */
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

    /**
     * check permission
     *
     * @param key the key of action
     */
    protected void checkPermission(String key) {
        // check permission; user should be admin; if not
        if (!isAdmin) {
            // throw SecurityException
            throw new SecurityException("Unauthorized access: Only admin can " + key);
        }
    }

    /**
     * function for check state is in the state that cannot do anything or not
     *
     * @param warningMsg     the message for a specific purpose
     * @param shouldBeStates the should-be states
     * @throws InvalidMachineStateException if it is not in the should-be states, throw an error
     */
    protected void checkState(String warningMsg, VendingMachineState... shouldBeStates) throws InvalidMachineStateException {
        // check state should be in shouldBeStates. If not, return an error
        if (!List.of(shouldBeStates).contains(state)) {
            // throw InvalidMachineStateException
            throw new InvalidMachineStateException(warningMsg);
        }
    }

}
