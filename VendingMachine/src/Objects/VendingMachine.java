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

public class VendingMachine extends VendingMachineService implements AdminAction, CustomerAction {

    public VendingMachine(int maxCapacity) {
        super(maxCapacity);
    }

    @Override
    public void addCoins(Coin coin, int amount) {
        if (!isAdmin) {
            throw new SecurityException("Unauthorized access: Only admin can add coins.");
        }
        System.out.println(">>> Admin Adding " + amount + " " + coin + " coin" + (amount > 1 ? "s" : ""));

        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + amount);
        machineBalance = machineBalance.add(coin.getValue().multiply(BigDecimal.valueOf(amount)));
        printState();
    }

    @Override
    public void withdrawCoins() throws InvalidMachineStateException {
        if (!isAdmin) {
            throw new SecurityException("Unauthorized access: Only admin can withdraw coins.");
        }
        System.out.println(">>> Admin Withdrawing Coins");

        if (state != VendingMachineState.IDLE) {
            throw new InvalidMachineStateException("This is not a time to withdraw Coins, State should be Idle");
        }

        System.out.println("This is the amount that you withdrew");
        printBalance("Machine", machineBalance, spareCoins);

        machineBalance = BigDecimal.ZERO;
        spareCoins.clear();
    }

    @Override
    public void addItem(Item item, int amount) throws LimitExceededException, InvalidMachineStateException {
        if (!isAdmin) {
            throw new SecurityException("Unauthorized access: Only admin can add items.");
        }
        System.out.println(">>> Admin Adding " + item + " to " + amount);

        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        } else if (amount > remainingCapacity) {
            throw new LimitExceededException("Maximum capacity exceeded");
        }

        if (state != VendingMachineState.IDLE) {
            throw new InvalidMachineStateException("This is not a time to add Items, State should be Idle");
        }

        remainingCapacity -= amount;

        shelf.put(item, shelf.getOrDefault(item, 0) + amount);
        printState();
    }

    @Override
    public void startOrReset() throws InvalidMachineStateException {
        if (!isAdmin) {
            throw new SecurityException("Unauthorized access: Only admin can add reset.");
        }
        resetProcess();
    }

    @Override
    public void breakToMaintenance() throws InvalidMachineStateException {
        if (!isAdmin) {
            throw new SecurityException("Unauthorized access: Only admin can break to maintenance.");
        }

        System.out.println(">>> Admin Breaking out of maintenance");

        if (!List.of(VendingMachineState.READY, VendingMachineState.IDLE).contains(state)) {
            throw new InvalidMachineStateException("This is not a time to break out of maintenance");
        }

        state = VendingMachineState.IDLE;
        printState();
    }

    @Override
    public void insertCoin(@NotNull Coin coin) throws InvalidMachineStateException {
        System.out.println(">>> Customer inserting a " + coin + " coin");
        if (!List.of(VendingMachineState.READY, VendingMachineState.PURCHASING).contains(state)) {
            throw new InvalidMachineStateException("The Vending Machine is unavailable. Try again Later.");
        }
        state = VendingMachineState.PURCHASING;
        customerBalance = customerBalance.add(coin.getValue());
        currentBalance = currentBalance.add(coin.getValue());
        customerCoins.put(coin, customerCoins.getOrDefault(coin, 0) + 1);
        printCurrentState();
    }

    @Override
    public void selectItem(String code) throws ItemNotFoundException, InvalidMachineStateException, OutOfShelfException {
        System.out.println(">>> Customer selecting item, " + code);
        if (!List.of(VendingMachineState.READY, VendingMachineState.PURCHASING).contains(state)) {
            throw new InvalidMachineStateException("The Vending Machine is unavailable. Try again Later.");
        }

        var selectedItem = getItemByCode(code);

        if (selectedItem == null) {
            throw new ItemNotFoundException("Item not found");
        }

        if (shelf.getOrDefault(selectedItem, 0) < 1) {
            throw new OutOfShelfException("This item, " + selectedItem.code() + selectedItem.name() + ", is out of shelf.");
        }

        if (this.selectedItem != null) {
            currentBalance = currentBalance.add(this.selectedItem.price());
        }

        state = VendingMachineState.PURCHASING;
        this.selectedItem = selectedItem;
        currentBalance = currentBalance.subtract(selectedItem.price());
        printCurrentState();
    }

    @Override
    public void requestRefund() throws RefundedException {
        System.out.println(">>> Customer requesting refund");

        if (state != VendingMachineState.PURCHASING) {
            throw new RefundedException("Can't request refund at this state");
        }

        returnCoins.putAll(customerCoins);
        customerCoins.clear();
        state = VendingMachineState.CANCELED;
        printCurrentState();

        if (!returnCoins.isEmpty()) {
            System.out.println("clink-clink");
            printBalance("Return", customerBalance, returnCoins);
        }
    }

    @Override
    public void requestPurchaseItem() throws PurchasedException, InsufficientSpareChangeCoinsException {
        System.out.println(">>> Customer requesting purchase item");
        // Case: haven't selected item yet.
        if (selectedItem == null) {
            throw new PurchasedException("No item selected");
        }

        // Case: not enough money
        if (currentBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new PurchasedException("Not enough money");
        }

        // Case: Success
        state = VendingMachineState.PURCHASED;

        returnItem = selectedItem;
        selectedItem = null;

        shelf.put(returnItem, shelf.get(returnItem) - 1);
        remainingCapacity += 1;

        for (var coin : customerCoins.keySet()) {
            spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + customerCoins.get(coin));
            machineBalance = machineBalance.add(coin.getValue().multiply(BigDecimal.valueOf(customerCoins.get(coin))));
        }

        customerCoins.clear();
        printCurrentState();

        if (returnItem != null) {
            System.out.println("Putting the item in return bucket......");
            System.out.println("clunk-clink");
            System.out.println("Return item: " + returnItem);
        }

        requestChange();
    }

    @Override
    public void requestChange() throws InsufficientSpareChangeCoinsException {
        System.out.println(">>> Customer requesting changes");
        if (state != VendingMachineState.PURCHASED) {
            throw new InsufficientSpareChangeCoinsException("Can't request change yet");
        }

        if (currentBalance.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("No change");
        } else {
            var returnBalance = currentBalance;
            for (var coin : spareCoins.keySet()) {
                var amountCoin = currentBalance.divide(coin.getValue(), RoundingMode.FLOOR).intValue();
                var amountOfCoin = spareCoins.getOrDefault(coin, 0);
                if (amountCoin != 0 && amountOfCoin != 0) {
                    var amount = amountOfCoin < amountCoin ? amountOfCoin : amountCoin;
                    var value = coin.getValue().multiply(BigDecimal.valueOf(amount));

                    currentBalance = currentBalance.subtract(value);

                    returnCoins.put(coin, amount);
                    spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) - amount);
                }
            }

            if (currentBalance.compareTo(BigDecimal.ZERO) > 0) {
                throw new InsufficientSpareChangeCoinsException("Can't request change yet, Please Contact Admin");
            }

            System.out.println("clink-clink");
            printBalance("Return", returnBalance, returnCoins);
        }
    }

    @Override
    public void collect() throws InvalidMachineStateException {
        if (!List.of(VendingMachineState.PURCHASED, VendingMachineState.CANCELED).contains(state)) {
            throw new InvalidMachineStateException("The process is not finished yet. There are nothing in the return bucket.");
        } else if (state == VendingMachineState.PURCHASED && currentBalance.compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidMachineStateException("The process is not finished yet. Please Contact Admin");
        }

        if (state == VendingMachineState.CANCELED) {
            System.out.println(">>> Customer collecting refunded coin");
        } else if (state == VendingMachineState.PURCHASED) {
            System.out.println(">>> Customer collecting items and change coin");
        }
        state = VendingMachineState.PURCHASED_COMPLETED;
        System.out.println("Thank you for purchasing! See you later!");
        resetProcess();
    }

}
