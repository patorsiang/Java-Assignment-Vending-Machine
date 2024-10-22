package Exceptions;

public class InsufficientHoldedCoinsException extends Exception {
    public InsufficientHoldedCoinsException(String message) {
        super(message);
    }
}
