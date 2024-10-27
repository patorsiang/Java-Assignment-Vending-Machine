package Interfaces;

import Constants.Coin;
import Exceptions.*;

/**
 * It represents the Customer interacting with the vending machine
 */
public interface CustomerAction {
    // "Users may deposit coins into the machine." -> Customer inserts a coin
    // User has to select item first
    void insertCoin(Coin coin) throws InvalidMachineStateException;

    //Customer selects item
    // throws an exception if not found
    void selectItem(String code) throws ItemNotFoundException, InvalidMachineStateException, OutOfShelfException;

    //Customer may cancel their purchase and withdraw their deposited money.
    //Customer requests a refund, coins to be placed in return bucket
    void requestRefund() throws InvalidMachineStateException;

    // Customer requests purchasing selected item
    // "Once enough money has been deposited, users may withdraw an item of their choice."
    // This has to check that userBalance with an item that the Customer selected, enough or not
    // throws an exception on an error and puts an item in the return bucket
    void requestPurchaseItem() throws PurchasedException, InsufficientSpareChangeCoinsException, InvalidMachineStateException;

    // Customer requests change, coins to be placed in return bucket
    void requestChange() throws InsufficientSpareChangeCoinsException, InvalidMachineStateException;

    //Customer collects refund, or item and change, from return bucket
    // Update states of the vending machine and return bucket
    void collect() throws InvalidMachineStateException;
}