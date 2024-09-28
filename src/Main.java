import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
        System.out.println("in standBy");
        // voltage sensor, resevoir sensor, lid sensor, carafe sensor, led bank
        //powerButton.negate();
        setAllLEDsToFalse();
        if (voltageSensor.isVoltageCorrect() && reservoirSensor.hasWater() && carafeSensor.carafeInPlace() && lidSensor.isClosed()) {
            writer.println(ledBank.voltageLEDOn());
        } else {writer.println(ledBank.errorLEDOn());}
    }


    public static void brewing(){
        System.out.println("in brewing");
        // Temp sensor, reservoir sensor, lid sensor ,carafe sensor, led bank, heater, timer
        //brewButton.negate();

        if (carafeSensor.carafeInPlace() & reservoirSensor.hasWater() & lidSensor.isClosed()) {
            writer.println(heater.heatUp());
            writer.println(ledBank.brewEDOn());
            timer.set(5000); // Five seconds
            timer.reset();

            System.out.println("Brewing");

            while (!timer.timeout() && !brewButton.getStatus()){}

            if (timer.timeout()) {
                System.out.println("Timed out");
            }

            if(brewButton.getStatus()){
                brewButton.turnOff();
            }

            System.out.println("Brewing Done");
            writer.println("CI1");
        } else {
            System.out.println("Failed to Brew");
        }
    }


    public static void heating() {
        System.out.println("in heating");
        // Done heating button, temp sensor, carafe sensor, led bank, heater, timer
        // Basic check for carafe
        if (!carafeSensor.carafeInPlace()) {
            setAllLEDsToFalse(); // Set all LEDS to off
            writer.println(ledBank.errorLEDOn());
            System.out.print("\n Error State in Heating, Talk with group on what to do\n");
            return;
        }

        writer.println(ledBank.heatingLEDOn());
        writer.println(heater.heatUp());

        timer.set(15000);
        timer.reset();

        while (!timer.timeout() && !heatingButton.getStatus()){
            if (temperatureSensor.getTemp() > optimalWaterTemp + 20 || temperatureSensor.getTemp() < optimalWaterTemp - 20 ){
                writer.println(ledBank.heatingLEDOff());
                writer.println(heater.coolDown());
                writer.println(ledBank.errorLEDOn());
                break;
            }
        }

        if (timer.timeout()) {
            System.out.println("Timed out");
        }

        if (heatingButton.getStatus()){
            heatingButton.turnOff();
        }

        writer.println(heater.coolDown());
    }


    public static void setAllLEDsToFalse(){
        writer.println(ledBank.errorLEDOff());
        writer.println(ledBank.brewEDOff());
        writer.println(ledBank.reservoirLEDOff());
        writer.println(ledBank.voltageLEDOff());
        writer.println(ledBank.heatingLEDOff());
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
                boolean running = true;
                System.out.println("\nSocket thread running");
                while(running) {
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
                            running = false;
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
                            lidSensor.close();
                            break;
                        case "LSF": // LidSensor False
                            System.out.println("Lid is up");
                            lidSensor.open();
                            break;
                        case "RST": // ReservoirSensor True
                            System.out.println("There is water");
                            reservoirSensor.fill();
                            break;
                        case "RSF": // ReservoirSensor False
                            System.out.println("There is not water");
                            reservoirSensor.empty();
                            break;
                        case "TSS": // Temp Sensor Set
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
                            System.out.println("Unknown command " + next);
                    }
                }
            }
        });
        perserThread.start();

        standBy();
        while(!powerButton.get()){
            // I don't know why the print is needed, but errors happen without it.
            System.out.print("");
            if (brewButton.getStatus()){
                brewButton.negate();
                brewing();
            }
            else if (heatingButton.getStatus()) {
                heatingButton.negate();
                heating();
            }
        }

        //////// SOCKET SECTION ////////
        try {
            mySocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //////// END SOCKET SECTION ////////

    }
}