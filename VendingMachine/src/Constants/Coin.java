package Constants;

/**
 * This class represents a coin type
 * - 2 pounds,
 * - 1 pounds,
 * - 50 pence,
 * - 20 pence,
 * - 10 pence,
 * - 5 pence,
 * -  and 1 penny
 */
public enum Coin {
    ONE_PENNY(0.01),
    TWO_PENCE(0.02),
    FIVE_PENCE(0.05),
    TEN_PENCE(0.10),
    TWENTY_PENCE(0.20),
    FIFTY_PENCE(0.50),
    ONE_POUND(1.00),
    TWO_POUNDS(2.00);

    public final double value;

    Coin(double v) {
        this.value = v;
    }

    public double getValue() {
        return value;
    }
}
