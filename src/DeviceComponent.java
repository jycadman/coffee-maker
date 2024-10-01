import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


/**
 * Device Component
 * This class handles the creation of Device Component related objects.
 * Examples of this: Power Button, Brew Button, Heater, Power Plug, etc.
 * This class should be able to create unique DeviceComponent objects that
 * have their own functionalities if utilized (and if this class object) is
 * set up properly.
 */
public class DeviceComponent extends UserControl implements Component{

    private final ImageView componentView;
    private final Image componentImage;

    static PrintWriter writer = null; // Use this to write to the coffee maker.
    static BufferedReader reader = null; // Use this to read from the coffee maker.


    public DeviceComponent(String name, String command, Image img) {
        super(name, command);
        this.componentImage = img;
        componentView = new ImageView();
        componentView.setImage(this.componentImage);

        DropShadow outline = new DropShadow();
        outline.setColor(Color.GREEN);
        outline.setRadius(10);
        outline.setSpread(0.5);

        Socket coffeeMaker;

        try {
            coffeeMaker = new Socket(InetAddress.getByName(null), 5000);
            writer = new PrintWriter(coffeeMaker.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(coffeeMaker.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.componentView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actionLogic();
            }
        });

        this.componentView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                componentView.setEffect(outline);
            }
        });

        this.componentView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                componentView.setEffect(null);
            }
        });
    }

    @Override
    public void actionLogic() {
        sendMessage();
    }

    @Override
    public ImageView getComponentView() {
        return this.componentView;
    }

    @Override
    public void sendMessage(){
        writer.println(this.getComm());
    }
}
