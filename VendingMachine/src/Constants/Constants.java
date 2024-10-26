package Constants;

import Objects.Item;

import java.math.BigDecimal;
import java.util.Map;

public class Constants {

    public static final int LIMIT_ITEM_QUANTITY_IN_VM = 15;

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

    public static final Map<Item, Integer> itemStock = Map.of(
            new Item("01", "Coke", BigDecimal.valueOf(1.25)), 5,
            new Item("02", "Water", BigDecimal.valueOf(1)), 5,
            new Item("03", "Milk", BigDecimal.valueOf(1.50)), 5
    );

    public static final Map<Item, Integer> addOnItemStock = Map.of(
            new Item("04", "Coffee", BigDecimal.valueOf(1.75)), 5
    );
}
