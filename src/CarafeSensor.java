public class CarafeSensor {
    private boolean isInPlace;

    public CarafeSensor(){
        isInPlace = false;
    }

    public void set(boolean input){ isInPlace = input;}

    public boolean carafeInPlace(){
        return isInPlace;
    }
}
