package Constants;

/**
 *
 */
public enum VendingMachineState {
    IDLE, // Idle: initial
    READY, // when admin setup the system including item, SparedChange
    COIN_INSERTED, // User insert coin
    ITEM_SELECTED, // User select item code
    OUT_OF_STOCK, // the item that user select is out of stock
    PURCHASED, // user request to purchase and the balance and item price is equal
    PURCHASED_COMPLETED, // user has change and collect the purchased item
    CANCELED // refund
}
