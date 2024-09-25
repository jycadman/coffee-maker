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

                    switch (nextCommand) {
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
                        case "PLT": // Pink LED on
                            System.out.println("Pink LED on");
                            break;
                        case "PLF": // Pink LED off
                            System.out.println("Pink LED off");
                            break;
                        case "GLT": // Green LED on
                            System.out.println("Green LED on");
                            break;
                        case "GLF": // Green LED off
                            System.out.println("Green LED off");
                            break;
                        case "BLT": // Blue LED on
                            System.out.println("Blue LED on");
                            break;
                        case "BLF": // Blue LED off
                            System.out.println("Blue LED off");
                            break;
                        case "YLT": // Yellow LED on
                            System.out.println("Yellow LED on");
                            break;
                        case "YLF": // Yellow LED off
                            System.out.println("Yellow LED off");
                            break;
                        case "RLT": // Red LED on
                            System.out.println("Red LED on");
                            break;
                        case "RLF": // Red LED off
                            System.out.println("Red LED off");
                            break;
                        default:
                            System.out.println("Unknown command");
                    }
                }

            }
        });
        perserThread.start();

        while(true){
            next = scanner.nextLine();
            System.out.println(next);
            writer.println(next);
        }
    }
}

/*
Command List

*/