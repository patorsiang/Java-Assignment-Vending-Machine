import java.util.*;

public class VendingMachineService extends ItemVerification {

    Map<Coin, Integer> coins;
    List<Item> items;
    String selectedItem;
    Map<Coin, Integer> holdCoins;

    VendingMachineService() {
        this.coins = new HashMap<>();
        this.items = new ArrayList<>();
        this.selectedItem = null;
        this.holdCoins = new HashMap<>();
    }

    Map<Coin, Integer> fillCoins(Coin coin, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (!coins.containsKey(coin)) {
            coins.put(coin, quantity);
        } else {
            coins.put(coin, coins.get(coin) + quantity);
        }
        return coins;
    }

    Map<Coin, Integer> getCoins() {
        return coins;
    }

    List<Item> addItem(Item item) {
        for (var storeditem: items) {
            if (item.getCode().equals(storeditem.getCode())) {
                throw new IllegalArgumentException("Item already exists");
            }
        }
        items.add(item);
        return items;
    }

    Map<Coin, Integer> withdrawCoins() {
        var withdrawCoins = new HashMap<>(coins);
        coins.clear();
        return withdrawCoins;
    }

    List<Item> fillItem (String code, int quantity) throws OverflowingShelfException {

        validateCode(code);

        for (var item : items) {
            if (item.getCode().equals(code)) {
                item.increaseQuantity(quantity);
            }
        }
        return items;
    }

    void listShelfItems () {
        System.out.println("Code\tName\tPrice\tQuantity");
        for (var item : items) {
            System.out.println(item.printItem());
        }
    }
}
