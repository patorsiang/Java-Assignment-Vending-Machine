package Interface;

import Constants.Coin;
import Exceptions.LimitExceededException;
import Objects.Item;

import java.util.Map;

/**
 * It's represent owner interacting with the vending machine
 */
public interface AdminAction {
    // Add money to the machine
    void addCoins(Coin coin, int amount);

    // Take the money out of the machine
    void withdrawCoins();

    // Add an item in the machine
    // Attempting to add contents
    // to the machine exceeding the limits should signal an error.
    void addItem(Item item, int amount) throws LimitExceededException;
}
