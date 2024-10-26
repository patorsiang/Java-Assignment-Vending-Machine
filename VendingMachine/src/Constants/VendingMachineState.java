package Constants;

public enum VendingMachineState {
    IDLE, // Idle: initial
    READY, // when admin set up the system including item
    PURCHASING, // processing between selecting item and insert coin
    PURCHASED, // customer request to purchase and the balance and item price is equal
    PURCHASED_COMPLETED, // customer has change and collect the purchased item
    CANCELED // refund
}
