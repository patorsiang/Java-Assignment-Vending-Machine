package Objects;

public abstract class User {
    protected VendingMachine vm;

    public User(VendingMachine vm) {
        this.vm = vm;
    }
}
