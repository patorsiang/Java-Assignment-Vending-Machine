package Services;

import org.jetbrains.annotations.NotNull;

/**
 * This service serves all who want to use
 */
public class GeneralPrintService {

    /**
     * To price the exception message
     * @param e the exception
     */
    public void printError(@NotNull Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}