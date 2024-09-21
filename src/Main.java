import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    BrewButton brewButton;
    HeatingButton heatingButton;
    PowerButton powerButton;
    TemperatureSensor temperatureSensor;
    VoltageSensor voltageSensor;
    ReservoirSensor reservoirSensor;
    LidSensor lidSensor;
    CarafeSensor carafeSensor;
    LEDBank ledBank;
    Heater heater;
    Timer timer;
    static final Scanner scanner = new Scanner(System.in);
    public static  void standBy() {

    }

    public static void brewing(){

    }

    public static void heating(){

    }

    public static String getUserInput() {
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Choices: brew button, heating button, power button");

        while (true) {
            System.out.print("Enter your choice: ");
            String userChoice = scanner.nextLine().toLowerCase();

            switch (userChoice) {
                case "brew button":
                    //scanner.close();
                    return "brew";
                case "heating button":
                    //scanner.close();
                    return "heating";
                case "power button":
                    //scanner.close();
                    return "power";
                default:
                    System.out.println("Invalid choice. Please select brew button, heating button, or power button.");
            }
        }
    }

    private static void choiceHandler(String input){// input = "brew" || "power" || "heating"
        System.out.println("Word is: " + input);
        if (input.equals("brew")){
            System.out.println("Brew button pressed!");
            // Run brewing
        } else if(input.equals("heating")){
            System.out.println("Heating button activated!");
            // Run heating
        }else if(input.equals("power")){
            System.out.println("Power button toggled!");
            // Run power
        }else{
            System.out.print("ERROR In ChoiceHandler SET ERROR LED");
        }
    }

    private static void collectCoffeeMachineInfo() {
        //Scanner scanner = new Scanner(System.in);
        boolean waterStatus;
        int voltage;
        int temperature;
        boolean lidStatus;
        boolean carafeStatus;

        while (true) {
            try {

                System.out.print("Does the reservoir contain water? (true/false): ");
                waterStatus = scanner.nextBoolean();

                System.out.print("What is the current voltage? (in volts): ");
                voltage = scanner.nextInt();

                System.out.print("What is the current temperature? (in Fahrenheit): ");
                temperature = scanner.nextInt();

                System.out.print("Is the lid closed? (true/false): ");
                lidStatus = scanner.nextBoolean();

                System.out.print("Is the carafe in place? (true/false): ");
                carafeStatus = scanner.nextBoolean();

                // Process the collected information
                System.out.println("\nCoffee Machine Information:");
                System.out.println("Water status: " + waterStatus);
                System.out.println("Voltage: " + voltage + " V");
                System.out.println("Temperature: " + temperature + "°F");
                System.out.println("Lid status: " + lidStatus);
                System.out.println("Carafe status: " + carafeStatus);
                //scanner.close();
                break; // Exit the loop if input is valid
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }

    public static void firstUserPromptForPowerButton() {
        //Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Java Coffee Machine!");
        System.out.println("Please press the power button.");

        while (true) {
            System.out.print("Enter your choice (power button): ");
            String userChoice = scanner.nextLine().toLowerCase();

            if (userChoice.equals("power button")) {
                // Close Scanner
                //scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Please select the power button.");
            }
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


        firstUserPromptForPowerButton(); // User clicks the power button the first time

        powerButton.turnOn(); // Turn the power button on the first time

        collectCoffeeMachineInfo(); // Simulating socket information received from coffee maker

        choiceHandler(getUserInput()); // getUserInput returns "brew" || "power" || "heating" and choiceHandler will handle



    }
}
