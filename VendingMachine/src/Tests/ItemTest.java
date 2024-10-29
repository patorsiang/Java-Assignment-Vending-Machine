package Tests;

import Objects.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {

    @Test
    public void testValidItemCreation() {
        // Create a valid item and verify its properties
        Item item = new Item("01", "Coke", BigDecimal.valueOf(1.25));
        assertEquals("01", item.code(), "Code should be '01'");
        assertEquals("Coke", item.name(), "Name should be 'Coke'");
        assertEquals(BigDecimal.valueOf(1.25), item.price(), "Price should be 1.25");
    }

    @Test
    public void testInvalidCodeEmpty() {
        // Test invalid empty code
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Item("", "Water", BigDecimal.valueOf(1.00)));
        assertEquals("Code cannot be empty", exception.getMessage());
    }

    @Test
    public void testInvalidCodePattern() {
        // Test code outside the 01-99 range
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Item("100", "Water", BigDecimal.valueOf(1.00)));
        assertEquals("Invalid code: Must be a two-digit number between 01 and 99", exception.getMessage());
    }

    @Test
    public void testNullCode() {
        // Test null code
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Item(null, "Water", BigDecimal.valueOf(1.00)));
        assertEquals("Code cannot be empty", exception.getMessage());
    }

    @Test
    public void testInvalidNameEmpty() {
        // Test invalid empty name
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Item("02", "", BigDecimal.valueOf(1.00)));
        assertEquals("Invalid name: Name cannot be empty", exception.getMessage());
    }

    @Test
    public void testNullName() {
        // Test null name
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Item("02", null, BigDecimal.valueOf(1.00)));
        assertEquals("Invalid name: Name cannot be empty", exception.getMessage());
    }

    @Test
    public void testNegativePrice() {
        // Test invalid negative price
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Item("03", "Milk", BigDecimal.valueOf(-1.00)));
        assertEquals("Invalid price: Price must be non-negative", exception.getMessage());
    }

    @Test
    public void testZeroPrice() {
        // Test valid zero price
        Item item = new Item("04", "Coffee", BigDecimal.ZERO);
        assertEquals("04", item.code(), "Code should be '04'");
        assertEquals("Coffee", item.name(), "Name should be 'Coffee'");
        assertEquals(BigDecimal.ZERO, item.price(), "Price should be 0.0");
    }
}

