import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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


    public static synchronized void standBy() {
        // Checks if there is correct power.
        if (voltageSensor.getVoltage() != 120) {
            return;
        }
        if (!voltageSensor.isVoltageCorrect()){
            writer.println(MachineState.ERROR_LEDS.getCommand());
            return;
        }
        // Checks state of power from powerButton
        if (!powerButton.get())  {
            return;
        }

        // Checks if the reservoir has water.
        if (reservoirSensor.hasWater()){
            writer.println(MachineState.STANDBY_LEDS_WITH_WATER.getCommand());
        } else {
            writer.println(MachineState.STANDBY_LEDS_WITHOUT_WATER.getCommand());
        }

        // The timer and for loop are so the coffee maker does not spam the socket. It waits for
        // a timer or other inputs to move on. It will wait for 2.5 seconds before moving on.
        //timer.set(100);
        //timer.reset();
        //while(!timer.timeout() && !brewButton.getStatus() && !heatingButton.getStatus() && !powerButton.get());
    }

    public static synchronized void brewing(){
        System.out.println("in brewing");
        // Temp sensor, reservoir sensor, lid sensor ,carafe sensor, led bank, heater, timer
        //brewButton.negate();

        if (carafeSensor.carafeInPlace() & reservoirSensor.hasWater() & lidSensor.isClosed()) {
            writer.println(MachineState.BREWING_LEDS.getCommand());
            heating();

            System.out.println("Brewing");
            writer.println("SB");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            writer.println("C25");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            writer.println("C50");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            writer.println("C75");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            writer.println("CI1");

            writer.println("FinB");


            if(brewButton.getStatus()){
                brewButton.turnOff();
            }

            System.out.println("Brewing Done");
            writer.println(MachineState.COOL_DOWN.getCommand());
        } else {
            System.out.println("Failed to Brew");
        }
    }


    public static synchronized void heating() {
        System.out.println("in heating");
        // Done heating button, temp sensor, carafe sensor, led bank, heater, timer
        // Basic check for carafe

        if (!carafeSensor.carafeInPlace()) {
            System.out.println("Heating failed carafe not in place");
            return;
        }

        writer.println(MachineState.HEATING_LEDS.getCommand());
        writer.println(MachineState.HEAT_UP.getCommand());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        writer.println(MachineState.COOL_DOWN.getCommand());
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
        ExecutorService threadRunner = Executors.newFixedThreadPool(5);
        ScheduledExecutorService readExecutor = Executors.newScheduledThreadPool(2);

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
                AtomicBoolean running = new AtomicBoolean(true);
                AtomicBoolean machineOn = new AtomicBoolean(true);
                AtomicReference<String> next = new AtomicReference<>("");
                System.out.println("\nSocket thread running");

                readExecutor.scheduleAtFixedRate(() -> {
                    standBy();
                    try {
                        next.set(reader.readLine());


                        switch(next.get()){
                                case "BBP": // Brew Button Pressed
                                    // System.out.println("Brew button pressed");
                                    //brewButton.negate();
                                    threadRunner.submit(Main::brewing);
                                    break;
                                case "HBP": // Heating Button Pressed
                                    System.out.println("Heating button pressed");
                                    heatingButton.negate();
                                    threadRunner.submit(Main::heating);
                                    break;
                                case "PBP": // Power Button Pressed
                                    System.out.println("Power button pressed");
                                    powerButton.negate();
                                    if (machineOn.get()) {
                                        machineOn.set(false);
                                        writer.println("POFF");
                                    } else {
                                        machineOn.set(true);
                                        if (reservoirSensor.hasWater()) {
                                            writer.println("PWON");
                                        } else {
                                            writer.println("PEON");
                                        }
                                    }
                                    break;
                                case "CSS": // Carafe Sensor Set;
                                    carafeSensor.negate();
                                    System.out.println("Carafe is " + carafeSensor.carafeInPlace());
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
                                case "STBY":
                                    System.out.println("In standby");
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
                                case "OFF" :
                                    writer.println("OFF");
                                    running.set(false);
                                    break;
                                default:
                                    System.out.println("Unknown command " + next);
                            }

                        } catch (IOException e) {
                            System.out.println("Test");
                        }
                }, 0, 1, TimeUnit.SECONDS);
                while(running.get()) {
                    // This loop is here to ensure
                    // perserThread does not kill itself
                    // until the stopSim button is pressed
                    // On vis.
                }
                threadRunner.shutdown();
                readExecutor.shutdown();
                try {
                    mySocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        perserThread.start();

        //////// SOCKET SECTION ////////

        //////// END SOCKET SECTION ////////

    }
}