import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


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
    Image componentImage;


    public DeviceComponent(String name, String command, Image img) {
        super(name, command);
        this.componentImage = img;
        componentView = new ImageView();
        componentView.setImage(this.componentImage);

        this.componentView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actionLogic();
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
        System.out.println(this.getComm());
    }
}
