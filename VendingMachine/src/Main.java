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
        for (var coin: changeCoin.entrySet()) {
            admin.addCoins(coin.getKey(), coin.getValue());
        }

        // admin test the system with add negative amount with addItem
        admin.addItem(milk, -1);

        // admin add items from 1st stock
        for (var item: itemStock.entrySet()) {
            admin.addItem(item.getKey(), item.getValue());
        }

        // if there is the another one, but it may be over the limit capacity of the machine
        for (var item: addOnItemStock.entrySet()) {
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

        // over price
        customer.selectItem("02");
        customer.insertCoin(Coin.TWO_POUNDS);
        customer.requestPurchaseItem();
        customer.collect();

        // admin break the system
        admin.breakToMaintenance();

        // admin withdraw coin during Idle state
        admin.withdrawCoins();

    }
}
