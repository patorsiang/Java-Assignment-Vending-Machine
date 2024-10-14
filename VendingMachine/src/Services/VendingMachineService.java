package Services;

import Constants.Coin;
import Constants.Constants;
import Objects.Item;
import Exceptions.OverflowingShelfException;
import Verifications.Verification;

import java.util.*;

public class VendingMachineService extends Verification {

    private final Map<Coin, Integer> coins;
    private final Map<Coin, Integer> holdCoins;
    private final List<Item> items;
    private String selectedItem;

    public VendingMachineService() {
        this.coins = new HashMap<>();
        this.items = new ArrayList<>();
        this.selectedItem = null;
        this.holdCoins = new HashMap<>();
    }

    public Map<Coin, Integer> fillCoins(Coin coin, int quantity) {
        if (validateLimitQuantity(quantity, Constants.LIMIT_COIN_QUANTITY_IN_VM) && validateQuantity(quantity)) {
            if (!coins.containsKey(coin)) {
                coins.put(coin, quantity);
            } else if (validateLimitQuantity(coins.get(coin) + quantity, Constants.LIMIT_COIN_QUANTITY_IN_VM)) {
                coins.put(coin, coins.get(coin) + quantity);
            }
        }
        return coins;
    }

    public Map<Coin, Integer> getCoins() {
        return coins;
    }

    public List<Item> addItem(Item item) {
        for (var storeditem : items) {
            if (item.getCode().equals(storeditem.getCode())) {
                throw new IllegalArgumentException("Item already exists");
            }
        }
        items.add(item);
        return items;
    }

    public Map<Coin, Integer> withdrawCoins() {
        var withdrawCoins = new HashMap<>(coins);
        coins.clear();
        return withdrawCoins;
    }

    public List<Item> fillItem(String code, int quantity) throws OverflowingShelfException {

        validateCode(code);

        for (var item : items) {
            if (item.getCode().equals(code)) {
                item.increaseQuantity(quantity);
            }
        }
        return items;
    }

    public void listShelfItems() {
        System.out.println("Code\tName\tPrice\tQuantity");
        for (var item : items) {
            System.out.println(item.printItem());
        }
    }

    public void setSelectedItem(String code) {
        validateCode(code);
        var checked = false;
        for (var item : items) {
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

    public String getSelectedItem() {
        return selectedItem;
    }

    public Map<Coin, Integer> insertCoin(Coin coin) {
        if (!holdCoins.containsKey(coin)) {
            holdCoins.put(coin, 1);
        } else if (validateLimitQuantity(holdCoins.get(coin) + 1, Constants.LIMIT_COIN_QUANTITY_IN_VM)) {
            holdCoins.put(coin, holdCoins.get(coin) + 1);
        }
        return holdCoins;
    }

}
