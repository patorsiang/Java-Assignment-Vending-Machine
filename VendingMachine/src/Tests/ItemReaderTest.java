package Tests;

import Objects.Item;
import Services.ItemReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemReaderTest {

    @Test
    void testValidCSV() throws IOException {
        // Assuming the file "valid_vending_machine_items.csv" exists and has valid data
        String filePath = "data/valid_items.csv";

        // Call the method to load items
        List<Item> items = ItemReader.loadItemsFromCSV(filePath);

        // Check that the items list is not empty and contains the correct number of items
        assertEquals(5, items.size());

        // Check the content of the first item
        Item firstItem = items.getFirst();
        assertEquals("01", firstItem.code());
        assertEquals("Chips", firstItem.name());
        assertEquals(1.50, firstItem.price());
    }

    @Test
    void testFileNotFound() {
        // Use an invalid file path that doesn't exist
        String filePath = "path/to/non_existent_file.csv";

        // Expect FileNotFoundException
        Exception exception = assertThrows(IOException.class, () -> ItemReader.loadItemsFromCSV(filePath));

        String expectedMessage = "File not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInvalidCSVFormat() {
        String invalidCSV = "data/invalid_items.csv";

        // Expect IllegalArgumentException when an invalid CSV is processed
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ItemReader.loadItemsFromCSV(invalidCSV));

        String expectedMessage = "Invalid line in CSV";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInvalidPriceFormat() {
        String invalidPriceCSV = "data/invalid_price_items.csv";  // Assumes file contains bad price

        // Expect IllegalArgumentException for bad price
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ItemReader.loadItemsFromCSV(invalidPriceCSV));

        String expectedMessage = "Invalid price format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEmptyCSV() throws IOException {
        // Assuming the file "empty_vending_machine_items.csv" exists and is empty
        String emptyFilePath = "data/empty_items.csv";

        List<Item> items = ItemReader.loadItemsFromCSV(emptyFilePath);

        // Check that the items list is empty
        assertTrue(items.isEmpty());
    }
}
