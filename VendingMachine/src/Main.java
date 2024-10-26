import Constants.Coin;
import Objects.VendingMachine;

import static Constants.Constants.*;

public class Main {
    public static void main(String[] args) {
        // initiate vending machine
        var vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);

        // Admin: adding items from item stock
        try {
            for (var item: itemStock.entrySet()) {
                vm.addItem(item.getKey(), item.getValue());
            }

            for (var item: addOnItemStock.entrySet()) {
                vm.addItem(item.getKey(), item.getValue());
            }
        } catch (Exception e) {
           vm.printError(e);
        }

        // Admin: adding coins from saving
        try {
            for (var coin: changeCoin.entrySet()) {
                vm.addCoins(coin.getKey(), coin.getValue());
            }
        } catch (Exception e) {
            vm.printError(e);
        }

        // Admin: starting the system
        try {
            vm.startOrReset();
        } catch (Exception e) {
            vm.printError(e);
        }

        // Customer: purchasing with enough coin
        try {
            vm.insertCoin(Coin.ONE_POUND);
            vm.selectItem("02");
            vm.requestPurchaseItem();
            vm.requestChange();
            vm.collect();
        } catch (Exception e) {
            vm.printError(e);
        }

        // Admin: breaking to maintain
        try {
            vm.breakToMaintenance();
        } catch (Exception e) {
            vm.printError(e);
        }

        // Admin: withdrawing coin
        try {
            vm.withdrawCoins();
        } catch (Exception e) {
            vm.printError(e);
        }
    }
}
