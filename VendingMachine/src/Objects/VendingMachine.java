package Objects;

import Exceptions.*;
import Interfaces.AdminAction;
import Interfaces.CustomerAction;
import Constants.Coin;
import Constants.VendingMachineState;
import Services.VendingMachineService;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * The vending machine class provides a facility to manage attributes in vending
 * following the List of necessary actions of Admin and Customer
 */
public class VendingMachine extends VendingMachineService implements AdminAction, CustomerAction {

    /**
     * Construct VendingMachine
     *
     * @param maxCapacity capacity limit capacity of vending machine
     */
    public VendingMachine(int maxCapacity) {
        super(maxCapacity); // set maxCapacity through the VendingMachineService
    }

    /**
     * For Admin to add spare coins for customers to ask for change.
     *
     * @param coin   assign that is the physical coin
     * @param amount the number of the coin
     */
    @Override
    public void addCoins(Coin coin, int amount) {
        // check permission
        checkPermission("add coins.");

        // show interaction that Admin adding how many coins
        System.out.println(">>> Admin Adding " + amount + " " + coin + " coin" + (amount > 1 ? "s" : ""));

        // checking the amount must be positive
        if (amount < 0) {
            // throw IllegalArgumentException
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        // update the spare coin; increase the number of the coin
        spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + amount);
        // update machine balance
        machineBalance = machineBalance.add(coin.getValue().multiply(BigDecimal.valueOf(amount)));
        // call print state in VendingMachineService for displaying the overall information in Vending Machine
        printState();
    }

    /**
     * For Admin to take all coins in the vending machine out
     *
     * @throws InvalidMachineStateException state should be idle before using this method
     */
    @Override
    public void withdrawCoins() throws InvalidMachineStateException {
        // check permission
        checkPermission("withdraw coins.");

        // show interaction that Admin withdrawing how many coins
        System.out.println(">>> Admin Withdrawing Coins");

        // check state should be in the idle
        checkState("This is not a time to withdraw Coins, State should be Idle", VendingMachineState.IDLE);

        // display the result
        System.out.println("This is the amount that you withdrew");

        // show the total amount using a pattern by calling the method in VendingMachineService
        printBalance("Machine", machineBalance, spareCoins);

        //Update machine balance to be zero
        machineBalance = BigDecimal.ZERO;

        // update spare coins by clearing it all
        spareCoins.clear();

        // call print state in VendingMachineService for displaying the overall information in Vending Machine
        printState();
    }

    /**
     * For Admin to add some ItemsItem into the Vending Machine
     *
     * @param item   Item object that Admin wants to add
     * @param amount several Item
     * @throws LimitExceededException       if reaching the limit of the vending machine
     * @throws InvalidMachineStateException The state should be idle when Admin adds the items
     */
    @Override
    public void addItem(Item item, int amount) throws LimitExceededException, InvalidMachineStateException {
        // check permission
        checkPermission("withdraw items.");

        // show interaction that Admin adding items
        System.out.println(">>> Admin Adding " + item + " to " + amount);

        // check duplicate unique code
        if (codeToItemMap.containsKey(item.code()) && codeToItemMap.get(item.code()) != item) {
            throw new IllegalArgumentException("You cannot add same code item to the shelf.");
        }

        // checking amount param, not less than zero
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        // checking reaching vending machine capacity yet
        else if (amount > remainingCapacity) {
            throw new LimitExceededException("Maximum capacity exceeded");
        }

        //Checking state of the machine should be Idle
        checkState("This is not a time to add Items, State should be Idle", VendingMachineState.IDLE);

        // passing all conditions, minus remaining capacity by amount of the Item
        remainingCapacity -= amount;

        //Update the Item on the shelf
        shelf.put(item, shelf.getOrDefault(item, 0) + amount);
        codeToItemMap.put(item.code(), item);

        // call print state in VendingMachineService for displaying the overall information in Vending Machine
        printState();
    }

    /**
     * For Admin to start or reset the vending machine
     *
     * @throws InvalidMachineStateException, the Exception that throws from the Reset Process
     */
    @Override
    public void startOrReset() throws InvalidMachineStateException {
        // check permission
        checkPermission("reset.");

        // show interaction that Admin starts the machine
        System.out.println(">>> Admin Starting the system");

        // use resetProcess in VendingMachineService
        resetProcess();
    }

