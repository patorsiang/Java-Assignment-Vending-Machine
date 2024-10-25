package Constants;

import java.math.BigDecimal;

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
    TWO_POUNDS(BigDecimal.valueOf(2.0)),   // 2 pounds coin, value: 2.00 pounds
    ONE_POUND(BigDecimal.valueOf(1.00)),    // 1 pound coin, value: 1.00 pounds
    FIFTY_PENCE(BigDecimal.valueOf(0.50)),  // 50 pence coin, value: 0.50 pounds
    TEN_PENCE(BigDecimal.valueOf(0.10)),    // 10 pence coin, value: 0.10 pounds
    FIVE_PENCE(BigDecimal.valueOf(0.05)),   // 5 pence coin, value: 0.05 pounds
    TWENTY_PENCE(BigDecimal.valueOf(0.20)), // 20 pence coin, value: 0.20 pounds
    TWO_PENCE(BigDecimal.valueOf(0.02)),    // 2 pence coin, value: 0.02 pounds
    ONE_PENNY(BigDecimal.valueOf(0.01));    // 1 penny coin, value: 0.01 pounds

    // The monetary value of the coin.
    public final BigDecimal value;

    /**
     * Constructor for the Coin enum.
     *
     * @param value The value of the coin in pounds.
     */
    Coin(BigDecimal value) {
        this.value = value;
    }

    /**
     * Returns the value of the coin.
     *
     * @return the value of the coin in pounds.
     */
    public BigDecimal getValue() {
        return value;
    }
}
