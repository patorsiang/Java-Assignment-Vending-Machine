package Tests;

import Constants.VendingMachineState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VendingMachineStateTest {

    @Test
    public void testVendingMachineStatesExist() {
        // Check that each state in VendingMachineState enum exists
        assertNotNull(VendingMachineState.IDLE, "IDLE state should exist");
        assertNotNull(VendingMachineState.READY, "READY state should exist");
        assertNotNull(VendingMachineState.PURCHASING, "PURCHASING state should exist");
        assertNotNull(VendingMachineState.PURCHASED, "PURCHASED state should exist");
        assertNotNull(VendingMachineState.PURCHASED_COMPLETED, "PURCHASED_COMPLETED state should exist");
        assertNotNull(VendingMachineState.CANCELED, "CANCELED state should exist");
    }
}
