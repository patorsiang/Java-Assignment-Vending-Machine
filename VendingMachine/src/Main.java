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
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }

        // Admin: adding coins from saving
        try {
            for (var coin: changeCoin.entrySet()) {
                vm.addCoins(coin.getKey(), coin.getValue());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Admin: breaking to maintain
        try {
            vm.breakToMaintenance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Admin: withdrawing coin
        try {
            vm.withdrawCoins();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