    /**
     * For Admin to set the state back to idle
     *
     * @throws InvalidMachineStateException, but the state should be in the process that the Customer purchases an item
     */
    @Override
    public void breakToMaintenance() throws InvalidMachineStateException {
        // check permission
        checkPermission("break to maintenance.");

        // show interaction that Admin would like to pause the machine
        System.out.println(">>> Admin Breaking out of maintenance");

        //Check the state, aim not to disturb the purchase process
        checkState("This is not a time to break out of maintenance", VendingMachineState.READY, VendingMachineState.IDLE);

        // passing all conditions, set state to IDLE
        state = VendingMachineState.IDLE;

        // call print state in VendingMachineService for displaying the overall information in Vending Machine
        printState();
    }

    /**
     * For Customers, insert coin
     *
     * @param coin assign it as the physical coin
     * @throws InvalidMachineStateException if they finish their purchase or cancel
     */
    @Override
    public void insertCoin(@NotNull Coin coin) throws InvalidMachineStateException {
        //Show interaction that the Customer inserted a coin
        System.out.println(">>> Customer inserting a " + coin + " coin");

        // checking the state should be READY and PURCHASING
        checkState("The Vending Machine is unavailable. Try again later.", VendingMachineState.READY, VendingMachineState.PURCHASING);

        // update state to be PURCHASING
        state = VendingMachineState.PURCHASING;

        // update customer balance, current balance, and customer coins
        customerBalance = customerBalance.add(coin.getValue());
        currentBalance = currentBalance.add(coin.getValue());
        customerCoins.put(coin, customerCoins.getOrDefault(coin, 0) + 1);

        // print overview that the Customer should update
        printCurrentState();
    }

    /**
     * For Customer to select an Item by code to purchase
     *
     * @param code unique code in the shelf
     * @throws ItemNotFoundException        if the code is not found
     * @throws InvalidMachineStateException if it is out of the period to select the Item
     * @throws OutOfShelfException          if amount of item = 0
     */
    @Override
    public void selectItem(String code) throws ItemNotFoundException, InvalidMachineStateException, OutOfShelfException {
        // show interaction that Customer selected Item by code
        System.out.println(">>> Customer selecting item, " + code);

        // check state
        checkState("The Vending Machine is unavailable. Try again later.", VendingMachineState.READY, VendingMachineState.PURCHASING);

        // get the Item from code
        var selectedItem = codeToItemMap.get(code);

        // if the Customer select code does not match the items on the shelf
        if (selectedItem == null) {
            throw new ItemNotFoundException("Item not found");
        }

        // if the Item is in or out of stock
        if (shelf.getOrDefault(selectedItem, 0) < 1) {
            throw new OutOfShelfException("This item, " + selectedItem.code() + selectedItem.name() + ", is out of shelf.");
        }

        // get the current balance back if it subtracts from the last time
        if (this.selectedItem != null) {
            System.out.println("Change Selected Item from" + this.selectedItem + "to" + selectedItem);
            currentBalance = currentBalance.add(this.selectedItem.price());
        }

        // update the state and selected Item and current balance
        state = VendingMachineState.PURCHASING;
        this.selectedItem = selectedItem;
        currentBalance = currentBalance.subtract(selectedItem.price());

        // print overview that the Customer should update
        printCurrentState();
    }

    /**
     * For Customer to cancel and refund
     *
     * @throws InvalidMachineStateException if Customer has not selected an item or inserted a coin
     */
    @Override
    public void requestRefund() throws InvalidMachineStateException {
        //Show interaction that the Customer canceled
        System.out.println(">>> Customer requesting refund");

        // check state
        checkState("Can't request refund at this state", VendingMachineState.PURCHASING);

        // update returned Coins, state, customer Coins
        returnCoins.putAll(customerCoins);
        customerCoins.clear();
        state = VendingMachineState.CANCELED;
        currentBalance = BigDecimal.ZERO;

        // print overview that the Customer should update
        printCurrentState();

        // set the sound and show balance, which, in return, bucket
        if (!returnCoins.isEmpty()) {
            System.out.println("clink-clink");
            printBalance("Return", customerBalance, returnCoins);
        } else {
            state = VendingMachineState.PURCHASED_COMPLETED;
            resetProcess();
        }
    }

