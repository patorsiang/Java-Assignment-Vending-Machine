package Tests;

import Constants.Coin;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {
    @Test
    public void should() {
        assertEquals(0.01, Coin.ONE_PENNY.getValue());
        assertEquals(0.02, Coin.TWO_PENCE.getValue());
        assertEquals(0.05, Coin.FIVE_PENCE.getValue());
        assertEquals(0.10, Coin.TEN_PENCE.getValue());
        assertEquals(0.20, Coin.TWENTY_PENCE.getValue());
        assertEquals(0.50, Coin.FIFTY_PENCE.getValue());
        assertEquals(1.00, Coin.ONE_POUND.getValue());
        assertEquals(2.00, Coin.TWO_POUNDS.getValue());
    }
}
