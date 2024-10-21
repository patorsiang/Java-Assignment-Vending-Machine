package Objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A record representing an Item with code, name, and price.
 * This record includes validation for the fields in its canonical constructor.
 */
public record Item(
        String code,   // Code of the item, should be a two-digit number from 01 to 99.
        String name,   // Name of the item, cannot be empty.
        double price   // Price of the item, must be non-negative.
) {
    /**
     * Canonical constructor with validation logic.
     *
     * @param code  The code of the item. Must be a two-digit number from 01 to 99.
     * @param name  The name of the item. Cannot be empty.
     * @param price The price of the item. Must be a non-negative value.
     * @throws IllegalArgumentException if the code, name, or price are invalid.
     */
    public Item {

        // Validate that the name is not empty.
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }

        // Regex pattern to match valid codes (from 01 to 99).
        String codeRegex = "0[1-9]|[1-9][0-9]";
        Pattern codePattern = Pattern.compile(codeRegex);
        Matcher codeMatcher = codePattern.matcher(code);

        // Validate the code to ensure it matches the pattern.
        if (!codeMatcher.matches()) {
            throw new IllegalArgumentException("Invalid code: Must be a two-digit number between 01 and 99");
        }

        // Validate that the name is not empty.
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name: Name cannot be empty");
        }

        // Validate that the price is non-negative.
        if (price < 0){
            throw new IllegalArgumentException("Invalid price: Price must be non-negative");
        }
    }
}
