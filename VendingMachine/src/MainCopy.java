//import Constants.Coin;
//import Objects.*;
//
//import static Constants.Constants.*;
//
//public class MainCopy {
//    public static void main(String[] args) {
//        // initiate vending machine
//        var vm = new VendingMachine(LIMIT_ITEM_QUANTITY_IN_VM);
//
//        // Admin: check state
//        vm.printState();
//
//
//        // Admin: add coin to be spare change coins that he prepared
//        for (var coin : changeCoin.entrySet()) {
//            vm.addCoins(coin.getKey(), coin.getValue());
//        }
//        // Admin: add more coins
//        vm.addCoins(Coin.ONE_PENNY, 50);
//        vm.addCoins(Coin.TWO_PENCE, 50);
//        vm.addCoins(Coin.FIVE_PENCE, 50);
//        vm.addCoins(Coin.TEN_PENCE, 50);
//        vm.addCoins(Coin.TWENTY_PENCE, 50);
//
//        // Admin: check coins in the machine
//        vm.printMachineBalance();
//
//
//        // Admin: add Items from stock
//        for (var item : itemStock.entrySet()) {
//            vm.addItem(item.getKey(), item.getValue());
//        }
//
//        // Admin: add another other item that left
//        vm.addItem(new Item("04", "Coffee", 1.85), 10);
//
//        // Admin: check shelves
//        vm.printShelf();
//
//        // Admin: set the state of machine to be Ready meaning ready for customer to use this machine
//        vm.startOrReset();
//
//        // Admin: withdraw money while machine is in the ready state
//        vm.withdrawCoins();
//
//        // Admin: check coins in the machine
//        vm.printMachineBalance();
//
//        // Admin: check shelves
//        vm.printShelf();
//
//        // Customer look at the display
//        vm.printShelf();
//        // Case I: insert Coin before select code
//        // Customer:
//        vm.insertCoin(Coin.ONE_POUND);
//
//        // Customer try to request purchase before select item
//        vm.requestPurchaseItem();
//
//        // Customer: Select item
//        // Step 1: select code that first time and valid
//        vm.selectItem("01");
//        // Step 2: change code
//        vm.selectItem("02");
//        // Step 3: select invalid code
//        vm.selectItem("05");
//
//        // Customer request refund
//        vm.requestRefund();
//
//        // Case II: select code before insert Coin
//        vm.selectItem("03");
//
//        // Customer insert Coins
//        vm.insertCoin(Coin.TWO_PENCE);
//        vm.insertCoin(Coin.FIFTY_PENCE);
//
//        // Customer request purchase item, but the Customer balance is not enough
//        vm.requestPurchaseItem();
//
//        // Customer insert more coins to be over the selected item price
//        vm.insertCoin(Coin.TEN_PENCE);
//        vm.insertCoin(Coin.ONE_POUND);
//
//        // Customer request purchase item
//        vm.requestPurchaseItem();
//
//        // Customer try to select item before complete a purchase
//        vm.selectItem("01");
//
//        // Admin try to add item during user using
//        vm.addItem(new Item("04", "Coffee", 1.85), 10);
//
//        // Customer request Change
//        vm.requestChange();
//
//        // End of the day
//        // Admin: set State of the machine
//        vm.breakToMaintenance();
//
//    }
//}
