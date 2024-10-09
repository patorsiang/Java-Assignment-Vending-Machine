import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    void testGetCode() {
        Item item = new Item("01", "Coke", 1.25);
        assertEquals("01", item.getCode());
    }

    @Test
    void testChangePriceSuccess() {
        Item item = new Item("01", "Coke", 1.25);
        item.changePrice(1.5);
        assertEquals("01 Coke ￡1.50 0", item.printItem());
    }

    @Test
    void testChangePriceFail() {
        Item item = new Item("01", "Coke", 1.25);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.changePrice(-1.5));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    void testIncreaseQuantitySuccess() throws OverflowingShelfException {
        Item item = new Item("01", "Coke", 1.25);
        item.increaseQuantity(10);
        assertEquals("01 Coke ￡1.25 10", item.printItem());
    }

    @Test
    void testIncreaseQuantityFail() {
        Item item = new Item("01", "Coke", 1.25);
        Throwable exception = assertThrows(OverflowingShelfException.class, () -> item.increaseQuantity(100));
        assertEquals("This item, 01, reached the limit of its' shelf.", exception.getMessage());
    }

    @Test
    void testIncreaseQuantityFail2() {
        Item item = new Item("01", "Coke", 1.25);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.increaseQuantity(-2));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    void testDecreaseQuantitySuccess() throws NotEnoughException, OverflowingShelfException {
        Item item = new Item("01", "Coke", 1.25);
        item.increaseQuantity(3);
        item.decreaseQuantity(2);
        assertEquals("01 Coke ￡1.25 1", item.printItem());
    }

    @Test
    void testDecreaseQuantityFail() {
        Item item = new Item("01", "Coke", 1.25);
        Throwable exception = assertThrows(NotEnoughException.class, () -> item.decreaseQuantity(10));
        assertEquals("This item, 01, have not enough stock as you requested.", exception.getMessage());
    }

    @Test
    void testDecreaseQuantityFail2() {
        Item item = new Item("01", "Coke", 1.25);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.decreaseQuantity(-1));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

}
