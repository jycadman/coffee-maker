public class CarafeSensor {
    private boolean isInPlace;

    public CarafeSensor(){
        isInPlace = true;
    }

    public void set(boolean input){ isInPlace = input;}
    public void negate(){isInPlace = !isInPlace;}

    public boolean carafeInPlace(){
        return isInPlace;
    }
}
