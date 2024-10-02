public class LidSensor {
    private boolean isClosed;

    public LidSensor() {
        isClosed = true;
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
