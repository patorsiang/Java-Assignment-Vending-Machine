package Objects;

import Constants.Coin;
import Interfaces.AdminAction;

/**
 * Represents an admin user responsible for
 * * adding items with specified quantities,
 * * starting/resetting the machine,
 * * entering maintenance mode,
 * * adding coins,
 * * and withdrawing balance.
 * All these methods in the Admin have to call setAdminRole to tell the vending machine that This is Admin
 */
public class Admin extends User implements AdminAction {

    /**
     * Constructor for the Admin.
     * to create an admin
     *
     * @param vm aim to receive VendingMachine object same as Customer
     */
    public Admin(VendingMachine vm) {
        super(vm);
    }

    /**
     * called the addCoins from the vending machine
     *
     * @param coin is a type of coin in the UK
     * @param amount a number of the coin
     */
    @Override
    public void addCoins(Coin coin, int amount) {
        try {
            vm.setAdminRole();
            vm.addCoins(coin,amount);
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the withdrawCoins from the vending machine
     */
    @Override
    public void withdrawCoins() {
        try {
            vm.setAdminRole();
            vm.withdrawCoins();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the addItem from the vending machine
     *
     * @param item Item object
     * @param amount a number of the Item object
     */
    @Override
    public void addItem(Item item, int amount) {
        try {
            vm.setAdminRole();
            vm.addItem(item, amount);
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the startOrReset from the vending machine
     */
    @Override
    public void startOrReset()  {
        try {
            vm.setAdminRole();
            vm.startOrReset();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the breakToMaintenance from the vending machine
     */
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