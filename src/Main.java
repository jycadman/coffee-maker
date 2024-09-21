import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static  void standBy() {

    }

    public static void brewing(){

    }

    public static void heating(){

    }

    public static String getUserInput(){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the Java Coffee Machine!");
            System.out.println("Choices: brew button, heating button, power button");

            String userChoice;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("Enter your choice: ");
                userChoice = scanner.nextLine().toLowerCase();

                switch (userChoice) {
                    case "brew button":
                        validChoice = true;
                        return "brew";
                    case "heating button":
                        validChoice = true;
                        return "heating";
                    case "power button":
                        validChoice = true;
                        return "power";
                    default:
                        System.out.println("Invalid choice. Please select brew button, heating button, or power button.");
                }
            }

            // Close Scanner
            scanner.close();
        return "";
    }

    private static void choiceHandler(String input){// input = "brew" || "power" || "heating"
        System.out.println("Word is: " + input);
        if (input.equals("brew")){
            System.out.println("Brew button pressed!");
        } else if(input.equals("heating")){
            System.out.println("Heating button activated!");
        }else if(input.equals("power")){
            System.out.println("Power button toggled!");
        }else{
            System.out.print("ERROR In ChoiceHandler SET ERROR LED");
        }
    }

    public static void main(String[] args) {

        // Initialize our coffee machine components
        BrewButton brewButton = new BrewButton();
        HeatingButton heatingButton = new HeatingButton();
        PowerButton powerButton = new PowerButton();
        TemperatureSensor temperatureSensor = new TemperatureSensor();
        VoltageSensor voltageSensor = new VoltageSensor();
        ReservoirSensor reservoirSensor = new ReservoirSensor();
        LidSensor lidSensor = new LidSensor();
        CarafeSensor carafeSensor = new CarafeSensor();
        LEDBank ledBank = new LEDBank();
        Heater heater = new Heater();
        Timer timer = new Timer();

        // Prompt user for input and return string based on input
        choiceHandler(getUserInput()); // returns "brew" || "power" || "heating"



    }
}
