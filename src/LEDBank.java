public class LEDBank {
    // Heating
    public String heatingLEDOn(){
        return "PLT";
    }
    public String heatingLEDOff(){
        return "PLF";
    }
    // Reservoir
    public String reservoirLEDOn(){
        return "BLT";
    }
    public String reservoirLEDOff(){
        return "BLF";
    }
    // Voltage
    public String voltageLEDOn(){
        return "YLT";
    }
    public String voltageLEDOff(){
        return "YLF";
    }
    // Error
    public String errorLEDOn(){
        return "RLT";
    }
    public String errorLEDOff(){
        return "RLF";
    }
    // BREW
    public String brewEDOn(){
        return "GLT";
    }
    public String brewEDOff(){
        return "GLF";
    }
}
