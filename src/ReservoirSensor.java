public class ReservoirSensor {
    private boolean hasWater;

    public ReservoirSensor(){
        hasWater = false;
    }

    public void fill() {
        //Fill the reservoir
        hasWater = true;
    }

    public void empty(){
        //Empty the reservoir
        hasWater = false;
    }

    public boolean hasWater(){
        //return if it has water or not
        return hasWater;
    }
}
