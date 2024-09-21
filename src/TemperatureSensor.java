public class TemperatureSensor {
    private int currentTemp;

    public TemperatureSensor(){
        currentTemp = 72;
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
