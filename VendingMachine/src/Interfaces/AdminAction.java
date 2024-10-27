package Interfaces;

import Constants.Coin;
import Exceptions.InvalidMachineStateException;
import Exceptions.LimitExceededException;
import Objects.Item;

/**
 * It represents the owner managing the vending machine
 */
public interface AdminAction {
    // Add money to the machine
    // vending machine state has to be IDLE
    void addCoins(Coin coin, int amount);

    // Take the money out of the machine
    // vending machine state has to be IDLE
    void withdrawCoins() throws InvalidMachineStateException;

    // Add an item to the machine
    // Attempting to add contents
    // to the machine exceeding the limits should signal an error.
    // vending machine state has to be IDLE
    void addItem(Item item, int amount) throws LimitExceededException, InvalidMachineStateException;

    // Start the system
    void startOrReset() throws InvalidMachineStateException;

    // Set the state of the machine to idle
    void breakToMaintenance() throws InvalidMachineStateException;
}