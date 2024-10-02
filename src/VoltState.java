public enum VoltState {
    VOLT0(0),
    VOLT50(50),
    VOLT120(120),
    VOLT300(300);

    private int voltage;

    VoltState(int i) {
        this.voltage = i;
    }

    public int getVoltage() {
        return voltage;
    }
}
