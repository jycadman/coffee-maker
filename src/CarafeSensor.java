public class CarafeSensor {
    private boolean isInPlace;

    public CarafeSensor(){
        isInPlace = false;
    }

    public void set(boolean input){ isInPlace = input;}

    public boolean get(){
        return isInPlace;
    }

    public static void main(String[] args){
        CarafeSensor test = new CarafeSensor();
        System.out.println(test.get());
    }
}
