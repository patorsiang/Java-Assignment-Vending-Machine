package Objects;

import Constants.Coin;
import Interfaces.AdminAction;

public class Admin extends User implements AdminAction {

    public Admin(VendingMachine vm) {
        super(vm);
    }

    @Override
    public void addCoins(Coin coin, int amount) {
        try {
            vm.setUserRole(true);
            vm.addCoins(coin,amount);
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void withdrawCoins() {
        try {
            vm.setUserRole(true);
            vm.withdrawCoins();
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void addItem(Item item, int amount) {
        try {
            vm.setUserRole(true);
            vm.addItem(item, amount);
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void startOrReset()  {
        try {
            vm.setUserRole(true);
            vm.startOrReset();
        } catch (Exception e) {
            vm.printError(e);
        }
    }

    @Override
    public void breakToMaintenance() {
        try {
            vm.setUserRole(true);
            vm.breakToMaintenance();
        } catch (Exception e) {
            vm.printError(e);
        }
    }
}
