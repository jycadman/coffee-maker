public class LEDBank {
    private boolean heatingLED;
    private boolean reservoirLED;
    private boolean voltageLED;
    private boolean errorLED;
    private boolean brewLED;

    public LEDBank(){
        heatingLED = false;
        reservoirLED = false;
        voltageLED = false;
        errorLED = false;
        brewLED = false;
    }
    // Heating
    public void setHeatingLED(boolean input){
        heatingLED = input;
    }
    public boolean getHeatingLED(){
        return heatingLED;
    }
    // Reservoir
    public void setReservoirLED(boolean input){
        reservoirLED = input;
    }
    public boolean getReservoirLED(){
        return reservoirLED;
    }
    // Voltage
    public void setVoltageLED(boolean input){
        voltageLED = input;
    }
    public boolean getVoltageLED(){
        return voltageLED;
    }
    // Error
    public void setErrorLED(boolean input){
        errorLED = input;
    }
    public boolean getErrorLED(){
        return errorLED;
    }
    // BREW
    public void setBrewLED(boolean input){
        brewLED = input;
    }
    public boolean getBrewLED(){
        return brewLED;
    }
}
