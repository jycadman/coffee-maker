public enum MachineState {
    POWER_BUTTON_PRESSED("PBP"),
    HEATING_BUTTON_PRESSED("HBP"),
    BREW_BUTTON_PRESSED("BBP"),
    CARAFE_IS_IN_PLACE("CST"),
    CARAFE_IS_NOT_IN_PLACE("CSF"),
    LID_IS_DOWN("LST"),
    LID_IS_UP("LSF"),
    RESERVOIR_FULL("RST"),
    RESERVOIR_EMPTY("RSF"),
    TEMPERATURE_SENSOR_SET("TSS"),
    VOLTAGE_SENSOR_SET("VSS"),
    HEAT_UP("HHU"),
    COOL_DOWN("HCD"),
    BREWING_LEDS("BLED"), //Pink, yellow, green
    HEATING_LEDS("HLED"),//Pink, yellow,
    ERROR_LEDS("ELED"),
    ALL_LEDS_OFF("ALO"),
    STANDBY_LEDS_WITH_WATER("SLTW"),
    STANDBY_LEDS_WITHOUT_WATER("SLFW");


    private final String command;

    MachineState(String s) {
        this.command = s;
    }

    public String getCommand() {
        return command;
    }
}
