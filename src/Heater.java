public class Heater {
    private int heatTemperature;
    private boolean heaterStatus;
    public Heater(){
        heaterStatus = false;
        heatTemperature = 72;
    }

    public void setHeaterTemperature(int input){
        heatTemperature = input;
    }

    public void incrementByOne(){
        heatTemperature+=1;
    }

    public void decrementByOne(){
        heatTemperature-=1;
    }

    public int getHeatTemperature(){
        return heatTemperature;
    }

}
