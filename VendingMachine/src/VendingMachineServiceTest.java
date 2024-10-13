import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VendingMachineServiceTest {

    static VendingMachineService vendingMachine;
    static Item coke;
    static Item apple;
    static ArrayList<Item> expected;

    @BeforeAll
    static void setUp() {
        vendingMachine = new VendingMachineService();
        coke = new Item("01", "coke", 10);
        apple = new Item("02", "apple", 5);
        expected = new ArrayList<Item>();
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
    @Order(4)
    void testWithdrawCoin () {
        var coins = vendingMachine.withdrawCoins();
        var remainCoins = vendingMachine.getCoins();
        assertEquals(Map.of(Coin.ONE_PENNY, 20, Coin.TWO_PENCE, 10), coins);
        assertEquals(new HashMap<>(), remainCoins);
    }

    @Test
    @Order(5)
    void testAddItem () {
        expected.add(coke);
        var items = vendingMachine.addItem(coke);
        assertEquals(expected, items);
    }

    @Test
    @Order(10)
    void testAddSecItem () {
        expected.add(apple);
        var items = vendingMachine.addItem(apple);
        assertEquals(expected, items);
    }

    @Test
    @Order(6)
    void testAddItemFailure() {
        Item item = new Item("01", "coke", 10);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> vendingMachine.addItem(item));
        assertEquals("Item already exists", exception.getMessage());
    }

    @Test
    @Order(7)
    void testFillItemSuccess() throws OverflowingShelfException {
        var items = vendingMachine.fillItem("01", 10);
        assertEquals(expected, items);
    }

    @Test
    @Order(11)
    void testFillSecItemSuccess() throws OverflowingShelfException {
        var items = vendingMachine.fillItem("02", 10);
        assertEquals(expected, items);
    }

    @Test
    @Order(8)
    void testFillItemFailFull() {
        Throwable exception = assertThrows(OverflowingShelfException.class, () -> vendingMachine.fillItem("01", 90));
        assertEquals("This item, 01, reached the limit of its' shelf.", exception.getMessage());
    }

    @Test
    @Order(9)
    void testFillItemFailNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> vendingMachine.fillItem("01", -1));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    @Order(12)
    void testListShelfItems() {
        // Set up to capture the printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        vendingMachine.listShelfItems();  // Assume this method prints something

        // Verify the output
        String expectedOutput = "Code\tName\tPrice\tQuantity\n01\tcoke\t￡10.00\t10\n02\tapple\t￡5.00\t10";  // Replace with actual expected output
        assertEquals(expectedOutput, outContent.toString().trim());

        // Restore the original System.out
        System.setOut(originalOut);
    }
}
