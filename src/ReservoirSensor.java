public class ReservoirSensor {
    private boolean hasWater;

    public ReservoirSensor(){
        //Initialization to false
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

    public static void main(String[] args) {
        ReservoirSensor myReservoir = new ReservoirSensor();

        // Fill the reservoir
        myReservoir.fill();

        // Print Statements for easy debuggin guys.
        if (myReservoir.hasWater()) {
            System.out.println("Water exists in the reservoir.");
        } else {
            System.out.println("The reservoir is empty.");
        }

        myReservoir.empty();

        // Print Statements for easy debuggin guys.
        if (myReservoir.hasWater()) {
            System.out.println("Water exists in the reservoir.");
        } else {
            System.out.println("The reservoir is empty.");
        }

    }
}
