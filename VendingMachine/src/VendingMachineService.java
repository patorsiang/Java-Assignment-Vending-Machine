import java.util.*;

public class VendingMachineService extends ItemVerification {

    private final Map<Coin, Integer> coins;
    private final Map<Coin, Integer> holdCoins;
    private final List<Item> items;
    private String selectedItem;

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

    void setSelectedItem(String code) {
        validateCode(code);
        var checked = false;
        for (var item: items) {
            if (item.getCode().equals(code)) {
                this.selectedItem = code;
                checked = true;
                break;
            }
        }

        if (!checked) {
            selectedItem = null;
            throw new IllegalArgumentException("Item does not exist");
        }
    }

    String getSelectedItem() {
        return selectedItem;
    }

    Map<Coin, Integer> insertCoin(Coin coin) {
        if (!holdCoins.containsKey(coin)) {
            holdCoins.put(coin, 1);
        } else {
            holdCoins.put(coin, holdCoins.get(coin) + 1);
        }
        return holdCoins;
    }
}
