import java.util.Random;

public class PowerButton {
    public static boolean coffeeMachineOn;
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

}
