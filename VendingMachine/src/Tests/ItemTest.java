package Tests;

import Exceptions.NotEnoughException;
import Exceptions.OverflowingShelfException;
import Objects.Item;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemTest {

    static Item item;

    @BeforeAll
    static void setUp() {
        item = new Item("01", "Coke", 1.25);
    }

    @Test
    void testSetItemSuccess() {
        Item chip = new Item("02", "Chip", 1);
        Assertions.assertEquals("02", chip.getCode());
    }

    @Test
    void testSetItemError() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Item("00", "coke", 1.25));
        assertEquals("Invalid code", exception.getMessage());
    }

    @Test
    void testGetCode() {
        Assertions.assertEquals("01", item.getCode());
    }

    @Test
    @Order(1)
    void testPrintItem() {
        Assertions.assertEquals("01\tCoke\t￡1.25\t0", item.printItem());
    }

    @Test
    @Order(2)
    void testChangePriceSuccess() {
        item.changePrice(1.5);
        Assertions.assertEquals("01\tCoke\t￡1.50\t0", item.printItem());
    }

    @Test
    void testChangePriceFail() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.changePrice(-1.5));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    @Order(3)
    void testIncreaseQuantitySuccess() throws OverflowingShelfException, OverflowingShelfException {
        item.increaseQuantity(10);
        Assertions.assertEquals("01\tCoke\t￡1.50\t10", item.printItem());
    }

    @Test
    void testIncreaseQuantityFailFull() {
        Throwable exception = assertThrows(OverflowingShelfException.class, () -> item.increaseQuantity(100));
        assertEquals("This item, 01, reached the limit of its' shelf.", exception.getMessage());
    }

    @Test
    void testIncreaseQuantityFailNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.increaseQuantity(-2));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    @Order(4)
    void testDecreaseQuantitySuccess() throws NotEnoughException {
        item.decreaseQuantity(2);
        Assertions.assertEquals("01\tCoke\t￡1.50\t8", item.printItem());
    }

    @Test
    @Order(5)
    void testDecreaseQuantityFail() {
        Throwable exception = assertThrows(NotEnoughException.class, () -> item.decreaseQuantity(10));
        assertEquals("This item, 01, have not enough stock as you requested.", exception.getMessage());
    }

    @Test
    void testDecreaseQuantityFail2() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.decreaseQuantity(-1));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

}
