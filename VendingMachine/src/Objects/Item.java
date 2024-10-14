package Objects;

import Exceptions.NotEnoughException;
import Exceptions.OverflowingShelfException;
import Verifications.ItemVerification;

public class Item extends ItemVerification {
    private final String code;
    private final String name;
    private double price;
    private int quantity;

    public Item(String code, String name, double price) {
        validateCode(code);
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public String getCode() {
        return this.code;
    }

    public String printItem() {
        return this.code + "\t" + this.name + "\t￡" + String.format("%.2f", this.price) + "\t" + this.quantity;
    }

    public void changePrice(double newPrice) throws IllegalArgumentException {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = newPrice;
    }

    public void increaseQuantity(int quantity) throws OverflowingShelfException, IllegalArgumentException {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        } else if (quantity + this.quantity > 99) {
            throw new OverflowingShelfException("This item, " + this.code + ", reached the limit of its' shelf.");
        }
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) throws NotEnoughException, IllegalArgumentException {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        } else if (quantity > this.quantity) {
            throw new NotEnoughException("This item, " + this.code + ", have not enough stock as you requested.");
        }
        this.quantity -= quantity;
    }
}
