public class CarafeSensor {
    private boolean isInPlace;

    public CarafeSensor(){
        isInPlace = false;
    }

    public void setCarafe(){
        isInPlace = true;
    }

    public void removeCarafe(){
        isInPlace = false;
    }

    public boolean getIsInPlace(){
        return isInPlace;
    }

    public static void main(String[] args){
        CarafeSensor test = new CarafeSensor();
        System.out.println(test.getIsInPlace());
        test.setCarafe();
        System.out.println(test.getIsInPlace());
    }
}
