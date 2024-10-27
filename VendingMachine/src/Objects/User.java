package Objects;

import Services.GeneralPrintService;

public abstract class User {
    protected VendingMachine vm;
    protected GeneralPrintService printService = new GeneralPrintService();

    public User(VendingMachine vm) {
        this.vm = vm;
    }
}
