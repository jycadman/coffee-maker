import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    static ServerSocket serverSocket;
    static Socket mySocket = null;
    static BufferedReader reader = null; // Use this to read from the terminal.
    static PrintWriter writer = null; // Use this to write to the terminal.
    static int optimalWaterTemp = 215;
    static int maxWaterTemp = 250;


    public static void standBy() {
        // voltage sensor, resevoir sensor, lid sensor, carafe sensor, led bank
        powerButton.negate();
        setAllLEDsToFalse();
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


    public static void brewing(){
        // Temp sensor, reservoir sensor, lid sensor ,carafe sensor, led bank, heater, timer
        brewButton.negate();

        if (carafeSensor.get() & reservoirSensor.hasWater() & lidSensor.get()) {
            heater.setHeaterStatus(true); //  Heats up
            ledBank.setBrewLED(true);
            heater.heatUp();
            timer.reset();
            brewButton.turnOn();
            System.out.println("Brewing: " + brewButton.getStatus());
        }

    }


    public static void heating() {
        // Done heating button, temp sensor, carafe sensor, led bank, heater, timer
        heatingButton.negate();
        System.out.println("In heating() Heating temp: " + heater.getHeatTemperature());
        // Basic check for carafe
        if (!carafeSensor.get()) {
            setAllLEDsToFalse(); // Set all LEDS to off
            ledBank.setErrorLED(true);
            System.out.print("\n Error State in Heating, Talk with group on what to do");
        }

        ledBank.setHeatingLED(true);

        if(temperatureSensor.getTemp() < optimalWaterTemp){
            heater.heatUp();
            timer.set(15);
        } else if( temperatureSensor.getTemp() < maxWaterTemp && temperatureSensor.getTemp() > optimalWaterTemp){
            heater.coolDown();
            timer.set(15);
        } else if(temperatureSensor.getTemp() >= maxWaterTemp){
            heater.setHeaterStatus(false);
            setAllLEDsToFalse();
            ledBank.setErrorLED(true);
            timer.reset();
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
        //System.out.println("Word is: " + input);
        switch (input) {
            case "brew" -> {
                System.out.println("Brew button pressed!");
                brewing();
            }
            case "heating" -> {
                System.out.println("Heating button activated!");
                heating();
            }
            case "power" -> {
                System.out.println("Power button toggled!");
                standBy();
            }
            default -> System.out.print("ERROR In ChoiceHandler SET ERROR LED");
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
        // Instantiate classes
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

        // Assign it
        Main.brewButton = brewButton;
        Main.heatingButton = heatingButton;
        Main.powerButton = powerButton;
        Main.temperatureSensor = temperatureSensor;
        Main.voltageSensor = voltageSensor;
        Main.reservoirSensor = reservoirSensor;
        Main.lidSensor = lidSensor;
        Main.carafeSensor = carafeSensor;
        Main.ledBank = ledBank;
        Main.heater = heater;
        Main.timer = timer;


/*
        //////// SOCKET SECTION ////////
        // Sets up a server socket on port 5000
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Waits for a socket to connect to the coffee machine.
        try {
            System.out.println("waiting for socket connection...");
            mySocket = serverSocket.accept();
            System.out.println("socket connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sets up reader and writer. reader is used to get data from the terminal
        // and writer is used to send it.
        try {
            reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            writer = new PrintWriter(mySocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parses commands sent from the terminal.
        Thread perserThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("\nSocket thread running");
                while(true) {
                    String next = null;
                    try {
                        next = reader.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // These parse commands sent from the terminal
                    // and changes the devices. Some commands expect
                    // an integer to be given as well.
                    switch(next){
                        case "BBP": // Brew Button Pressed
                            System.out.println("Brew button pressed");
                            brewButton.negate();
                            break;
                        case "HBP": // Heating Button Pressed
                            System.out.println("Heating button pressed");
                            heatingButton.negate();
                            break;
                        case "PBP": // Power Button Pressed
                            System.out.println("Power button pressed");
                            powerButton.negate();
                            break;
                        case "CST": // CarafeSensor True;
                            System.out.println("Carafe is in place");
                            carafeSensor.set(true);
                            break;
                        case "CSF": // CarafeSensor False;
                            System.out.println("Carafe is not In place");
                            carafeSensor.set(false);
                            break;
                        case "LST": // LidSensor True;
                            System.out.println("Lid is down");
                            lidSensor.set(true);
                            break;
                        case "LSF": // LidSensor False
                            System.out.println("Lid is up");
                            lidSensor.set(false);
                            break;
                        case "RST": // ReservoirSensor True
                            System.out.println("There is water");
                            reservoirSensor.set(true);
                            break;
                        case "RSF": // ReservoirSensor False
                            System.out.println("There is not water");
                            reservoirSensor.set(false);
                            break;
                        case "TSS": // Temp Sensor Ser
                            try {
                                String num = reader.readLine();
                                System.out.println("Setting temperature to " + num);
                                temperatureSensor.setTemp(Integer.parseInt(num));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case "VSS": // Voltage sensor set
                            try {
                                String num = reader.readLine();
                                System.out.println("Setting voltage to " + num);
                                voltageSensor.setVoltage(Integer.parseInt(num));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        default:
                            System.out.println("Unknown command");
                    }
                }
            }
        });
        //perserThread.start();

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