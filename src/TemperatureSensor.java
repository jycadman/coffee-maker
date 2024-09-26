public class TemperatureSensor extends Heater {
    private int currentTemp;

    public TemperatureSensor(){
        super();
    }

    @Override
    public void heatUp(){
        System.out.println("Temp Sens: " + currentTemp);
        super.heatUp();
        setTemp(getHeatTemperature());
    }

    @Override
    public void coolDown(){
        super.coolDown();
        setTemp(getHeatTemperature());
    }
    public void setTemp(int input){
        currentTemp = input;
    }

    public int getTemp(){
        return currentTemp;
    }

    public static void main(String[] args){
        TemperatureSensor test = new TemperatureSensor();

        test.setTemp(251);

        System.out.print("Temperature: " + test.currentTemp);
    }
}
