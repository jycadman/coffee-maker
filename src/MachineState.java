public enum MachineState {
    OFF("Powered Off"),
    ON("Powered On"),
    ERRWATER("Water Low"),
    ERRVOLTAGE("Incorrect Voltage"),
    ERRTEMPSENSE("Temperature Sensors Faulty"),
    HEATING("Heating Up"),
    BREWING("Brewing In Process"),
    KEEPWARM("Keeping Warm"),
    STANDBY("On Standby"),
    COOLDOWN("Cooling Down");

    private final String command;

    MachineState(String s) {
        this.command = s;
    }

    public String getCommand() {
        return command;
    }
}
