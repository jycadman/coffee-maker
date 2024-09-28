import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * CoffeeMakerPOVs is the class that sets up the POVs to interact with. This is
 * the boilerplate class that sets up the entire visualization. This needs to be refined.
 * Meaning I (Matt) have to make more comments and specify more interactables within the
 * Visualization.
 */
public class CoffeeMakerPOVs extends Application {
    private Stage Cafe;
    private POVList currentPOV;
    private final Scene FrontView;
    private final BorderPane FrontPane = new BorderPane();
    private ImageView[][] FrontArray;
    private final Scene LeftView;
    private final BorderPane LeftPane = new BorderPane();
    private ImageView[][] LeftArray;
    private final Scene RightView;
    private final BorderPane RightPane = new BorderPane();

    private ImageView[][] RightArray;
    private final Scene BackView;
    private final BorderPane BackPane = new BorderPane();

    private ImageView[][] BackArray;
    private final Scene TopView;
    private final BorderPane TopPane = new BorderPane();

    private ImageView[][] TopArray;
    private final VBox POVMenu = new VBox();
    private final VBox ErrorMenu = new VBox();

    public CoffeeMakerPOVs() {
        makeMenus();

        this.FrontView = makeFrontPOV();
        this.LeftView = makeLeftPOV();
        this.RightView = makeRightPOV();
        this.BackView = makeBackPOV();
        this.TopView = makeTopPOV();

        this.currentPOV = POVList.FRONT;
    }

    private void makeMenus() {
        Button FrontPOV = new Button();
        FrontPOV.setText("Switch to Front");
        FrontPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FrontPane.setLeft(ErrorMenu);
                FrontPane.setRight(POVMenu);
                Cafe.setScene(FrontView);
            }
        });


        Button RightPOV = new Button();
        RightPOV.setText("Switch to Right");
        RightPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RightPane.setLeft(ErrorMenu);
                RightPane.setRight(POVMenu);
                Cafe.setScene(RightView);
            }
        });

        Button LeftPOV = new Button();
        LeftPOV.setText("Switch to Left");
        LeftPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LeftPane.setLeft(ErrorMenu);
                LeftPane.setRight(POVMenu);
                Cafe.setScene(LeftView);
            }
        });

        Button TopPOV = new Button();
        TopPOV.setText("Switch to Top");
        TopPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TopPane.setLeft(ErrorMenu);
                TopPane.setRight(POVMenu);
                Cafe.setScene(TopView);
            }
        });

        Button BackPOV = new Button();
        BackPOV.setText("Switch to Back");
        BackPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BackPane.setLeft(ErrorMenu);
                BackPane.setRight(POVMenu);
                Cafe.setScene(BackView);
            }
        });

        Button Overheat = new Button();
        Overheat.setText("Overheat");
        Overheat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Do Overheat");
            }
        });

        this.POVMenu.getChildren().add(FrontPOV);
        this.POVMenu.getChildren().add(RightPOV);
        this.POVMenu.getChildren().add(LeftPOV);
        this.POVMenu.getChildren().add(TopPOV);
        this.POVMenu.getChildren().add(BackPOV);
        this.POVMenu.setPrefWidth(150);
        this.POVMenu.setAlignment(Pos.CENTER_RIGHT);


        this.ErrorMenu.getChildren().add(Overheat);
        this.ErrorMenu.setPrefWidth(150);
        this.ErrorMenu.setAlignment(Pos.CENTER_LEFT);

    }

    private Scene makeFrontPOV() {

        Image FrontOFF = new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\POV\\Front\\Front.png");
        ImageView FrontPOV = new ImageView();
        FrontPOV.setImage(FrontOFF);
        StackPane FrontStack = new StackPane();
        FrontStack.getChildren().add(FrontPOV);

        DeviceComponent PowButton = new DeviceComponent("Power Button", "Provide Power", new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\PowerButton.png"));

        DeviceComponent HeatButton = new DeviceComponent("Keep Warm", "Keep Warm", new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\KeepWarmButton.png"));

        DeviceComponent BrewButton = new DeviceComponent("Brew", "Brew", new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\BrewButton.png"));

        this.FrontPane.setLeft(this.ErrorMenu);
        this.FrontPane.setRight(this.POVMenu);
        this.FrontPane.setCenter(FrontStack);

        return new Scene(this.FrontPane);
    }

    private Scene makeLeftPOV() {
        Image LeftUnplugged = new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\POV\\Left\\LeftUnplugged.png");
        ImageView LeftPOV = new ImageView();
        LeftPOV.setImage(LeftUnplugged);
        StackPane LeftStack = new StackPane();
        LeftStack.getChildren().add(LeftPOV);
        this.LeftPane.setCenter(LeftStack);

        return new Scene(this.LeftPane);
    }

    private Scene makeRightPOV() {
        Image WaterFull = new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\POV\\Right\\RightWaterFull.png");
        ImageView RightPOV = new ImageView();
        RightPOV.setImage(WaterFull);
        StackPane RightStack = new StackPane();
        RightStack.getChildren().add(RightPOV);
        this.RightPane.setCenter(RightStack);

        return new Scene(this.RightPane);
    }

    private Scene makeBackPOV() {
        Image BackEmpty = new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\POV\\Back\\BackWaterEmpty.png");
        ImageView BackPOV = new ImageView();
        BackPOV.setImage(BackEmpty);
        StackPane BackStack = new StackPane();
        BackStack.getChildren().add(BackPOV);
        this.BackPane.setCenter(BackStack);
        return new Scene(this.BackPane);
    }

    private Scene makeTopPOV() {
        Image TopOpenEmpty = new Image("C:\\Users\\Admin.DIGITALSTORM-PC\\IdeaProjects\\CoffeeMakerVisual\\resources\\CoffeeMakerImages\\POV\\Top\\TopWaterEmptyLidOpened.png");
        ImageView TopPOV = new ImageView();
        TopPOV.setImage(TopOpenEmpty);
        StackPane TopStack = new StackPane();
        TopStack.getChildren().add(TopPOV);
        this.TopPane.setCenter(TopStack);

        return new Scene(this.TopPane);
    }

    @Override
    public void start(Stage Cafe) throws Exception {

        this.Cafe = Cafe;
        this.Cafe.setTitle("Coffee Making");
        this.Cafe.setScene(FrontView);
        this.Cafe.show();
    }
}
