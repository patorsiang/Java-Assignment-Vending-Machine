public class ItemVerification {
    static boolean validateCode(String code) {
        if (!code.matches("^(?!00)[0-9]{2}")) {
            throw new IllegalArgumentException("Invalid code");
        }
        return true;
    }
}
