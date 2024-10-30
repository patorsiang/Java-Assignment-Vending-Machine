package Tests;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.*;
import Objects.VendingMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static Constants.Constants.*;
import static Tests.VendingMachineTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineCustomerPartTest {

    private VendingMachine vm;

    @BeforeEach
    public void setUp() throws LimitExceededException, InvalidMachineStateException {
        vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);
        vm.setAdminRole();
        for (var item: itemStock.entrySet()) {
            vm.addItem(item.getKey(), item.getValue());
        }
        vm.startOrReset();
    }

    @Test
    public void testFailureCaseInsertCoin() throws InvalidMachineStateException {
        // insert coin when the machine is break for maintaining
        vm.setAdminRole();
        vm.breakToMaintenance();
        vm.setCustomerRole();
        assertThrows(InvalidMachineStateException.class, () -> vm.insertCoin(Coin.TEN_PENCE));
        // insert coin when customer cancel but not collect the return coins
        vm.setAdminRole();
        vm.startOrReset();
        vm.setCustomerRole();
        vm.insertCoin(Coin.TEN_PENCE);
        vm.requestRefund();
        assertThrows(InvalidMachineStateException.class, () -> vm.insertCoin(Coin.TEN_PENCE));
    }

    @Test
    public void testSuccessCaseInsertCoin() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException {
        vm.insertCoin(Coin.TWO_POUNDS);
        var state = getState(vm);
        assertEquals(VendingMachineState.PURCHASING, state);
        var customerBalance = getCustomerBalance(vm);
        assertEquals(BigDecimal.valueOf(2.0), customerBalance);
        var currentBalance = getCurrentBalance(vm);
        assertEquals(BigDecimal.valueOf(2.0), currentBalance);
        var customerCoins = getCustomerCoins(vm);
        assertEquals(Map.of(Coin.TWO_POUNDS, 1), customerCoins);
    }

    @Test
    public void testFailureCasesSelectItem() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException, LimitExceededException {
        // select an Item when the machine is break for maintaining
        vm.setAdminRole();
        vm.breakToMaintenance();
        assertThrows(InvalidMachineStateException.class, () -> vm.selectItem("07"));
        vm.setAdminRole();
        vm.addItem(coffee, 0);
        vm.startOrReset();
        // select an Item that is not in the machine
        assertThrows(ItemNotFoundException.class, () -> vm.selectItem("07"));
        // select an Item that out of stock
        assertThrows(OutOfShelfException.class, () -> vm.selectItem("04"));
        // select an Item when customer cancel but not collect the return coins
        vm.insertCoin(Coin.TEN_PENCE);
        vm.requestRefund();
        assertThrows(InvalidMachineStateException.class, () -> vm.selectItem("07"));
    }

    @Test
    public void testSuccessCasesSelectItem() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException, OutOfShelfException, ItemNotFoundException {
        // select first time
        vm.selectItem("01");
        var currentBalance = getCurrentBalance(vm);
        assertEquals(BigDecimal.ZERO.subtract(coke.price()), currentBalance);
        var state = getState(vm);
        assertEquals(VendingMachineState.PURCHASING, state);
        var selectedItem = getSelectedItem(vm);
        assertEquals(coke, selectedItem);
        // select the second time, change the selected item
        vm.selectItem("02");
        currentBalance = getCurrentBalance(vm);
        assertEquals(BigDecimal.ZERO.subtract(water.price()).setScale(2, RoundingMode.FLOOR), currentBalance);
        state = getState(vm);
        assertEquals(VendingMachineState.PURCHASING, state);
        selectedItem = getSelectedItem(vm);
        assertEquals(water, selectedItem);
    }

    @Test
    public void testRequestRefund() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException, OutOfShelfException, ItemNotFoundException, InsufficientSpareChangeCoinsException, PurchasedException {
        // State is Ready
        assertThrows(InvalidMachineStateException.class, () -> vm.requestRefund());
        // State is Cancel
        vm.insertCoin(Coin.TWO_POUNDS);
        vm.requestRefund();
        var returnCoin = getReturnCoins(vm);
        assertEquals(Map.of(Coin.TWO_POUNDS, 1), returnCoin);
        var customerCoins = getCustomerCoins(vm);
        assertTrue(customerCoins.isEmpty());
        var state = getState(vm);
        assertEquals(VendingMachineState.CANCELED, state);
        var currentBalance = getCurrentBalance(vm);
        assertEquals(BigDecimal.ZERO, currentBalance);
        assertThrows(InvalidMachineStateException.class, () -> vm.requestRefund());
        vm.collect();
        // State is Purchasing, but not insert coin yet
        vm.selectItem("01");
        vm.requestRefund();
        returnCoin = getReturnCoins(vm);
        assertTrue(returnCoin.isEmpty());
        customerCoins = getCustomerCoins(vm);
        assertTrue(customerCoins.isEmpty());
        state = getState(vm);
        assertEquals(VendingMachineState.READY, state);
        currentBalance = getCurrentBalance(vm);
        assertEquals(BigDecimal.ZERO, currentBalance);
        // State is Purchased
        vm.selectItem("02");
        vm.insertCoin(Coin.ONE_POUND);
        vm.requestPurchaseItem();
        assertThrows(InvalidMachineStateException.class, () -> vm.requestRefund());
        vm.collect();
        // State is IDLE
        vm.setAdminRole();
        vm.breakToMaintenance();
        vm.setCustomerRole();
        assertThrows(InvalidMachineStateException.class, () -> vm.requestRefund());
    }

    @Test
    public void testPurchaseItem() throws InvalidMachineStateException, OutOfShelfException, ItemNotFoundException, InsufficientSpareChangeCoinsException, PurchasedException, NoSuchFieldException, IllegalAccessException {
        // State is Ready
        assertThrows(InvalidMachineStateException.class, () -> vm.requestPurchaseItem());
        // Haven't select an Item yet
        vm.insertCoin(Coin.TEN_PENCE);
        assertThrows(PurchasedException.class, () -> vm.requestPurchaseItem());
        vm.selectItem("02");
        assertThrows(PurchasedException.class, () -> vm.requestPurchaseItem());
        // Enough Money to purchase
        vm.insertCoin(Coin.FIFTY_PENCE);
        vm.insertCoin(Coin.TEN_PENCE);
        vm.insertCoin(Coin.TEN_PENCE);
        vm.insertCoin(Coin.TEN_PENCE);
        vm.insertCoin(Coin.TEN_PENCE);
        vm.insertCoin(Coin.TEN_PENCE);
        vm.requestPurchaseItem();
        var state = getState(vm);
        assertEquals(VendingMachineState.PURCHASED, state);
        var returnItem = getReturnItem(vm);
        assertEquals(water, returnItem);
        var selectedItem = getSelectedItem(vm);
        assertNull(selectedItem);
        var shelf = getShelf(vm);
        assertEquals(itemStock.get(water)-1, shelf.get(water));
        var remainingCapacity = getRemainingCapacity(vm);
        assertEquals(1, remainingCapacity);
        var customerCoins = getCustomerCoins(vm);
        assertTrue(customerCoins.isEmpty());
        vm.collect();
        // Have to return change but no spare coins
        vm.selectItem("01");
        vm.insertCoin(Coin.TWO_POUNDS);
        assertThrows(InsufficientSpareChangeCoinsException.class, () -> vm.requestPurchaseItem());
        state = getState(vm);
        assertEquals(VendingMachineState.PURCHASED, state);
        returnItem = getReturnItem(vm);
        assertEquals(coke, returnItem);
        selectedItem = getSelectedItem(vm);
        assertNull(selectedItem);
        shelf = getShelf(vm);
        assertEquals(itemStock.get(coke)-1, shelf.get(coke));
        remainingCapacity = getRemainingCapacity(vm);
        assertEquals(2, remainingCapacity);
        customerCoins = getCustomerCoins(vm);
        assertTrue(customerCoins.isEmpty());
    }

    @Test
    public void testRequestChange() throws InvalidMachineStateException, OutOfShelfException, ItemNotFoundException, InsufficientSpareChangeCoinsException, PurchasedException, NoSuchFieldException, IllegalAccessException {
        // State is Ready
        assertThrows(InvalidMachineStateException.class, () -> vm.requestChange());
        // have to return change
        vm.setAdminRole();
        vm.addCoins(Coin.FIFTY_PENCE, 1);
        vm.addCoins(Coin.TWENTY_PENCE, 1);
        vm.addCoins(Coin.FIVE_PENCE, 1);
        vm.setCustomerRole();
        vm.selectItem("01");
        vm.insertCoin(Coin.TWO_POUNDS);
        vm.requestPurchaseItem();
        var currentBalance = getCurrentBalance(vm);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR), currentBalance);
        var returnCoins = getReturnCoins(vm);
        assertEquals(Map.of(Coin.FIFTY_PENCE, 1, Coin.TWENTY_PENCE, 1, Coin.FIVE_PENCE, 1), returnCoins);
        var spareCoins = getSpareCoins(vm);
        assertEquals(Map.of(Coin.TWO_POUNDS, 1, Coin.FIFTY_PENCE, 0, Coin.TWENTY_PENCE, 0, Coin.FIVE_PENCE, 0), spareCoins);
    }

    @Test
    public void testCollect() throws OutOfShelfException, InvalidMachineStateException, ItemNotFoundException {
        assertThrows(InvalidMachineStateException.class, () -> vm.collect());
        // Have to return change but no spare coins
        vm.selectItem("01");
        vm.insertCoin(Coin.TWO_POUNDS);
        assertThrows(InsufficientSpareChangeCoinsException.class, () -> vm.requestPurchaseItem());
        assertThrows(InvalidMachineStateException.class, () -> vm.collect());
    }
}
