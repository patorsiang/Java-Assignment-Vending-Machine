package Tests;

import Constants.Coin;
import Exceptions.*;
import Objects.Item;
import Objects.VendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    private VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine(10); // Initialize with a max capacity of 10
        vendingMachine.setAdminRole();
    }

    @Test
    void testResetPermission() {
        vendingMachine.setCustomerRole();
        assertThrows(SecurityException.class, () -> vendingMachine.startOrReset(), "Should be admin to reset");
    }

    @Test
    void testAddCoinsSuccessfully() throws InvalidMachineStateException {
        assertDoesNotThrow(() -> vendingMachine.addCoins(Coin.ONE_POUND, 5),
                "Adding coins should not throw an exception in READY state");
    }

    @Test
    void testAddCoinsWithNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.addCoins(Coin.ONE_POUND, -5),
                "Adding negative amount of coins should throw IllegalArgumentException");
    }

    @Test
    void testWithdrawCoinsInIdleState() {
        assertDoesNotThrow(vendingMachine::withdrawCoins, "Should be able to withdraw coins in IDLE state");
    }

    @Test
    void testWithdrawCoinsWhenNotIdleThrowsException() throws InvalidMachineStateException {
        vendingMachine.startOrReset();
        assertThrows(InvalidMachineStateException.class, vendingMachine::withdrawCoins,
                "Withdrawing coins when not in IDLE state should throw InvalidMachineStateException");
    }

    @Test
    void testAddItemSuccessfully() {
        assertDoesNotThrow(() -> vendingMachine.addItem(coke, 3));
    }

    @Test
    void testAddDuplicateItemThrowsException() throws LimitExceededException, InvalidMachineStateException {
        Item item = new Item("01", "Water", BigDecimal.valueOf(1.25));
        vendingMachine.addItem(item, 3);
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.addItem(item, 2),
                "Adding duplicate item code should throw IllegalArgumentException");
    }

    @Test
    void testAddItemWithNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.addItem(coke, -12),
                "Adding negative amount of item should throw IllegalArgumentException");
    }

    @Test
    void testAddItemReachOverCapacityThrowsException()  {
        assertThrows(LimitExceededException.class, () -> vendingMachine.addItem(coke, 11),
                "Adding item over Capacity should throw LimitExceededException");
    }

    @Test
    void testStartSuccessfully() throws InvalidMachineStateException  {
       assertDoesNotThrow(() -> vendingMachine.startOrReset(), "Should be admin to start or reset");
    }

    @Test
    void testResetThrowsException() throws InvalidMachineStateException {
        vendingMachine.startOrReset();
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        assertThrows(InvalidMachineStateException.class, () -> vendingMachine.startOrReset(), "Should be admin to reset");
    }

    @Test
    void testInsertCoinWhenNotInReadyOrPurchasingThrowsException() {
        assertThrows(InvalidMachineStateException.class, () -> vendingMachine.insertCoin(Coin.ONE_POUND),
                "Inserting coin when not in READY or PURCHASING state should throw InvalidMachineStateException");
    }

    @Test
    void testSelectItemWhenNotReadyThrowsException() {
        assertThrows(InvalidMachineStateException.class, () -> vendingMachine.selectItem("01"),
                "Selecting item when not in READY state should throw InvalidMachineStateException");
    }

    @Test
    void testRequestRefundWhenNotPurchasingThrowsException() {
        assertThrows(InvalidMachineStateException.class, vendingMachine::requestRefund,
                "Requesting refund when not in PURCHASING state should throw InvalidMachineStateException");
    }

    @Test
    void testCollectWhenNotPurchasedOrCanceledThrowsException() {
        assertThrows(InvalidMachineStateException.class, vendingMachine::collect,
                "Collecting when not in PURCHASED or CANCELED state should throw InvalidMachineStateException");
    }

    @Test
    void testBreakToMaintenanceSuccessfully() {
        assertDoesNotThrow(vendingMachine::breakToMaintenance, "Breaking to maintenance");
    }

    @Test
    void testBreakToMaintenanceThrowsException() throws InvalidMachineStateException {
        vendingMachine.startOrReset();
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        assertThrows(InvalidMachineStateException.class, () -> vendingMachine.breakToMaintenance(), "Breaking to maintenance when not in PURCHASED or CANCELED state should throw InvalidMachineStateException");
    }

    @Test
    void testSuccessfulPurchaseFlow() throws InvalidMachineStateException, LimitExceededException, OutOfShelfException, PurchasedException, InsufficientSpareChangeCoinsException, ItemNotFoundException {
        // Admin adds items
        vendingMachine.addItem(coke, 5);

        // Start the system
        vendingMachine.startOrReset();

        // Customer selects item and inserts coin
        vendingMachine.insertCoin(Coin.ONE_POUND);
        vendingMachine.insertCoin(Coin.TWENTY_PENCE);
        vendingMachine.insertCoin(Coin.FIVE_PENCE);
        vendingMachine.selectItem(coke.code());

        // Complete the purchase
        vendingMachine.requestPurchaseItem();
        vendingMachine.collect();
    }

    @Test
    void testSuccessfulPurchaseFlowWithChange() throws InvalidMachineStateException, LimitExceededException, OutOfShelfException, PurchasedException, InsufficientSpareChangeCoinsException, ItemNotFoundException {
        // Admin adds items
        vendingMachine.addItem(milk, 5);
        vendingMachine.addCoins(Coin.FIFTY_PENCE, 1);
        // Start the system
        vendingMachine.startOrReset();

        // Customer selects item and inserts coin
        vendingMachine.insertCoin(Coin.TWO_POUNDS);
        vendingMachine.selectItem(milk.code());

        // Complete the purchase
        vendingMachine.requestPurchaseItem();
        vendingMachine.collect();
    }

    @Test
    void TestSuccessfulRefund() throws InvalidMachineStateException {
        vendingMachine.startOrReset();
        vendingMachine.insertCoin(Coin.FIFTY_PENCE);
        vendingMachine.requestRefund();
        vendingMachine.collect();
    }
    
}
