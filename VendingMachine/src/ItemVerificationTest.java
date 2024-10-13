import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemVerificationTest {

    @Test
    void testValidateCode00() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> ItemVerification.validateCode("00"));
        assertEquals("Invalid code", exception.getMessage());
    }

    @Test
    void testValidateCode100() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> ItemVerification.validateCode("100"));
        assertEquals("Invalid code", exception.getMessage());
    }

    @Test
    void testValidateCode01() {
        var validated = ItemVerification.validateCode("01");
        assertTrue(validated);
    }

}
