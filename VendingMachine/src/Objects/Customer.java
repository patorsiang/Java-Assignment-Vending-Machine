package Objects;

import Constants.Coin;
import Interfaces.CustomerAction;

public class Customer extends User implements CustomerAction {

    public Customer(VendingMachine vm) {
        super(vm);
    }

    @Override
    public void insertCoin(Coin coin) {
        try {
            vm.setUserRole(false);
            vm.insertCoin(coin);
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void selectItem(String code) {
        try {
            vm.setUserRole(false);
            vm.selectItem(code);
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void requestRefund() {
        try {
            vm.setUserRole(false);
            vm.requestRefund();
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void requestPurchaseItem() {
        try {
            vm.setUserRole(false);
            vm.requestPurchaseItem();
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void requestChange() {
        try {
            vm.setUserRole(false);
            vm.requestChange();
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void collect() {
        try {
            vm.setUserRole(false);
            vm.collect();
        } catch (Exception e) {
            vm.printError(e);
        }
    }
}
