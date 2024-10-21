package Tests;

import Objects.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testValidItem() {
        // Test creating a valid item
        Item item = new Item("01", "Apple", 10.5);
        assertEquals("01", item.code());
        assertEquals("Apple", item.name());
        assertEquals(10.5, item.price());
    }

    @Test
    void testInvalidCode() {
        // Test invalid code that doesn't match the pattern
        assertThrows(IllegalArgumentException.class, () -> new Item("", "Apple", 10.5));
        assertThrows(IllegalArgumentException.class, () -> new Item("100", "Apple", 10.5));
        assertThrows(IllegalArgumentException.class, () -> new Item(null, "Apple", 10.5));
        assertThrows(IllegalArgumentException.class, () -> new Item("A1", "Apple", 10.5));
        assertThrows(IllegalArgumentException.class, () -> new Item("00", "Apple", 10.5));
    }

    @Test
    void testEmptyName() {
        // Test when the name is empty
        assertThrows(IllegalArgumentException.class, () -> new Item("01", "", 10.5));
        assertThrows(IllegalArgumentException.class, () -> new Item("01", null, 10.5));
    }

    @Test
    void testNegativePrice() {
        // Test when the price is negative
        assertThrows(IllegalArgumentException.class, () -> new Item("01", "Apple", -10.5));
    }
}