package Objects;

import Constants.*;

import java.util.*;

public class VendingMachine {
    // maxCapacity: configurable in terms of the maximum number of items that it can hold
    // if it has been set up, these limits should not change
    private final int maxCapacity;

    // a variety of items: Item and amount of it
    private final Map<Item, Integer> shelf;

    private final Map<Coin, Integer> spareChangeCoins;
    private final Map<Coin, Integer> insertedCoins;

    private double ownerBalance;
    private double userBalance;
    private String selectedCode;

    private VendingMachineState currentState;  // Track the current state of the machine

    public VendingMachine(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Maximum capacity must be greater than 0");
        }
        this.maxCapacity = maxCapacity;
        this.shelf = new HashMap<>();
        this.spareChangeCoins = new EnumMap<>(Coin.class);
        this.insertedCoins = new EnumMap<>(Coin.class);
        this.ownerBalance = 0.0;
        this.userBalance = 0.0;
        this.selectedCode = null;
        this.currentState = VendingMachineState.IDLE;  // Initially the machine is idle
    }

    public void reset () {
        userBalance = 0.0;
        selectedCode = null;
        insertedCoins.clear();
        currentState = VendingMachineState.READY;
    }
}