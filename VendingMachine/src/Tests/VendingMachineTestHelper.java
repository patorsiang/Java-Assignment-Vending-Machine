package Tests;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import Constants.Coin;
import Constants.VendingMachineState;
import Objects.Item;
import Objects.VendingMachine;
import org.jetbrains.annotations.NotNull;

public class VendingMachineTestHelper {

    public static Map<Coin, Integer> getSpareCoins(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field spareCoinsField = vm.getClass().getSuperclass().getDeclaredField("spareCoins");
        spareCoinsField.setAccessible(true);
        return (Map<Coin, Integer>) spareCoinsField.get(vm);
    }

    public static BigDecimal getMachineBalance(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field machineBalanceField = vm.getClass().getSuperclass().getDeclaredField("machineBalance");
        machineBalanceField.setAccessible(true);
        return (BigDecimal) machineBalanceField.get(vm);
    }

    public static Map<Item, Integer> getShelf(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field shelfField = vm.getClass().getSuperclass().getDeclaredField("shelf");
        shelfField.setAccessible(true);
        return (Map<Item, Integer>) shelfField.get(vm);
    }

    public static Map<String, Item> getCodeToItemMap(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field codeToItemMapField = vm.getClass().getSuperclass().getDeclaredField("codeToItemMap");
        codeToItemMapField.setAccessible(true);
        return (Map<String, Item>) codeToItemMapField.get(vm);
    }

    public static VendingMachineState getState(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field stateField = vm.getClass().getSuperclass().getDeclaredField("state");
        stateField.setAccessible(true);
        return (VendingMachineState) stateField.get(vm);
    }

    public static BigDecimal getCustomerBalance(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field customerBalanceField = vm.getClass().getSuperclass().getDeclaredField("customerBalance");
        customerBalanceField.setAccessible(true);
        return (BigDecimal) customerBalanceField.get(vm);
    }

    public static BigDecimal getCurrentBalance(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field currentBalanceField = vm.getClass().getSuperclass().getDeclaredField("currentBalance");
        currentBalanceField.setAccessible(true);
        return (BigDecimal) currentBalanceField.get(vm);
    }

    public static Map<Coin, Integer> getCustomerCoins(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field customerCoinField = vm.getClass().getSuperclass().getDeclaredField("customerCoins");
        customerCoinField.setAccessible(true);
        return (Map<Coin, Integer>) customerCoinField.get(vm);
    }

    public static Map<Coin, Integer> getReturnCoins(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field returnCoinField = vm.getClass().getSuperclass().getDeclaredField("returnCoins");
        returnCoinField.setAccessible(true);
        return (Map<Coin, Integer>) returnCoinField.get(vm);
    }

    public static Item getSelectedItem(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field selectedItemField = vm.getClass().getSuperclass().getDeclaredField("selectedItem");
        selectedItemField.setAccessible(true);
        return (Item) selectedItemField.get(vm);
    }

    public static Item getReturnItem(@NotNull VendingMachine vm) throws NoSuchFieldException, IllegalAccessException {
        Field returnItemField = vm.getClass().getSuperclass().getDeclaredField("returnItem");
        returnItemField.setAccessible(true);
        return (Item) returnItemField.get(vm);
    }
}
