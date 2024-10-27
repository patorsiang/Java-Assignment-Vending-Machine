package Constants;

import Objects.Item;

import java.math.BigDecimal;
import java.util.Map;

/**
 * It contains static values used for mock demonstrations.
 */
public class Constants {

    // Using in set up vending machines step; if there are number input
    public static final int LIMIT_ITEM_QUANTITY_IN_VM = 15;

    // Like the pile of coins that the owner or admin prepared to push them into the vending machine
    public static final Map<Coin, Integer> changeCoin = Map.of(
            Coin.ONE_PENNY, 85,
            Coin.TWO_PENCE, 75,
            Coin.FIVE_PENCE, 65,
            Coin.TEN_PENCE, 54,
            Coin.TWENTY_PENCE, 45,
            Coin.FIFTY_PENCE, 30,
            Coin.ONE_POUND, 20,
            Coin.TWO_POUNDS, 10
    );

    // The 1st stock that the owner or admin prepared to sell in the vending machine
    public static final Map<Item, Integer> itemStock = Map.of(
            new Item("01", "Coke", BigDecimal.valueOf(1.25)), 5,
            new Item("02", "Water", BigDecimal.valueOf(1)), 5,
            new Item("03", "Milk", BigDecimal.valueOf(1.50)), 5
    );

    // The 2nd stock
    public static final Map<Item, Integer> addOnItemStock = Map.of(
            new Item("04", "Coffee", BigDecimal.valueOf(1.75)), 5
    );
}