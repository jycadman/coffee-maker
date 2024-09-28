public class TemperatureSensor {
    private int currentTemp = 0;

    public void setTemp(int input){
        currentTemp = input;
    }

    public int getTemp(){
        return currentTemp;
    }
}
