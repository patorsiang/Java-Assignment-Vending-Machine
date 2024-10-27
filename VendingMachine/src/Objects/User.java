package Objects;

import Services.GeneralPrintService;

/**
 * The User class serves as a superclass for both Admin and Customer classes,
 * providing some common attributes
 * Each User has access to a VendingMachine and a GeneralPrintService.
 */
public class User {
    protected VendingMachine vm;

    // This GeneralPrintService for utility printing services,
    // such as error messages or general information
    protected GeneralPrintService printService = new GeneralPrintService();

    /**
     * Constructs a User with a reference to a VendingMachine instance.
     *
     * @param vm The VendingMachine instance to be associated with the User
     */
    public User(VendingMachine vm) {
        this.vm = vm;
    }
}