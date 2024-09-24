import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintWriter writer = null; // Use this to write to the coffee maker.
        BufferedReader reader = null; // Use this to read from the coffee maker.
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

        while(true){
            next = scanner.nextLine();
            System.out.println(next);
            writer.println(next);

            try {
                System.out.println(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
