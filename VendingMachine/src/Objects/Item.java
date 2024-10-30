package Objects;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A record representing an Item with Code, Name, and Price.
 * This record includes validation for the fields in its canonical constructor.
 */
public record Item(
        String code,   //Code of the Item, should be a two-digit number from 01 to 99.
        String name,   //Name of the Item, cannot be empty.
        BigDecimal price   //Price of the Item must be non-negative.
        ) {
    /**
     * Canonical constructor with validation logic.
     *
     * @param code  The Code of the Item. It must be a two-digit number from 01 to 99.
     * @param name  The Name of the Item. It cannot be empty.
     * @param price The Price of the Item. It must be a non-negative value.
     * @throws IllegalArgumentException if the Code, Name, or Price are invalid.
     */
    public Item {

        // Validate that the Name is not empty.
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }

        // Regex pattern to match valid codes (from 01 to 99).
        String codeRegex = "0[1-9]|[1-9][0-9]";
        Pattern codePattern = Pattern.compile(codeRegex);
        Matcher codeMatcher = codePattern.matcher(code);

        // Validate the Code to ensure it matches the pattern.
        if (!codeMatcher.matches()) {
            throw new IllegalArgumentException("Invalid code: Must be a two-digit number between 01 and 99");
        }

        // Validate that the Name is not empty.
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid name: Name cannot be empty");
        }

        // Validate that the Price is non-negative.
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid price: Price must be non-negative");
        }
    }
}