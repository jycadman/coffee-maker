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

}
