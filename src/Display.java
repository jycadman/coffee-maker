import javafx.application.Application;
import javafx.stage.Stage;

public class Display extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage Cafe) throws Exception {
        CoffeeMakerPOVs POV = new CoffeeMakerPOVs();
        POV.start(Cafe);
    }
}
