package Objects;

import Services.ItemReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private final int maxCapacity;
    private final List<Item> items;

    public VendingMachine(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than 0");
        }

        this.maxCapacity = maxCapacity;
        this.items = new ArrayList<Item>();
    }

    public void addItems(String filePath) throws IOException {
        items.addAll(ItemReader.loadItemsFromCSV(filePath));
    }

    public void addItem(Item item) {
        if (items.size() >= maxCapacity) {
            throw new IllegalStateException("Vending machine is full. Cannot add more items.");
        }
        items.add(item);
    }

    public int getCurrentItemCount() {
        return items.size();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy to protect internal state
    }
}
