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
            vm.setAdminRole();
            vm.addCoins(coin,amount);
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    @Override
    public void withdrawCoins() {
        try {
            vm.setAdminRole();
            vm.withdrawCoins();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    @Override
    public void addItem(Item item, int amount) {
        try {
            vm.setAdminRole();
            vm.addItem(item, amount);
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    @Override
    public void startOrReset()  {
        try {
            vm.setAdminRole();
            vm.startOrReset();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    @Override
    public void breakToMaintenance() {
        try {
            vm.setAdminRole();
            vm.breakToMaintenance();
        } catch (Exception e) {
            printService.printError(e);
        }
    }
}