    /**
     * For Customers like click the purchase button
     *
     * @throws PurchasedException                    some error during the purchasing
     * @throws InsufficientSpareChangeCoinsException spare coin is not enough, cannot give change
     * @throws InvalidMachineStateException          check state
     */
    @Override
    public void requestPurchaseItem() throws PurchasedException, InsufficientSpareChangeCoinsException, InvalidMachineStateException {
        // show interaction that customer purchase
        System.out.println(">>> Customer requesting purchase item");

        // Check state
        checkState("Haven't insert a Coin and select an Item", VendingMachineState.PURCHASING);

        //Case: A customer has not selected the Item yet.
        if (selectedItem == null) {
            throw new PurchasedException("No item selected");
        }

        //CaseCase: not enough money
        if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new PurchasedException("Not enough money");
        }

        // Case: Success
        state = VendingMachineState.PURCHASED;

        returnItem = selectedItem;
        selectedItem = null;

        shelf.put(returnItem, shelf.get(returnItem) - 1);
        remainingCapacity += 1;

        for (var coin: customerCoins.keySet()) {
            spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + customerCoins.get(coin));
            machineBalance = machineBalance.add(coin.getValue().multiply(BigDecimal.valueOf(customerCoins.get(coin))));
        }

        customerCoins.clear();

        // print overview that the Customer should update
        printCurrentState();

        // dropping Item to return bucket
        if (returnItem != null) {
            System.out.println("Putting the item in return bucket......");
            System.out.println("clunk-clink");
            System.out.println("Return item: " + returnItem);
        }

        // auto request change
        requestChange();
    }

    /**
     * For Customer to request change manual
     *
     * @throws InsufficientSpareChangeCoinsException if the spare coin is not enough
     */
    @Override
    public void requestChange() throws InsufficientSpareChangeCoinsException, InvalidMachineStateException {
        // show interaction that Customer requests change
        System.out.println(">>> Customer requesting change");

        // check state
        checkState("Can't request change yet", VendingMachineState.PURCHASED);

        //In Case, insert coin enough
        if (currentBalance.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("No change");
        }
        //In Case, have to return change
        else {
            var returnBalance = currentBalance;
            var listOfSpareCoin = spareCoins.keySet().stream().sorted(Comparator.comparing(Coin::getValue).reversed())
                    .toList();

            // check with a coin in spare coin from the large value
            for (var coin: listOfSpareCoin) {
                // get the aspect amount
                var amountCoin = currentBalance.divide(coin.getValue(), RoundingMode.FLOOR).intValue();
                // get the actually amount
                var amountOfCoin = spareCoins.getOrDefault(coin, 0);
                // check that there are some coins in the spare coin bucket, and this coin can be used for returning as change
                if (amountCoin != 0 && amountOfCoin != 0) {

                    // check amount of coin; partition to return
                    var amount = amountOfCoin <= amountCoin ? amountOfCoin: amountCoin;
                    var value = coin.getValue().multiply(BigDecimal.valueOf(amount));

                    // update currentBalance, returnCoins, spareCoins
                    currentBalance = currentBalance.subtract(value);
                    returnCoins.put(coin, amount);
                    spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) - amount);
                }
            }

            //The spare coin is not enough
            if (currentBalance.compareTo(BigDecimal.ZERO) > 0) {
                throw new InsufficientSpareChangeCoinsException("Can't request change yet, Please Contact Admin");
            }

            //Add the sound like dropping the coin to the return bucket
            System.out.println("clink-clink");
            printBalance("Return", returnBalance, returnCoins);
        }
    }

    /**
     * finish the process and return the state to be ready
     *
     * @throws InvalidMachineStateException if there is nothing in the return bucket
     */
    @Override
    public void collect() throws InvalidMachineStateException {
        // check the state and current balance; it should return to Customer before state back to ready
        if (! List.of(VendingMachineState.PURCHASED, VendingMachineState.CANCELED).contains(state)) {
            throw new InvalidMachineStateException("The process is not finished yet. There are nothing in the return bucket.");
        } else if (state == VendingMachineState.PURCHASED && currentBalance.compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidMachineStateException("The process is not finished yet. Please Contact Admin");
        }

        // show interaction that Customer collecting coin and/or Item in return bucket
        if (state == VendingMachineState.CANCELED) {
            System.out.println(">>> Customer collecting refunded coin");
        } else if (state == VendingMachineState.PURCHASED) {
            System.out.println(">>> Customer collecting items and change coin");
        }

        // update the state, and set the process back to ready
        state = VendingMachineState.PURCHASED_COMPLETED;
        System.out.println("Thank you for purchasing! See you later!");
        resetProcess();
    }

}
