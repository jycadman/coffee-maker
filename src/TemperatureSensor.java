public class TemperatureSensor {
    private int currentTemp = 72;

    public void setTemp(int input){
        currentTemp = input;
    }

    public int getTemp(){
        return currentTemp;
    }
}
