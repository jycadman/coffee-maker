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

    public boolean getStatus(){
        return isOn;
    }

    public static void main(String[] args){
        HeatingButton test = new HeatingButton();
        System.out.println(test.getStatus());
        test.turnOn();
        System.out.println(test.getStatus());
    }

}
