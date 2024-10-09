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
    void testGetCode() {
        assertEquals("01", item.getCode());
    }

    @Test
    @Order(1)
    void testPrintItem() {
        assertEquals("01 Coke ￡1.25 0", item.printItem());
    }

    @Test
    @Order(2)
    void testChangePriceSuccess() {
        item.changePrice(1.5);
        assertEquals("01 Coke ￡1.50 0", item.printItem());
    }

    @Test
    void testChangePriceFail() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.changePrice(-1.5));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    @Order(3)
    void testIncreaseQuantitySuccess() throws OverflowingShelfException {
        item.increaseQuantity(10);
        assertEquals("01 Coke ￡1.50 10", item.printItem());
    }

    @Test
    void testIncreaseQuantityFail() {
        Throwable exception = assertThrows(OverflowingShelfException.class, () -> item.increaseQuantity(100));
        assertEquals("This item, 01, reached the limit of its' shelf.", exception.getMessage());
    }

    @Test
    void testIncreaseQuantityFail2() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> item.increaseQuantity(-2));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    @Order(4)
    void testDecreaseQuantitySuccess() throws NotEnoughException, OverflowingShelfException {
        item.decreaseQuantity(2);
        assertEquals("01 Coke ￡1.50 8", item.printItem());
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
