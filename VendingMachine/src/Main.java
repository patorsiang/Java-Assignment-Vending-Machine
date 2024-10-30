import Constants.Coin;
import Objects.Admin;
import Objects.Customer;
import Objects.VendingMachine;

import static Constants.Constants.*;

public class Main {
    public static void main(String[] args) {
        // initialize Vending Machine from factory
        VendingMachine vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);
        // initialize Admin
        Admin admin = new Admin(vm);
        // initialize Customer
        Customer customer = new Customer(vm);

        // admin add coin with negative amount
        admin.addCoins(Coin.TEN_PENCE, -1);

        // admin add coin from save
        for (var coin : changeCoin.entrySet()) {
            admin.addCoins(coin.getKey(), coin.getValue());
        }

        // admin test the system with add negative amount with addItem
        admin.addItem(milk, -1);

        // admin add items from 1st stock
        for (var item : itemStock.entrySet()) {
            admin.addItem(item.getKey(), item.getValue());
        }

        admin.addItem(coffee, 0);

        // if there is the another one, but it may be over the limit capacity of the machine
        for (var item : addOnItemStock.entrySet()) {
            admin.addItem(item.getKey(), item.getValue());
        }

        // customer try inserting Coin and selecting Item before opening
        customer.insertCoin(Coin.TEN_PENCE);
        customer.selectItem("01");
        customer.collect();
        customer.requestRefund();
        customer.requestChange();
        customer.requestPurchaseItem();

        // admin start the system
        admin.startOrReset();

        // admin withdraw coin during vending machine is opening for selling
        admin.withdrawCoins();

        // customer does not play following the step, try to collect, request refund,
        // change, and purchase before inserting coin or select item
        customer.collect();
        customer.requestRefund();
        customer.requestChange();
        customer.requestPurchaseItem();

        // customer purchase Item
        customer.selectItem("01");
        customer.insertCoin(Coin.TEN_PENCE);
        customer.insertCoin(Coin.ONE_POUND);
        // not enough money
        customer.requestPurchaseItem();
        customer.insertCoin(Coin.TEN_PENCE);
        customer.insertCoin(Coin.FIVE_PENCE);
        // enough money
        customer.requestPurchaseItem();
        customer.collect();

        // customer insert coin over price
        customer.selectItem("02");
        customer.insertCoin(Coin.TWO_POUNDS);
        customer.requestPurchaseItem();

        // admin break the system during customer is not finished his purchasing
        admin.breakToMaintenance();

        // customer collect his item and coin
        customer.collect();

        // customer select the Item out of the Catalog
        customer.selectItem("07");

        // customer select the Item out of stock
        customer.selectItem("04");

        // admin break to maintain again
        admin.breakToMaintenance();

        // admin withdraw coin during Idle state
        admin.withdrawCoins();

        // admin state the machine again
        admin.startOrReset();

        // customer insert coin over price, but the spare coins in the machine is not enough
        customer.selectItem("03");
        customer.insertCoin(Coin.TWO_POUNDS);
        customer.requestPurchaseItem();

        // admin add coin to be a change for customer
        admin.addCoins(Coin.ONE_PENNY, 1);
        admin.addCoins(Coin.TWO_PENCE, 2);
        admin.addCoins(Coin.FIVE_PENCE, 1);
        admin.addCoins(Coin.TWENTY_PENCE, 1);
        admin.addCoins(Coin.TEN_PENCE, 2);

        // Customer must request change manual
        customer.requestChange();
        customer.collect();

        // admin break to maintain again
        admin.breakToMaintenance();

        // admin withdraw coin during Idle state
        admin.withdrawCoins();

    }
}
