package Interface;

import Constants.Coin;
import Exceptions.*;

/**
 * It's represent customer interacting with the vending machine
 */
public interface CustomerAction {
    // "Users may deposit coins into the machine." -> Customer inserts a coin
    // User has to select item first
    void insertCoin(Coin coin) throws InvalidMachineStateException;

    // Customer selects item
    // throws exception if not found
    void selectItem(String code) throws ItemNotFoundException, InvalidMachineStateException, OutOfShelfException;

    // Customer may cancel their purchase and withdraw the money they have deposited.
    // Customer requests a refund, coins to be placed in return bucket
    void requestRefund();

    // Customer requests purchasing selected item
    // "Once enough money has been deposited, users may withdraw an item of their choice."
    // This has to check that userBalance with item that customer selected, enough or not
    // throws exception on error, puts item in return bucket
    void requestPurchaseItem() throws PurchasedException;

    // Customer requests change, coins to be placed in return bucket
    void requestChange() throws InsufficientSpareChangeCoinsException;

    // Customer collects refund, or item and change, from return bucket
    // Update states of the vending machine and return bucket
    void collect() throws InvalidMachineStateException;

    // Start the system
    void startOrReset() throws InvalidMachineStateException;
}
