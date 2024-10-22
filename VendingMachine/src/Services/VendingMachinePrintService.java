package Services;

import Objects.Item;
import Objects.VendingMachine;

import java.util.Comparator;

public class VendingMachinePrintService {
    public static void printShelf(VendingMachine vm) {
        var shelf = vm.getShelf();
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-4s | %-10s | %-5s | %-6s |%n", "Code", "Name", "Price", "Amount");
        System.out.printf("--------------------------------------%n");
        var items = shelf.keySet().stream().sorted(Comparator.comparing(Item::code)).toList();
        for (var item : items) {
            var itemAmount = shelf.get(item);
            System.out.printf("| %-4s | %-10s | %5.2f | %-6d |%n", item.code(), item.name(), item.price(), itemAmount);
        }
        System.out.printf("--------------------------------------%n");
    }


    public static void printMachineBalance(VendingMachine vm) {
        var machineBalance = vm.getMachineBalance();
        var spareChangeCoins = vm.getSpareChangeCoins();
        System.out.printf("Balance in Machine: %.2f%n", machineBalance);
        if (machineBalance > 0) {
            System.out.printf("----------------------------%n");
            System.out.printf("| %-15s | %-6s |%n", "Coin", "Amount");
            System.out.printf("----------------------------%n");
            for (var entry : spareChangeCoins.entrySet()) {
                System.out.printf("| %-15s | %-6d |%n", entry.getKey(), entry.getValue());
            }
            System.out.printf("----------------------------%n");
        }
    }

    public static void printAction(String action) {
        System.out.println("Interaction: " + action);
    }

    public static void printState(VendingMachine vm) {
        var currentState = vm.getCurrentState();
        System.out.println("State: " + currentState);
    }

    public static void printException(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
