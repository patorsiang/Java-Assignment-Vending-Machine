package Verifications;

import Constants.Constants;

public class Verification {
    public static boolean validateCode(String code) {
        if (!code.matches("^(?!00)[0-9]{2}")) {
            throw new IllegalArgumentException("Invalid code");
        }
        return true;
    }

    public static boolean validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return true;
    }

    public static boolean validateLimitQuantity(int quantity, int limit) {
        if (quantity > limit) {
            throw new IllegalArgumentException("Quantity reached limit");
        }
        return true;
    }
}
