package Constants;

/**
 * This class represents British pound sterling in the form of coins
 * - 2 pounds,
 * - 1 pounds,
 * - 50 pence,
 * - 20 pence,
 * - 10 pence,
 * - 5 pence,
 * -  and 1 penny
 */
public enum Coin {

    // Enum constants representing the different coins, with their respective values.
    ONE_PENNY(0.01),    // 1 penny coin, value: 0.01 pounds
    TWO_PENCE(0.02),    // 2 pence coin, value: 0.02 pounds
    FIVE_PENCE(0.05),   // 5 pence coin, value: 0.05 pounds
    TEN_PENCE(0.10),    // 10 pence coin, value: 0.10 pounds
    TWENTY_PENCE(0.20), // 20 pence coin, value: 0.20 pounds
    FIFTY_PENCE(0.50),  // 50 pence coin, value: 0.50 pounds
    ONE_POUND(1.00),    // 1 pound coin, value: 1.00 pounds
    TWO_POUNDS(2.00);   // 2 pounds coin, value: 2.00 pounds

    // The monetary value of the coin.
    public final double value;

    /**
     * Constructor for the Coin enum.
     *
     * @param value The value of the coin in pounds.
     */
    Coin(double value) {
        this.value = value;
    }

    /**
     * Returns the value of the coin.
     *
     * @return the value of the coin in pounds.
     */
    public double getValue() {
        return value;
    }
}
