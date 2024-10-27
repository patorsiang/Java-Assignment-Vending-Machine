package Objects;

import Constants.Coin;
import Interfaces.CustomerAction;

/**
 * Represents a customer interacting with the vending machine;
 * The customer can
 * * insert coins,
 * * select items,
 * * request refunds (cancel state),
 * * request change,
 * * and collect coins and/or items.
 * All these methods in the Admin have to call setAdminRole to tell the vending machine that This is Admin
 */
public class Customer extends User implements CustomerAction {

    /**
     * Constructor for the Customer.
     * to create a customer
     *
     * @param vm aim to receive VendingMachine object same as Admin
     */
    public Customer(VendingMachine vm) {
        super(vm);
    }

    /**
     * called the insertCoin from the vending machine
     * like the user inserts one coin for one time
     *
     * @param coin assign that is the physical coin
     */
    @Override
    public void insertCoin(Coin coin) {
        try {
            vm.setCustomerRole();
            vm.insertCoin(coin);
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the selectItem from the vending machine
     *
     * @param code unique code in the vending machine
     */
    @Override
    public void selectItem(String code) {
        try {
            vm.setCustomerRole();
            vm.selectItem(code);
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the requestRefund from the vending machine
     */
    @Override
    public void requestRefund() {
        try {
            vm.setCustomerRole();
            vm.requestRefund();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the requestPurchaseItem from the vending machine
     */
    @Override
    public void requestPurchaseItem() {
        try {
            vm.setCustomerRole();
            vm.requestPurchaseItem();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the requestChange from the vending machine
     */
    @Override
    public void requestChange() {
        try {
            vm.setCustomerRole();
            vm.requestChange();
        } catch (Exception e) {
            printService.printError(e);
        }
    }

    /**
     * called the collect from the vending machine
     */
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