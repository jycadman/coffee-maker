import java.util.Random;

public class VoltageSensor {

    public static int voltage = 120;

    public void checkVoltage(boolean correctVoltage) {
        if (correctVoltage) {
            System.out.println("Voltage is correct: " + voltage + "v");
        }
        else {
            Random random = new Random();
            int lowVoltageMin = 0;
            int lowVoltageMax = 100;
            int voltageHighMin = 140;
            int voltageHighMax = 200;

            // Randomly decide which range to use
            boolean useLowRange = random.nextBoolean();  // true means low voltage range, false means high voltage range

            int randomVoltage;

            if (useLowRange) {
                // Generate random number in low voltage range
                randomVoltage = random.nextInt((lowVoltageMax - lowVoltageMin) + 1) + lowVoltageMin;
                System.out.println("Voltage is low: " + randomVoltage + "v");
                voltage = randomVoltage;
            } else {
                // Generate random number in high voltage range
                randomVoltage = random.nextInt((voltageHighMax - voltageHighMin) + 1) + voltageHighMin;
                System.out.println("Voltage is too High: " + randomVoltage + "v");
                voltage = randomVoltage;
            }
        }
    }
}
