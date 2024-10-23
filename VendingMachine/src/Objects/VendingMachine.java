package Objects;

import Constants.*;
import Exceptions.*;
import Interface.*;

import java.util.*;

import static Services.VendingMachinePrintService.*;

public class VendingMachine implements AdminAction, CustomerAction {
    // maxCapacity: configurable in terms of the maximum number of items that it can hold
    // if it has been set up, these limits should not change
    private final int maxCapacity;
    private int remainCapacity;
    // a variety of items: Item and amount of it
    private final Map<Item, Integer> shelf;

    private final Map<Coin, Integer> spareChangeCoins;
    private final Map<Coin, Integer> insertedCoins;

    private double machineBalance;
    private double currentBalance;
    private double userBalance;
    private String selectedCode;
    private double currentPrice;

    private VendingMachineState currentState;  // Track the current state of the machine

    public VendingMachine(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Maximum capacity must be greater than 0");
        }
        this.maxCapacity = maxCapacity;
        this.remainCapacity = 0;
        this.shelf = new HashMap<>();
        this.spareChangeCoins = new EnumMap<>(Coin.class);
        this.insertedCoins = new EnumMap<>(Coin.class);
        this.machineBalance = 0.0;
        this.userBalance = 0.0;
        this.currentBalance = 0.0;
        this.selectedCode = null;
        this.currentState = VendingMachineState.IDLE;  // Initially the machine is idle
    }

    public void startOrReset() {
        userBalance = 0.0;
        currentBalance = 0.0;
        selectedCode = null;
        insertedCoins.clear();
        currentState = VendingMachineState.READY;
    }


    @Override
    public void addCoins(Coin coin, int amount) throws InvalidMachineStateException {
        if (currentState == VendingMachineState.IDLE) {
            spareChangeCoins.put(coin, spareChangeCoins.getOrDefault(coin, 0) + amount);
            machineBalance += coin.getValue() * amount;
        } else {
            throw new InvalidMachineStateException("This is not a time to add Coins, Should set state to Idle");
        }
    }

    @Override
    public void withdrawCoins() throws InvalidMachineStateException {
        if (currentState == VendingMachineState.IDLE) {
            machineBalance = 0.0;
            spareChangeCoins.clear();
            printAction("Admin withdraws coins.");
        } else {
            throw new InvalidMachineStateException("This is not a time to withdraw Coins, Should set state to Idle");
        }
    }

    @Override
    public void addItem(Item item, int amount) throws LimitExceededException, InvalidMachineStateException {
        if (currentState == VendingMachineState.IDLE) {
            remainCapacity += amount;
            if (remainCapacity > maxCapacity) {
                throw new LimitExceededException("Cannot add items more than Machine Capacity, not more than " + maxCapacity);
            }
            shelf.put(item, shelf.getOrDefault(item, 0) + amount);
        } else {
            throw new InvalidMachineStateException("This is not a time to add Items, Should set state to Idle");
        }
    }

    @Override
    public void breakToMaintenance() throws InvalidMachineStateException {
        if (currentState == VendingMachineState.READY || currentState == VendingMachineState.IDLE) {
            currentState = VendingMachineState.IDLE;
        } else {
            throw new InvalidMachineStateException("It's not ready to maintain because it is in the time that the customer is using");
        }
    }

    @Override
    public void insertCoin(Coin coin) {
        currentState = VendingMachineState.COIN_INSERTED;
        userBalance += coin.getValue();
        currentBalance += coin.getValue();
        insertedCoins.put(coin, insertedCoins.getOrDefault(coin, 0) + 1);
        printAction("Customer inserted a " + coin + " coin.");
        printInfo();
    }

    @Override
    public double getCurrentBalance() {
        return 0;
    }

    @Override
    public void selectItem(String code) throws ItemNotFoundException {
        printAction("Customer selected " + code);
        var itemFound = false;
        for (Item item : shelf.keySet()) {
            if (item.code().equals(code)) {
                itemFound = true;
                if (selectedCode == null || selectedCode.isEmpty()) {
                    currentBalance += currentPrice - item.price();
                } else {
                    currentBalance -= item.price();
                }
                selectedCode = code;
                currentPrice = item.price();
                currentState = VendingMachineState.ITEM_SELECTED;
                break;
            }
        }
        printInfo();
        if (!itemFound) {
            throw new ItemNotFoundException("Item not found");
        }
    }

    @Override
    public String getItemCode() {
        return "";
    }

    @Override
    public void requestRefund() {

    }

    @Override
    public void requestPurchaseItem() throws InsufficientHoldedCoinsException {

    }

    @Override
    public void requestChange() throws InsufficientSpareChangeCoinsException {

    }

    @Override
    public void collect() {

    }

    public Map<Item, Integer> getShelf() {
        return shelf;
    }

    public double getMachineBalance() {
        return machineBalance;
    }

    public Map<Coin, Integer> getSpareChangeCoins() {
        return spareChangeCoins;
    }

    public VendingMachineState getCurrentState() {
        return currentState;
    }

    public void printInfo() {
        System.out.println("--------------------------------------");
        System.out.println("User Balance: " + userBalance);
        System.out.println("Current Balance: " + currentBalance);
        System.out.println("Selected Code: " + selectedCode);
        System.out.println("--------------------------------------");
    }
}