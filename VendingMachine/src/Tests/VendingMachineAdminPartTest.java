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
    public void testCoin() throws NoSuchFieldException, IllegalAccessException {
        // Failure: Permission denied
        assertThrows(SecurityException.class, () -> vm.addCoins(Coin.TEN_PENCE, 1));
        // Failure: Negative amount
        vm.setAdminRole();
        assertThrows(IllegalArgumentException.class, () -> vm.addCoins(Coin.TEN_PENCE, -1));
        // Success
        vm.addCoins(Coin.TEN_PENCE, 1);
        var spareCoinsField = getSpareCoins(vm);
        assertEquals(1, spareCoinsField.get(Coin.TEN_PENCE));
        var machineBalanceField = getMachineBalance(vm);
        assertEquals(BigDecimal.valueOf(0.10), machineBalanceField);
    }

    @Test
    public void testFailureCasesWithdrawCoin() throws InvalidMachineStateException {
        // Permission Denied
        assertThrows(SecurityException.class, () -> vm.withdrawCoins());
        // Withdraw Coins when the machine is opening.
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
    public void testFailureCasesPermissionAddItem() throws LimitExceededException, InvalidMachineStateException {
        // Permission Denied
        assertThrows(SecurityException.class, () -> vm.addItem(coke, 1));
        vm.setAdminRole();
        // Duplicated code
        vm.addItem(coke, 1);
        assertThrows(IllegalArgumentException.class, () -> vm.addItem(new Item("01", "Tea", BigDecimal.valueOf(2.00)), 1));
        // Add Items with the machine is opening
        vm.startOrReset();
        assertThrows(InvalidMachineStateException.class, () -> vm.addItem(coke, 1));
        // Negative amount
        assertThrows(IllegalArgumentException.class, () -> vm.addItem(coke, -1));
        // Over the limit capacity
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
    public void testFailureCasesReset() throws InvalidMachineStateException {
        // Permission Denied
        assertThrows(SecurityException.class, () -> vm.startOrReset());
        // Reset Machine while there is a customer using
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
    public void testFailureCasesBreakToMaintenance() throws InvalidMachineStateException {
        // Permission Denied
        assertThrows(SecurityException.class, () -> vm.breakToMaintenance());
        // Break the machine before finishing the purchase process
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
