import Constants.Coin;
import Objects.Admin;
import Objects.Customer;
import Objects.VendingMachine;

import static Constants.Constants.*;

public class Main {
    public static void main(String[] args) {
        // initiate vending machine
        var vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);

        // set up Admin and Customer
        var admin = new Admin(vm);
        var customer = new Customer(vm);

        // Admin: adding items from item stock
        for (var item : itemStock.entrySet()) {
            admin.addItem(item.getKey(), item.getValue());
        }

        for (var item : addOnItemStock.entrySet()) {
            admin.addItem(item.getKey(), item.getValue());
        }

        // Admin: starting the system
        admin.startOrReset();

        // Customer: canceling
        customer.insertCoin(Coin.ONE_POUND);
        customer.selectItem("02");
        customer.requestRefund();
        customer.collect();

        // Customer: purchasing with enough coin
        customer.insertCoin(Coin.ONE_POUND);
        customer.selectItem("02");
        customer.requestPurchaseItem();
        customer.collect();

        // Customer: purchasing with over coins and change is not enough
        customer.insertCoin(Coin.TWO_POUNDS);
        customer.selectItem("03");
        customer.requestPurchaseItem();
        admin.addCoins(Coin.FIFTY_PENCE, 1);
        customer.requestChange();
        customer.collect();

        // Admin: breaking to maintain
        admin.breakToMaintenance();

        // Admin: withdrawing coin
        admin.withdrawCoins();

        // Admin: adding coins from saving
        for (var coin : changeCoin.entrySet()) {
            admin.addCoins(coin.getKey(), coin.getValue());
        }

        // Admin: starting system again
        admin.startOrReset();
    }
}
