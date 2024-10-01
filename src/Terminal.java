import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Terminal {
    static PrintWriter writer = null; // Use this to write to the coffee maker.
    static BufferedReader reader = null; // Use this to read from the coffee maker.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket coffeeMaker;
        String next;

        // Connects the socket to the coffee maker and sets up
        // writer and reader.
        try {
            coffeeMaker = new Socket(InetAddress.getByName(null), 5000);
            writer = new PrintWriter(coffeeMaker.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(coffeeMaker.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connected");

        // Sits there and consumes data given from the machine
        Thread perserThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("\nSocket thread running");
                while(true) {
                    String nextCommand = null;
                    try {
                        nextCommand = reader.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // These are the commands that the coffee maker needs to send
                    // so that the terminal knows it's status.
                    switch (nextCommand) {
                        // Commands for Carafe
                        case "C00": // Carafe empty
                            System.out.println("Carafe empty");
                            break;
                        case "C25": // Carafe 25%
                            System.out.println("Carafe 25%");
                            break;
                        case "C50": // Carafe 50%
                            System.out.println("Carafe 50%");
                            break;
                        case "C75": // Carafe 75%
                            System.out.println("Carafe 75%");
                            break;
                        case "CI1": // Carafe is full
                            System.out.println("Carafe full");
                            break;

                        // Commands for LEDs
                        case "BLED": // Brewing LEDs
                            System.out.println("brew LED on");
                            break;
                        case "HLED": // Heating LEDs
                            System.out.println("heating LED on");
                            break;
                        case "ELED": // Error LEDs
                            System.out.println("error LED on");
                            break;
                        case "ALO": // ALL LEDs off
                            System.out.println("all LED off");
                            break;
                        case "SLTW": // Standby with water
                            System.out.println("StandBy with water");
                            break;
                        case "SLFW": // Standby without water
                            System.out.println("StandBy without water");
                            break;

                        // Commands for devices.
                        case "HHU": // Heater heat up
                            System.out.println("heating up");
                            writer.println("TSS");
                            writer.println("215");
                            break;
                        case "HCD": // Heater cool down
                            System.out.println("cooling down");
                            writer.println("TSS");
                            writer.println("0");
                            break;
                        default:
                            System.out.println("Unknown command " + nextCommand);
                    }
                }

            }
        });
        perserThread.start();

        // Send initial startup commands

        // Sends data to the coffee maker with writer.
        while(true){
            next = scanner.nextLine();
            System.out.println(next);
            writer.println(next);
        }
    }
}