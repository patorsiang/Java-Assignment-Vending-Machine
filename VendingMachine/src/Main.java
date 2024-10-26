import Constants.Coin;
import Exceptions.InvalidMachineStateException;
import Exceptions.LimitExceededException;
import Objects.Admin;
import Objects.Customer;
import Objects.Item;
import Objects.VendingMachine;

import java.math.BigDecimal;

import static Constants.Constants.*;

public class Main {
    public static void main(String[] args) throws LimitExceededException, InvalidMachineStateException {
        // 1. Initialize the vending machine
        VendingMachine vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);

        // 2. Create Admin and Customer objects with the vending machine instance
        Admin admin = new Admin(vm);
        Customer customer = new Customer(vm);

        // 3. Admin adds items to the vending machine
        Item coke = new Item("01", "Coke", BigDecimal.valueOf(1.25));
        admin.addItem(coke, 5);
        admin.startOrReset();
        customer.insertCoin(Coin.ONE_POUND);
        customer.insertCoin(Coin.TWENTY_PENCE);
        customer.requestRefund();
        customer.collect();
        admin.breakToMaintenance();
        admin.addItem(coke, 5);
    }
}
