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
            vm.setCustomerRole();
            vm.insertCoin(coin);
        } catch (Exception e) {
           printService.printError(e);
        }
    }

    @Override
    public void selectItem(String code) {
        try {
            vm.setCustomerRole();
            vm.selectItem(code);
        } catch (Exception e) {
           printService.printError(e);
        }
    }

    @Override
    public void requestRefund() {
        try {
            vm.setCustomerRole();
            vm.requestRefund();
        } catch (Exception e) {
           printService.printError(e);
        }
    }

    @Override
    public void requestPurchaseItem() {
        try {
            vm.setCustomerRole();
            vm.requestPurchaseItem();
        } catch (Exception e) {
           printService.printError(e);
        }
    }

    @Override
    public void requestChange() {
        try {
            vm.setCustomerRole();
            vm.requestChange();
        } catch (Exception e) {
           printService.printError(e);
        }
    }

    @Override
    public void collect() {
        try {
            vm.setCustomerRole();
            vm.collect();
        } catch (Exception e) {
           printService.printError(e);
        }
    }
}
