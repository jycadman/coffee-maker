public class HeatingButton {
    private boolean isOn;
    public HeatingButton(){
        isOn = false;
    }

    public void turnOff(){
        isOn = false;
    }

    public void turnOn(){
        isOn = true;
    }

    public void negate(){
        isOn = !isOn;
    }

    public boolean getStatus(){
        return isOn;
    }

}
