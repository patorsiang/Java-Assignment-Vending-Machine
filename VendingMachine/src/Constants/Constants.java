package Constants;

import Objects.Item;

import java.util.Map;

public class Constants {

    public static final int LIMIT_ITEM_QUANTITY_IN_VM = 15;

    public static final Map<Coin, Integer> changeCoin = Map.of(
            Coin.ONE_PENNY, 10,
            Coin.TWO_PENCE, 10,
            Coin.FIVE_PENCE, 10,
            Coin.TEN_PENCE, 10,
            Coin.TWENTY_PENCE, 10,
            Coin.FIFTY_PENCE, 10,
            Coin.ONE_POUND, 10,
            Coin.TWO_POUNDS, 10
    );

    public static final Map<Item, Integer> itemStock = Map.of(
            new Item("01", "Coke", 1.25), 5,
            new Item("02", "Water", 1), 5,
            new Item("03", "Milk", 1.50), 5
    );
}
