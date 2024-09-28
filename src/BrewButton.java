public class BrewButton {
    private boolean isOn;
    public BrewButton(){
        isOn = false;
    }

    public void turnOff(){
        isOn = false;
    }

    public void negate(){isOn = !isOn;}

    public void turnOn(){
        isOn = true;
    }

    public boolean getStatus(){;
        return isOn;
    }

}
