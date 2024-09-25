import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static BrewButton brewButton;
    static HeatingButton heatingButton;
    static PowerButton powerButton;
    static TemperatureSensor temperatureSensor;
    static VoltageSensor voltageSensor;
    static ReservoirSensor reservoirSensor;
    static LidSensor lidSensor;
    static CarafeSensor carafeSensor;
    static LEDBank ledBank;
    static Heater heater;
    static Timer timer;
    static private ServerSocket serverSocket;
    static private Socket mySocket = null;


    public static void standBy() {
        powerButton.negate();
        setAllLEDsToFalse();
        boolean pressed = false;
        boolean brewButtonState = brewButton.getStatus();
        boolean heatingButtonState = heatingButton.getStatus();
        boolean powerButtonState = powerButton.get();
        while(!pressed) {
            if (brewButtonState != brewButton.getStatus() || heatingButtonState != heatingButton.getStatus()
                    || powerButtonState != powerButton.get()) {
                pressed = true;
            }else {
                if (powerButton.get()) {
                    if (voltageSensor.isVoltageCorrect() && reservoirSensor.hasWater() && carafeSensor.get() && lidSensor.get()) {
                        ledBank.setVoltageLED(true);
                    } else {
                        ledBank.setErrorLED(true);
                    }
                } else {
                    System.out.print("Power down Appliance??  Talk with group on what to do");
                }
            }
        }
    }


    public static void brewing(){
        // Temp sensor, reservoir sensor, carafe sensor, led bank, heater, timer
        brewButton.negate();
        if (carafeSensor.get() & reservoirSensor.hasWater()) {
            heater.heatUp();
            ledBank.setBrewLED(true);
            timer.reset();
            while (heater.getHeatTemperature() < 202) {
                System.out.println("waiting for brewing to reach adequate temperature");
            }
            brewButton.turnOn();
            System.out.println("Brewing: " + brewButton.getStatus());
        }

    }


    public static void heating() {
        // Done heating button, carafe sensor temp sensor, carafe sensor, led bank, heater, timer
        heatingButton.negate();

        boolean pressed = false;
        boolean brewButtonState = brewButton.getStatus();
        boolean heatingButtonState = heatingButton.getStatus();
        boolean powerButtonState = powerButton.get();

        while (!pressed) {
            if (brewButtonState != brewButton.getStatus() || heatingButtonState != heatingButton.getStatus()
                    || powerButtonState != powerButton.get()) {
                pressed = true;
            }
            else {
                // Basic check for carafe
                if (!carafeSensor.get()) {
                    setAllLEDsToFalse(); // Set all LEDS to off
                    ledBank.setErrorLED(true);
                    System.out.print("\n Error State in Heating, Talk with group on what to do");
                }
                ledBank.setHeatingLED(true);
                if (temperatureSensor.getTemp() < 215) {
                    ledBank.getHeatingLED();
                    // Heat up heating element
                    timer.set(15);
                    heater.heatUp();
                } else if (temperatureSensor.getTemp() < 250 && temperatureSensor.getTemp() > 215) {
                    heater.coolDown();
                } else if (temperatureSensor.getTemp() >= 250) {
                    heater.setHeaterStatus(false);
                    setAllLEDsToFalse();
                    ledBank.setErrorLED(true);
                    timer.reset();
                }
            }
        }
    }


    public static void setAllLEDsToFalse(){
        ledBank.setErrorLED(false);
        ledBank.setBrewLED(false);
        ledBank.setReservoirLED(false);
        ledBank.setVoltageLED(false);
        ledBank.setHeatingLED(false);
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
            heating();
        }else if(input.equals("power")){
            System.out.println("Power button toggled!");
            standBy();
        }else{
            System.out.print("ERROR In ChoiceHandler SET ERROR LED");
        }
    }


    private static Object[] collectCoffeeMachineInfo() {
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

                // Return the collected data as an array
                return new Object[]{waterStatus, voltage, temperature, lidStatus, carafeStatus};
                //scanner.close();
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


        /*
        //////// SOCKET SECTION ////////
        BufferedReader reader = null; // Use this to read from the terminal.
        PrintWriter writer = null; // Use this to write to the terminal.
        try {
            serverSocket = new ServerSocket(5000);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("waiting for socket connection...");
            mySocket = serverSocket.accept();
            System.out.println("socket connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            writer = new PrintWriter(mySocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // For testing that the socket works
        while(true){
            try {
                String fromSocket = reader.readLine();
                if (fromSocket.equals("quit")){
                    break;
                }

                System.out.println(fromSocket);

                writer.println(scanner.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /////// END SOCKET SECTION /////////
         */


        //////// NOT FUTURE SOCKET SECTION ////////
        firstUserPromptForPowerButton(); // User clicks the power button the first time
        //////// NOT FUTURE SOCKET SECTION //////// 1 4 3 2 2


        powerButton.turnOn(); // Turn the power button on the first time, check first power button push with socket


        //////// NOT FUTURE SOCKET SECTION ////////
        //Collect information for the sensors from user(will be from socket later on)
        Object[] coffeeInfo = collectCoffeeMachineInfo(); // Simulating socket information received from coffee maker Need to send data to main and handle
        //////// END NOT FUTURE SOCKET SECTION ////////


        //////// FUTURE SOCKET SECTION ////////
        // Set sensor values from coffeeInfo object Change to socket info later
        reservoirSensor.set((boolean)coffeeInfo[0]);
        voltageSensor.setVoltage((int) coffeeInfo[1]);
        temperatureSensor.setTemp((int) coffeeInfo[2]);
        lidSensor.set((boolean) coffeeInfo[3]);
        carafeSensor.set((boolean) coffeeInfo[4]);
        /////// END FUTURE SOCKET SECTION /////////


        choiceHandler(getUserInput()); // getUserInput returns "brew" || "power" || "heating" and choiceHandler will handle
    }
}
