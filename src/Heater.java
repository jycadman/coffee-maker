public class Heater {
    private int heatTemperature;
    private boolean heaterStatus;
    private final int roomTemp = 72;
    private final int overHeatTemp;
    private final int incDecAmount;
    private final int threadSleepTime;
    public Heater(){
        heaterStatus = false;
        heatTemperature = roomTemp;
        overHeatTemp = 250;
        incDecAmount = 50;
        threadSleepTime = 1000;

    }

    public void setHeaterStatus(boolean status) {
        heaterStatus = status;
    }

    public void heatUp() {
        // Heater has to me shut off in main will run forever here while increasing unless we use commented out code
        while (heaterStatus) {
            heatTemperature += incDecAmount;
            waitOneSecond();
        }
        // Automatically turn off if overheating Turn off after devbugging
        if (heatTemperature >= overHeatTemp) {
            System.out.println("Heater automatically turned off due to overheating!");
        }
        /*
        while (heaterStatus && heatTemperature < overHeatTemp) { // Remove 250 after debugging
            heatTemperature += incDecAmount;
            waitOneSecond();
        }
        // Automatically turn off if overheating Turn off after devbugging
        if (heatTemperature >= overHeatTemp) {
            System.out.println("Heater automatically turned off due to overheating!");
        }

         */
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
