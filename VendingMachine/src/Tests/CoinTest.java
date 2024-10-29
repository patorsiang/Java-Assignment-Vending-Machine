package Tests;

import Constants.Coin;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinTest {

    @Test
    public void testCoinValues() {
        // Test each coin to ensure it returns the correct value
        assertEquals(BigDecimal.valueOf(2.0), Coin.TWO_POUNDS.getValue(), "TWO_POUNDS value should be 2.0");
        assertEquals(BigDecimal.valueOf(1.0), Coin.ONE_POUND.getValue(), "ONE_POUND value should be 1.0");
        assertEquals(BigDecimal.valueOf(0.50), Coin.FIFTY_PENCE.getValue(), "FIFTY_PENCE value should be 0.50");
        assertEquals(BigDecimal.valueOf(0.20), Coin.TWENTY_PENCE.getValue(), "TWENTY_PENCE value should be 0.20");
        assertEquals(BigDecimal.valueOf(0.10), Coin.TEN_PENCE.getValue(), "TEN_PENCE value should be 0.10");
        assertEquals(BigDecimal.valueOf(0.05), Coin.FIVE_PENCE.getValue(), "FIVE_PENCE value should be 0.05");
        assertEquals(BigDecimal.valueOf(0.02), Coin.TWO_PENCE.getValue(), "TWO_PENCE value should be 0.02");
        assertEquals(BigDecimal.valueOf(0.01), Coin.ONE_PENNY.getValue(), "ONE_PENNY value should be 0.01");
    }
}
