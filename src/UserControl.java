/**
 * UserControl is the Abstract Class used to help define DeviceComponent objects. Could be refined
 */
public abstract class UserControl {
    private final String name;
    private final String comm;

    public UserControl(String n, String message) {
        this.name = n;
        this.comm = message;
    }

    public abstract void actionLogic();

    @Override
    public String toString() {
        return "Component: " + this.name + "\nCommand: " + this.comm;
    }

    public String getComm() { return this.comm; }

    public String getName() { return this.name; }
}
