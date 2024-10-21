package Services;

import Objects.Item;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ItemReader {
    public static List<Item> loadItemsFromCSV(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            String line;
            boolean isFirstLine = true;  // To skip the header row
            List<Item> items = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Skip the header
                }

                Item item = getItem(line);
                items.add(item);
            }
            return items;
        }
    }

    private static @NotNull Item getItem(String line) {
        String[] values = line.split(",");  // Split CSV by comma
        if (values.length != 3) {
            throw new IllegalArgumentException("Invalid line in CSV: " + line);
        }

        String code = values[0].trim();
        String name = values[1].trim();
        double price;
        try {
            price = Double.parseDouble(values[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + values[2]);
        }

        // return an Item
        return new Item(code, name, price);
    }
}
