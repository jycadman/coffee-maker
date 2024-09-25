import java.util.Random;

public class PowerButton {
    public static boolean coffeeMachineOn;
    public PowerButton(){
        coffeeMachineOn = false;
    }

    void turnOn() {
        coffeeMachineOn = true;
    }

    void negate(){
        coffeeMachineOn = !coffeeMachineOn;
    }

    boolean get(){
        return coffeeMachineOn;
    }

    void turnOff() {
        coffeeMachineOn = false;
    }

    public static void main(String[] args){
        PowerButton powerbutton = new PowerButton();
        System.out.print(powerbutton.get());
        powerbutton.negate();
        System.out.print(powerbutton.get());
    }

}
