package Services;

import org.jetbrains.annotations.NotNull;

public class GeneralPrintService {
    public void printError(@NotNull Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
