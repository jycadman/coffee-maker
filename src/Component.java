import javafx.scene.image.ImageView;

/**
 * Component interface for the DeviceComponent to implement functions for. Could be refined.
 */
public interface Component {
    ImageView getComponentView();

    void sendMessage();
}
