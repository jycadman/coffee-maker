public class BrewButton {
    private boolean isOn;
    public BrewButton(){
        isOn = false;
    }

    public void turnOff(){
        isOn = false;
    }

    public void turnOn(){
        isOn = true;
    }

    public boolean getStatus(){
        return isOn;
    }

    public static void main(String[] Args){
        BrewButton test = new BrewButton();
        System.out.println(test.getStatus());
        test.turnOn();
        System.out.println(test.getStatus());

    }

}
