package Tests;

import Constants.Constants;
import Constants.Coin;
import Objects.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ConstantsTest {

    @Test
    public void testLimitItemQuantityInVM() {
        // Verify the limit item quantity in the vending machine
        assertEquals(15, Constants.LIMIT_ITEM_QUANTITY_IN_VM, "LIMIT_ITEM_QUANTITY_IN_VM should be 15");
    }

    @Test
    public void testChangeCoinValues() {
        // Verify each coin in the changeCoin map has the correct quantity
        Map<Coin, Integer> changeCoin = Constants.changeCoin;
        assertEquals(85, changeCoin.get(Coin.ONE_PENNY), "ONE_PENNY quantity should be 85");
        assertEquals(75, changeCoin.get(Coin.TWO_PENCE), "TWO_PENCE quantity should be 75");
        assertEquals(65, changeCoin.get(Coin.FIVE_PENCE), "FIVE_PENCE quantity should be 65");
        assertEquals(54, changeCoin.get(Coin.TEN_PENCE), "TEN_PENCE quantity should be 54");
        assertEquals(45, changeCoin.get(Coin.TWENTY_PENCE), "TWENTY_PENCE quantity should be 45");
        assertEquals(30, changeCoin.get(Coin.FIFTY_PENCE), "FIFTY_PENCE quantity should be 30");
        assertEquals(20, changeCoin.get(Coin.ONE_POUND), "ONE_POUND quantity should be 20");
        assertEquals(10, changeCoin.get(Coin.TWO_POUNDS), "TWO_POUNDS quantity should be 10");
    }

    @Test
    public void testItems() {
        // Check the attributes of each item
        Item coke = Constants.coke;
        assertEquals("01", coke.code(), "Coke code should be '01'");
        assertEquals("Coke", coke.name(), "Coke name should be 'Coke'");
        assertEquals(BigDecimal.valueOf(1.25), coke.price(), "Coke price should be 1.25");

        Item water = Constants.water;
        assertEquals("02", water.code(), "Water code should be '02'");
        assertEquals("Water", water.name(), "Water name should be 'Water'");
        assertEquals(BigDecimal.valueOf(1.0), water.price(), "Water price should be 1.0");

        Item milk = Constants.milk;
        assertEquals("03", milk.code(), "Milk code should be '03'");
        assertEquals("Milk", milk.name(), "Milk name should be 'Milk'");
        assertEquals(BigDecimal.valueOf(1.50), milk.price(), "Milk price should be 1.50");

        Item coffee = Constants.coffee;
        assertEquals("04", coffee.code(), "Coffee code should be '04'");
        assertEquals("Coffee", coffee.name(), "Coffee name should be 'Coffee'");
        assertEquals(BigDecimal.valueOf(1.75), coffee.price(), "Coffee price should be 1.75");
    }

    @Test
    public void testItemStockValues() {
        // Verify the initial stock quantities for items in the vending machine
        Map<Item, Integer> itemStock = Constants.itemStock;
        assertEquals(6, itemStock.get(Constants.coke), "Coke stock should be 6");
        assertEquals(4, itemStock.get(Constants.water), "Water stock should be 4");
        assertEquals(5, itemStock.get(Constants.milk), "Milk stock should be 5");
        assertFalse(itemStock.containsKey(Constants.coffee), "Coffee should not be in the initial stock");
    }

    @Test
    public void testAddOnItemStockValues() {
        // Verify the additional stock for coffee in the addOnItemStock map
        Map<Item, Integer> addOnItemStock = Constants.addOnItemStock;
        assertEquals(5, addOnItemStock.get(Constants.coffee), "Coffee add-on stock should be 5");
        assertFalse(addOnItemStock.containsKey(Constants.coke), "Coke should not be in the add-on stock");
    }
}