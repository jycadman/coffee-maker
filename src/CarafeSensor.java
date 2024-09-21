public class CarafeSensor {
    private boolean isInPlace;

    public CarafeSensor(){
        isInPlace = false;
    }

    public void setCarafeIn(){
        isInPlace = true;
    }

    public void removeCarafeOut(){
        isInPlace = false;
    }

    public boolean getIsInPlace(){
        return isInPlace;
    }

    public static void main(String[] args){
        CarafeSensor test = new CarafeSensor();
        System.out.println(test.getIsInPlace());
        test.setCarafeIn();
        System.out.println(test.getIsInPlace());
    }
}
