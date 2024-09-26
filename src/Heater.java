import java.util.ArrayList;
import java.util.List;

public class Heater {
    protected int heatTemperature;
    private boolean heaterStatus;
    private int roomTemp = 72;
    private int overHeatTemp;
    private int incDecAmount;
    private int threadSleepTime;

    public Heater(){
        heaterStatus = false;
        heatTemperature = roomTemp;
        overHeatTemp = 250;
        incDecAmount = 10;
        threadSleepTime = 1000;

    }

    public void setHeaterStatus(boolean status) {
        heaterStatus = status;
    }

    public void heatUp() {
        while (heaterStatus && heatTemperature < 215) {
            heatTemperature += incDecAmount;
            System.out.println("Heater Temp: " + heatTemperature);
            waitOneSecond();
            if (heatTemperature >= overHeatTemp) {
                System.out.println("Heater automatically turned off due to overheating!");
            }
        }
    }

    public void coolDown() {
        while (!heaterStatus && heatTemperature > roomTemp) {
            heatTemperature -= incDecAmount;
            waitOneSecond();
        }
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(threadSleepTime); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getHeatTemperature(){
        return heatTemperature;
    }

    public static void main(String[] args) {
        Heater myHeater = new Heater();

        // Turn on the heater
        myHeater.setHeaterStatus(true);
        myHeater.heatUp();
        System.out.println(myHeater.heatTemperature);
        // Turn off the heater
        myHeater.setHeaterStatus(false);
        myHeater.coolDown();
        System.out.println(myHeater.heatTemperature);

        System.out.println("Final temperature: " + myHeater.getHeatTemperature() + "°F");
    }

}
