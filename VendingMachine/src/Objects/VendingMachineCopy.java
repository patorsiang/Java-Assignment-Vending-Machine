//package Objects;
//
//import Constants.*;
//import Exceptions.*;
//import Interface.*;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.io.InvalidObjectException;
//import java.math.BigDecimal;
//import java.util.*;
//
//public class VendingMachineCopy implements AdminAction, CustomerAction {
//    // maxCapacity: configurable in terms of the maximum number of items that it can hold
//    // if it has been set up, these limits should not change
//    private final int maxCapacity;
//    private int remainCapacity;
//    // a variety of items: Item and amount of it
//    private final Map<Item, Integer> shelf;
//
//    private final Map<Coin, Integer> spareCoins;
//    private final Map<Coin, Integer> userCoins;
//    private final Map<Coin, Integer> changeCoins;
//
//    private BigDecimal machineBalance;
//    private BigDecimal currentBalance;
//    private BigDecimal userBalance;
//    private Item selectedItem;
//
//    private VendingMachineState currentState; // Track the current state of the machine
//
//    public VendingMachineCopy(int maxCapacity) {
//        if (maxCapacity <= 0) {
//            throw new IllegalArgumentException("Maximum capacity must be greater than 0");
//        }
//        this.maxCapacity = maxCapacity;
//        this.remainCapacity = 0;
//        this.shelf = new HashMap<>();
//        this.spareCoins = new EnumMap<>(Coin.class);
//        this.changeCoins = new EnumMap<>(Coin.class);
//        this.userCoins = new EnumMap<>(Coin.class);
//        this.machineBalance = new BigDecimal("0.00");
//        this.userBalance = new BigDecimal("0.00");
//        this.currentBalance = new BigDecimal("0.00");
//        this.selectedItem = null;
//        this.currentState = VendingMachineState.IDLE; // Initially the machine is idle
//    }
//
//    public void startOrReset() {
//        userBalance = BigDecimal.valueOf(0.0);
//        currentBalance = BigDecimal.valueOf(0.0);
//        selectedItem = null;
//        userCoins.clear();
//        currentState = VendingMachineState.READY;
//    }
//
//
//    @Override
//    public void addCoins(Coin coin, int amount) {
//        printAction("Admin adds Coins");
//        try {
//            if (amount < 0) {
//                throw new IllegalArgumentException("Amount must be greater than 0");
//            }
//            if (currentState == VendingMachineState.IDLE) {
//                spareCoins.put(coin, spareCoins.getOrDefault(coin, 0) + amount);
//                machineBalance.add(BigDecimal.valueOf(coin.getValue() * amount));
//            } else {
//                throw new InvalidMachineStateException("This is not a time to add Coins, State should be Idle");
//            }
//            System.out.println(coin + " +" + amount);
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void withdrawCoins() {
//        printAction("Admin withdraws coins.");
//        try {
//            if (currentState == VendingMachineState.IDLE) {
//                machineBalance = BigDecimal.valueOf(0.0);
//                spareCoins.clear();
//            } else {
//                throw new InvalidMachineStateException("This is not a time to withdraw Coins, State should be Idle");
//            }
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void addItem(Item item, int amount) {
//        printAction("Admin adds item.");
//        try {
//            if (amount < 0) {
//                throw new IllegalArgumentException("Amount must be greater than 0");
//            }
//            if (currentState == VendingMachineState.IDLE) {
//                remainCapacity += amount;
//                if (remainCapacity > maxCapacity) {
//                    throw new LimitExceededException("Cannot add items more than Machine Capacity, not more than " + maxCapacity);
//                }
//                shelf.put(item, shelf.getOrDefault(item, 0) + amount);
//            } else {
//                throw new InvalidMachineStateException("This is not a time to add Items, State should be Idle");
//            }
//            System.out.println(item + " +" + amount);
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void breakToMaintenance() {
//        printAction("Admin breaks machine to maintenance.");
//        try {
//            if (currentState == VendingMachineState.READY || currentState == VendingMachineState.IDLE) {
//                currentState = VendingMachineState.IDLE;
//            } else {
//                throw new InvalidMachineStateException("It's not ready to maintain because it is in the time that the customer is using");
//            }
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void insertCoin(@NotNull Coin coin) {
//        printAction("Customer inserted a " + coin + " coin.");
//        try {
//            if (currentState != VendingMachineState.IDLE && currentState != VendingMachineState.PURCHASED) {
//                currentState = VendingMachineState.COIN_INSERTED;
//                userBalance.add(BigDecimal.valueOf(coin.getValue()));
//                currentBalance.add(BigDecimal.valueOf(coin.getValue()));
//                userCoins.put(coin, userCoins.getOrDefault(coin, 0) + 1);
//                printInfo();
//            } else {
//                throw new InvalidMachineStateException("During the request purchase is not completed, cannot insert coin now.");
//            }
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void selectItem(String code) {
//        printAction("Customer selected " + code);
//        try {
//            if (currentState != VendingMachineState.IDLE && currentState != VendingMachineState.PURCHASED) {
//                var item = getItemByCode(code);
//
//                if (item != null) {
//                    if (shelf.getOrDefault(item, 0) == 0) {
//                        throw new OutOfShelfException("This " + item.code() + " " + item.name() + " is out of shelf");
//                    }
//                    if (selectedItem != null) {
//                        currentBalance.add(BigDecimal.valueOf(selectedItem.price() - item.price()));
//                    } else {
//                        currentBalance.subtract(BigDecimal.valueOf(item.price()));
//                    }
//                    selectedItem = item;
//                    currentState = VendingMachineState.ITEM_SELECTED;
//                }
//
//                printInfo();
//
//                if (item == null) {
//                    throw new ItemNotFoundException("Item not found");
//                }
//            } else {
//                throw new InvalidMachineStateException("During the request purchase is not completed, cannot select new item now.");
//            }
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void requestRefund() {
//        printAction("Customer requested refund.");
//        try {
//            if (currentState != VendingMachineState.IDLE && currentState != VendingMachineState.PURCHASED) {
//                currentState = VendingMachineState.CANCELED;
//                System.out.println("Your coins placed in return bucket");
//
//                printBalance("User", userBalance.doubleValue(), userCoins);
//            } else {
//                throw new InvalidMachineStateException("During the request purchase is not completed, cannot select new item now.");
//            }
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void requestPurchaseItem() {
//        printAction("Customer requested purchase item.");
//        try {
//            if (currentState == VendingMachineState.IDLE) {
//                throw new InvalidMachineStateException("This machine is not opened yet");
//            } else if (currentState == VendingMachineState.PURCHASED) {
//                throw new InvalidMachineStateException("Current customer haven't finish purchase process yet");
//            }
//
//            if (currentBalance.equals(BigDecimal.valueOf(0.0))) {
//                throw new InsufficientHoldedCoinsException("Your Balance is not enough to purchase this item");
//            } else if (selectedItem != null) {
//                currentState = VendingMachineState.PURCHASED;
//                System.out.println("Your item, " + selectedItem.name() + ", is in return bucket");
//                shelf.put(selectedItem, shelf.get(selectedItem) - 1);
//                userBalance.subtract(BigDecimal.valueOf(selectedItem.price()));
//            }
//            printInfo();
//        } catch (Exception e) {
//            printException(e);
//        }
//    }
//
//    @Override
//    public void requestChange() {
//        printAction("Customer requested changes");
//        try {
//            for (var coin : spareCoins.entrySet()) {
//                if (userBalance.equals(BigDecimal.valueOf(0.0))) {
//                    break;
//                }
//                if (coin.getValue() != 0) {
//                    int amount = userBalance.divide(BigDecimal.valueOf(coin.getKey().getValue())).intValue();
//                    System.out.println(amount);
//                    if (amount > 0) {
//                        var diffAmount = coin.getValue() - amount;
//                        if (diffAmount >= 0) {
//                            changeCoins.put(coin.getKey(), amount);
//                            System.out.println(userBalance + " " + amount * coin.getKey().getValue());
//                            userBalance.subtract(BigDecimal.valueOf(amount * coin.getKey().getValue()));
//                            spareCoins.put(coin.getKey(), diffAmount);
//                        } else {
//                            changeCoins.put(coin.getKey(), coin.getValue());
//                            userBalance.subtract(BigDecimal.valueOf(coin.getValue() * coin.getKey().getValue()));
//                            spareCoins.put(coin.getKey(), 0);
//                        }
//                    }
//                }
//            }
//
//            if (userBalance.equals(BigDecimal.valueOf(0.0))) {
//                throw new InsufficientSpareChangeCoinsException("Spare Change is not enough");
//            }
//        } catch (Exception e) {
//            printException(e);
//        }
//        System.out.println(changeCoins);
//    }
//
//    @Override
//    public void collect() {
//
//    }
//
//
//    public void printMachineBalance() {
//        printBalance("Machine", machineBalance.doubleValue(), spareCoins);
//    }
//
//    public void printState() {
//        System.out.println("State: " + currentState);
//    }
//
//    public void printShelf() {
//        System.out.printf("--------------------------------------%n");
//        System.out.printf("| %-4s | %-10s | %-5s | %-6s |%n", "Code", "Name", "Price", "Amount");
//        System.out.printf("--------------------------------------%n");
//        var items = shelf.keySet().stream().sorted(Comparator.comparing(Item::code)).toList();
//        for (var item : items) {
//            var itemAmount = shelf.get(item);
//            System.out.printf("| %-4s | %-10s | %5.2f | %-6d |%n", item.code(), item.name(), item.price(), itemAmount);
//        }
//        System.out.printf("--------------------------------------%n");
//    }
//
//    private @Nullable Item getItemByCode(String code) {
//        for (var item : shelf.keySet()) {
//            if (item.code().equals(code)) {
//                return item;
//            }
//        }
//        return null;
//    }
//
//    private void printAction(String action) {
//        System.out.println("--------------------------------------");
//        System.out.println("Interaction: " + action);
//        System.out.println("--------------------------------------");
//    }
//
//    private void printException(@NotNull Exception e) {
//        System.out.println("Error: " + e.getMessage());
//    }
//
//    private void printBalance(String label, double balance, Map<Coin, Integer> Coins) {
//        System.out.printf("%s Balance: %.2f%n", label, balance);
//        if (balance > 0) {
//            System.out.printf("----------------------------%n");
//            System.out.printf("| %-15s | %-6s |%n", "Coin", "Amount");
//            System.out.printf("----------------------------%n");
//            for (var entry : Coins.entrySet()) {
//                System.out.printf("| %-15s | %-6d |%n", entry.getKey(), entry.getValue());
//            }
//            System.out.printf("----------------------------%n");
//        }
//    }
//
//    private void printInfo() {
//        System.out.println("--------------------------------------");
//        System.out.printf("User Balance: %.2f%n", userBalance);
//        System.out.printf("Current Balance: %.2f%n", currentBalance);
//        System.out.println("Selected Item: " + selectedItem.code() + " " + selectedItem.name());
//        System.out.println("--------------------------------------");
//    }
//}
