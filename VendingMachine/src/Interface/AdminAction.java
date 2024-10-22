package Interface;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.InvalidMachineStateException;
import Exceptions.LimitExceededException;
import Objects.Item;

/**
 * It's represent owner interacting with the vending machine
 */
public interface AdminAction {
    // Add money to the machine
    // vending machine state has to be IDLE
    void addCoins(Coin coin, int amount) throws InvalidMachineStateException;

    // Take the money out of the machine
    // vending machine state has to be IDLE
    void withdrawCoins() throws InvalidMachineStateException;

    // Add an item in the machine
    // Attempting to add contents
    // to the machine exceeding the limits should signal an error.
    // vending machine state has to be IDLE
    void addItem(Item item, int amount) throws LimitExceededException, InvalidMachineStateException;

    // Set state of machine
    void setState(VendingMachineState state);
}
