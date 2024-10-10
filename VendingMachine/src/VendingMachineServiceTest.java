import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VendingMachineServiceTest {

    static VendingMachineService vendingMachine;

    @BeforeAll
    static void setUp() {
        vendingMachine = new VendingMachineService();
    }

    @Test
    @Order(1)
    void testFillCoinSuccess() {
        var coins = vendingMachine.fillCoins(Coin.ONE_PENNY, 10);

        assertEquals(Map.of(Coin.ONE_PENNY, 10), coins);
    }

    @Test
    void testFillCoinFailureNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> vendingMachine.fillCoins(Coin.ONE_PENNY, -10));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    @Order(2)
    void testFillCoinTwoStep() {
        vendingMachine.fillCoins(Coin.ONE_PENNY, 10);
        var coins = vendingMachine.fillCoins(Coin.TWO_PENCE, 10);

        assertEquals(Map.of(Coin.ONE_PENNY, 20, Coin.TWO_PENCE, 10), coins);
    }

    @Test
    @Order(3)
    void testGetCoins() {
        var coins = vendingMachine.getCoins();

        assertEquals(Map.of(Coin.ONE_PENNY, 20, Coin.TWO_PENCE, 10), coins);
    }

    @Test
    void testAddItem () {
        Item coke = new Item("01", "coke", 10);
        ArrayList<Item> expected = new ArrayList<Item>();
        expected.add(coke);
        var items = vendingMachine.addItem(coke);
        assertEquals(expected, items);
    }

    @Test
    @Order(4)
    void testWithdrawCoin () {
        var coins = vendingMachine.withdrawCoins();
        var remainCoins = vendingMachine.getCoins();
        assertEquals(Map.of(Coin.ONE_PENNY, 20, Coin.TWO_PENCE, 10), coins);
        assertEquals(new HashMap<>(), remainCoins);
    }
}
