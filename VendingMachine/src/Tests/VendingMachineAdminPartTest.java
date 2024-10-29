package Tests;

import Constants.Coin;
import Constants.VendingMachineState;
import Exceptions.*;
import Objects.Item;
import Objects.VendingMachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static Constants.Constants.*;
import static Tests.VendingMachineTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineAdminPartTest {

    private VendingMachine vm;

    @BeforeEach
    public void setUp() {
        vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);
    }

    @Test
    public void testInvalidPermissionAddCoin() {
        assertThrows(SecurityException.class, () -> vm.addCoins(Coin.TEN_PENCE, 1));
    }

    @Test
    public void testNegativeAmountAddCoin() {
        vm.setAdminRole();
        assertThrows(IllegalArgumentException.class, () -> vm.addCoins(Coin.TEN_PENCE, -1));
    }

    @Test
    public void testSuccessCaseAddCoin() throws NoSuchFieldException, IllegalAccessException {
        vm.setAdminRole();
        vm.addCoins(Coin.TEN_PENCE, 1);
        // Access the private field "spareCoins" in the VendingMachine class
        var spareCoinsField = getSpareCoins(vm);
        assertEquals(1, spareCoinsField.get(Coin.TEN_PENCE));
        var machineBalanceField = getMachineBalance(vm);
        assertEquals(BigDecimal.valueOf(0.10), machineBalanceField);
    }

    @Test
    public void testInvalidPermissionWithdrawCoin() {
        assertThrows(SecurityException.class, () -> vm.withdrawCoins());
    }

    @Test
    public void testStateIsNotIdleWithdrawCoin() throws InvalidMachineStateException {
        vm.setAdminRole();
        vm.startOrReset();
        assertThrows(InvalidMachineStateException.class, () -> vm.withdrawCoins());
    }

    @Test
    public void testSuccessCaseWithdrawCoin() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException {
        vm.setAdminRole();
        vm.withdrawCoins();
        var machineBalance = getMachineBalance(vm);
        assertEquals(BigDecimal.ZERO, machineBalance);
        var spareCoinsField = getSpareCoins(vm);
        assertTrue(spareCoinsField.isEmpty());
    }

    @Test
    public void testInvalidPermissionAddItem() {
        assertThrows(SecurityException.class, () -> vm.addItem(coke, 1));
    }

    @Test
    public void testDuplicatedCodeAddItem() throws LimitExceededException, InvalidMachineStateException {
        vm.setAdminRole();
        vm.addItem(coke, 1);
        assertThrows(IllegalArgumentException.class, () -> vm.addItem(new Item("01", "Tea", BigDecimal.valueOf(2.00)), 1));
    }

    @Test
    public void testStateIsNotIdleAddItem() throws InvalidMachineStateException {
        vm.setAdminRole();
        vm.startOrReset();
        assertThrows(InvalidMachineStateException.class, () -> vm.addItem(coke, 1));
    }

    @Test
    public void testNegativeAmountAddItem() {
        vm.setAdminRole();
        assertThrows(IllegalArgumentException.class, () -> vm.addItem(coke, -1));
    }

    @Test
    public void testOverLimitCapacityAddItem() {
        vm.setAdminRole();
        assertThrows(LimitExceededException.class, () -> vm.addItem(coke, LIMIT_ITEM_QUANTITY_IN_VM + 1));
    }

    @Test
    public void testSuccessCaseAddItem() throws LimitExceededException, InvalidMachineStateException, NoSuchFieldException, IllegalAccessException {
        vm.setAdminRole();
        vm.addItem(coke, 2);
        var shelf = getShelf(vm);
        assertEquals(2, shelf.get(coke));
        vm.addItem(coke, 3);
        assertEquals(5, shelf.get(coke));
        var codeToItemMap = getCodeToItemMap(vm);
        assertEquals(Set.of("01"), codeToItemMap.keySet());
    }

    @Test
    public void testInvalidPermissionReset() {
        assertThrows(SecurityException.class, () -> vm.startOrReset());
    }

    @Test
    public void testStateIsPurchasingReset() throws InvalidMachineStateException {
        vm.setAdminRole();
        vm.startOrReset();
        vm.insertCoin(Coin.TEN_PENCE);
        assertThrows(InvalidMachineStateException.class, () -> vm.startOrReset());
    }

    @Test
    public void testSuccessCaseReset() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException {
        vm.setAdminRole();
        vm.startOrReset();
        vm.insertCoin(Coin.TEN_PENCE);
        vm.requestRefund();
        vm.collect();
        var state = getState(vm);
        assertEquals(VendingMachineState.READY, state);
        var customerBalance = getCustomerBalance(vm);
        assertEquals(BigDecimal.ZERO, customerBalance);
        var customerCoins = getCustomerCoins(vm);
        assertTrue(customerCoins.isEmpty());
        var selectedItem = getSelectedItem(vm);
        assertNull(selectedItem);
        var returnItem = getReturnItem(vm);
        assertNull(returnItem);
    }

    @Test
    public void testInvalidPermissionBreakToMaintenance() {
        assertThrows(SecurityException.class, () -> vm.breakToMaintenance());
    }

    @Test
    public void testStateIsPurchasingBreakToMaintenance() throws InvalidMachineStateException {
        vm.setAdminRole();
        vm.startOrReset();
        vm.insertCoin(Coin.TEN_PENCE);
        assertThrows(InvalidMachineStateException.class, () -> vm.breakToMaintenance());
    }

    @Test
    public void testSuccessCaseBreakToMaintenance() throws InvalidMachineStateException, NoSuchFieldException, IllegalAccessException {
        vm.setAdminRole();
        vm.startOrReset();
        vm.breakToMaintenance();
        var state = getState(vm);
        assertEquals(VendingMachineState.IDLE, state);
    }
}
