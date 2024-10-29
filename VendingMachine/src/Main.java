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

        // admin start the system
        admin.startOrReset();

        // admin withdraw coin during vending machine is opening for selling
        admin.withdrawCoins();

        // admin break the system
        admin.breakToMaintenance();

        // admin withdraw coin during Idle state
        admin.withdrawCoins();

    }
}
