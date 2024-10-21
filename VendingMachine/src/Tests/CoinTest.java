package Tests;

import Constants.Coin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoinTest {

    @Test
    void testCoinValues() {
        // Verify that each enum constant returns the correct value
        assertEquals(0.01, Coin.ONE_PENNY.getValue(), 0.00001);
        assertEquals(0.02, Coin.TWO_PENCE.getValue(), 0.00001);
        assertEquals(0.05, Coin.FIVE_PENCE.getValue(), 0.00001);
        assertEquals(0.10, Coin.TEN_PENCE.getValue(), 0.00001);
        assertEquals(0.20, Coin.TWENTY_PENCE.getValue(), 0.00001);
        assertEquals(0.50, Coin.FIFTY_PENCE.getValue(), 0.00001);
        assertEquals(1.00, Coin.ONE_POUND.getValue(), 0.00001);
        assertEquals(2.00, Coin.TWO_POUNDS.getValue(), 0.00001);
    }

    @Test
    void testCoinEnumNames() {
        // Verify that the enum constants have the correct names
        assertEquals("ONE_PENNY", Coin.ONE_PENNY.name());
        assertEquals("TWO_PENCE", Coin.TWO_PENCE.name());
        assertEquals("FIVE_PENCE", Coin.FIVE_PENCE.name());
        assertEquals("TEN_PENCE", Coin.TEN_PENCE.name());
        assertEquals("TWENTY_PENCE", Coin.TWENTY_PENCE.name());
        assertEquals("FIFTY_PENCE", Coin.FIFTY_PENCE.name());
        assertEquals("ONE_POUND", Coin.ONE_POUND.name());
        assertEquals("TWO_POUNDS", Coin.TWO_POUNDS.name());
    }

    @Test
    void testCoinEnumOrdinals() {
        // Verify the ordinal (position) of each enum constant
        assertEquals(0, Coin.ONE_PENNY.ordinal());
        assertEquals(1, Coin.TWO_PENCE.ordinal());
        assertEquals(2, Coin.FIVE_PENCE.ordinal());
        assertEquals(3, Coin.TEN_PENCE.ordinal());
        assertEquals(4, Coin.TWENTY_PENCE.ordinal());
        assertEquals(5, Coin.FIFTY_PENCE.ordinal());
        assertEquals(6, Coin.ONE_POUND.ordinal());
        assertEquals(7, Coin.TWO_POUNDS.ordinal());
    }
}
