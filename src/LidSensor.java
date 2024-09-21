public class LidSensor {
    private boolean isClosed;

    public LidSensor() {
        isClosed = false;
    }

    public void close() {
        isClosed = true;
    }

    public void open() {
        isClosed = false;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public static void main(String[] args) {
        LidSensor ls = new LidSensor();

        // Assume lid is closed on start-up
        ls.close();

        if (ls.isClosed()) {
            System.out.println("Coffee Maker Lid is Closed.");
        } else {
            System.out.println("Coffee Maker Lid is Open.");
        }
    }
}
