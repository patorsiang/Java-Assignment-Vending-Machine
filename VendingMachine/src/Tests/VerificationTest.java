package Tests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import Verifications.Verification;
import Constants.Constants;

public class VerificationTest {

    @Test
    void testValidateCode00() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Verification.validateCode("00"));
        assertEquals("Invalid code", exception.getMessage());
    }

    @Test
    void testValidateCode100() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Verification.validateCode("100"));
        assertEquals("Invalid code", exception.getMessage());
    }

    @Test
    void testValidateCode01() {
        var validated = Verification.validateCode("01");
        Assertions.assertTrue(validated);
    }

    @Test
    void testValidateQuantitySuccess() {
        var validated = Verification.validateQuantity(1);
        Assertions.assertTrue(validated);
    }

    @Test
    void testValidateQuantityFailure() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Verification.validateQuantity(-1));
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    void testValidateCoinQuantitySuccess() {
        var validated = Verification.validateLimitQuantity(1, Constants.LIMIT_COIN_QUANTITY_IN_VM);
        Assertions.assertTrue(validated);
    }

    @Test
    void testValidateCoinQuantityFailure() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> Verification.validateLimitQuantity(1001, Constants.LIMIT_COIN_QUANTITY_IN_VM));
        assertEquals("Quantity reached limit", exception.getMessage());
    }
}
