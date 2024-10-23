import Constants.Coin;
import Exceptions.ItemNotFoundException;
import Objects.*;

import static Constants.Constants.*;
import static Services.VendingMachinePrintService.*;

public class Main {
    public static void main(String[] args) {
        // initiate vending machine
        var vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);

        // Admin: check state
        printState(vm);

        try {
            // Admin: add coin to be spare change coins that he prepared
            for (var coin: changeCoin.entrySet()) {
                vm.addCoins(coin.getKey(), coin.getValue());
            }
            // Admin: add more coins
            vm.addCoins(Coin.ONE_PENNY, 50);
            vm.addCoins(Coin.TWO_PENCE, 50);
            vm.addCoins(Coin.FIVE_PENCE, 50);
            vm.addCoins(Coin.TEN_PENCE, 50);
            vm.addCoins(Coin.TWENTY_PENCE, 50);
        } catch (Exception e) {
            printException(e);
        }

        // Admin: check coins in the machine
        printMachineBalance(vm);

        try {
            // Admin: add Items from stock
            for (var item : itemStock.entrySet()) {
                vm.addItem(item.getKey(), item.getValue());
            }

            // Admin: add another other item that left
            vm.addItem(new Item("04", "Coffee", 1.85), 10);
        } catch (Exception e) {
            printException(e);
        }

        // Admin: check shelves
        printShelf(vm);

        // Admin: set the state of machine to be Ready meaning ready for customer to use this machine
        vm.startOrReset();

        // Admin: withdraw money while machine is in the ready state
        try {
            vm.withdrawCoins();
        } catch (Exception e) {
            printException(e);
        }

        // Admin: check coins in the machine
        printMachineBalance(vm);

        // Admin: check shelves
        printShelf(vm);

        // Case I: insert Coin before select code
        // Customer:
        vm.insertCoin(Coin.ONE_POUND);

        // Customer: Select item
        try {
            // Step 1: select code that first time and valid
            vm.selectItem("01");
            // Step 2: change code
            vm.selectItem("02");
            // Step 3: select invalid code
            vm.selectItem("05");
        } catch (Exception e) {
            printException(e);
        }
        // Case II: select code before insert Coin

        // End of the day
        // Admin: set State of the machine
        try {
            vm.breakToMaintenance();
        } catch (Exception e) {
            printException(e);
        }
    }
}
