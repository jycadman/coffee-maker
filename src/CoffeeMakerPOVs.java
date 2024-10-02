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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * CoffeeMakerPOVs is the class that sets up the POVs to interact with. This is
 * the boilerplate class that sets up the entire visualization. This needs to be refined.
 * Meaning I (Matt) have to make more comments and specify more interactables within the
 * Visualization.
 */
public class CoffeeMakerPOVs extends Application {
    private Stage Cafe;

    // POV helper
    private POVList currentPOV;

    // Carafe ImageView
    private final ImageView CarafeImage = new ImageView();

    // Front POV items
    private final Scene FrontView;
    private final BorderPane FrontPane = new BorderPane();
    private final ImageView FrontPOV = new ImageView();
    private StackPane FrontStack;

    // Left POV items
    private final Scene LeftView;
    private final BorderPane LeftPane = new BorderPane();
    private final ImageView LeftPOV = new ImageView();
    private StackPane LeftStack;

    // Right POV items
    private final Scene RightView;
    private final BorderPane RightPane = new BorderPane();
    private final ImageView RightPOV = new ImageView();
    private StackPane RightStack;

    // Back POV items
    private final Scene BackView;
    private final BorderPane BackPane = new BorderPane();
    private final ImageView BackPOV = new ImageView();
    private StackPane BackStack;

    // Top POV items
    private final Scene TopView;
    private final BorderPane TopPane = new BorderPane();
    private final ImageView TopPOV = new ImageView();
    private StackPane TopStack;

    // Menu Boxes
    private final VBox POVMenu = new VBox();
    private final VBox ErrorMenu = new VBox();
    private final VBox VoltageMenu = new VBox();
    private final VBox WaterMenu = new VBox();
    private final VBox CarafeMenu = new VBox();
    private final VBox LeftMenu = new VBox();
    private final VBox RightMenu = new VBox();

    // Socket settings
    private static Socket coffeeMaker;
    private static PrintWriter writer = null; // Use this to write to the coffee maker.
    private static BufferedReader reader = null; // Use this to read from the coffee maker.

    // Coffee Maker Image States
    private MachineState currentMachineState = MachineState.ALL_LEDS_OFF;
    private CarafeInPlace carafePresence = CarafeInPlace.PRESENT;
    private CoffeeGrindState currentGrindState = CoffeeGrindState.MISSING;
    private CarafeState currentCarafe = CarafeState.C0;
    private PowerBlockState currentPower = PowerBlockState.UNPLUGGED;
    private VoltState currentVoltage = VoltState.VOLT120;
    public CoffeeMakerPOVs() {
        makeMenus();
        try {
            coffeeMaker = new Socket(InetAddress.getByName(null), 5000);
            writer = new PrintWriter(coffeeMaker.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(coffeeMaker.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.FrontView = makeFrontPOV();
        this.LeftView = makeLeftPOV();
        this.RightView = makeRightPOV();
        this.BackView = makeBackPOV();
        this.TopView = makeTopPOV();

        this.currentPOV = POVList.FRONT;
        changeVisibility();
    }

    private void makeMenus() {

        // POV Switching buttons
        Text POVText = new Text("POV Menu");

        Button FrontPOV = new Button();
        FrontPOV.setText("Switch to Front");
        FrontPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FrontPane.setLeft(LeftMenu);
                FrontPane.setRight(RightMenu);
                Cafe.setScene(FrontView);
                currentPOV = POVList.FRONT;
                changeVisibility();
            }
        });

        Button RightPOV = new Button();
        RightPOV.setText("Switch to Right");
        RightPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RightPane.setLeft(LeftMenu);
                RightPane.setRight(RightMenu);
                Cafe.setScene(RightView);
                currentPOV = POVList.RIGHT;
                changeVisibility();
            }
        });

        Button LeftPOV = new Button();
        LeftPOV.setText("Switch to Plug");
        LeftPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LeftPane.setLeft(LeftMenu);
                LeftPane.setRight(RightMenu);
                Cafe.setScene(LeftView);
                currentPOV = POVList.LEFT;
                changeVisibility();
            }
        });

        Button TopPOV = new Button();
        TopPOV.setText("Switch to Top");
        TopPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TopPane.setLeft(LeftMenu);
                TopPane.setRight(RightMenu);
                Cafe.setScene(TopView);
                currentPOV = POVList.TOP;
                changeVisibility();

            }
        });

        Button BackPOV = new Button();
        BackPOV.setText("Switch to Back");
        BackPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BackPane.setLeft(LeftMenu);
                BackPane.setRight(RightMenu);
                Cafe.setScene(BackView);
                currentPOV = POVList.BACK;
                changeVisibility();
            }
        });

        // Error Buttons
        Button Overheat = new Button();
        Overheat.setText("Overheat");
        Overheat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Do Overheat");
            }
        });

        // Voltage Buttons
        Text currentVolts = new Text("Power Block Voltage: 120");
        Button setVoltage50 = new Button();
        setVoltage50.setText("Set Voltage to 50");
        setVoltage50.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentVoltage = VoltState.VOLT50;
                currentVolts.setText("Power Block Voltage: " + VoltState.VOLT50.getVoltage());
            }
        });
        Button setVoltage120 = new Button();
        setVoltage120.setText("Set Voltage to 120");
        setVoltage120.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentVoltage = VoltState.VOLT120;
                currentVolts.setText("Power Block Voltage: " + VoltState.VOLT120.getVoltage());
            }
        });
        Button setVoltage300 = new Button();
        setVoltage300.setText("Set Voltage to 300");
        setVoltage300.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentVoltage = VoltState.VOLT300;
                currentVolts.setText("Power Block Voltage: " + VoltState.VOLT300.getVoltage());
            }
        });

        // Carafe Buttons
        Text carafeText = new Text("Carafe Menu");

        Button carafeEmpty = new Button();
        carafeEmpty.setText("Empty Carafe");
        carafeEmpty.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    CarafeImage.setImage(CarafeState.C0.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    CarafeImage.setImage(CarafeState.C0.getRightCarafe());
                }
            }
        });

        Button carafe25 = new Button();
        carafe25.setText("Fill to 25%");
        carafe25.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    CarafeImage.setImage(CarafeState.C25.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    CarafeImage.setImage(CarafeState.C25.getRightCarafe());
                }
            }
        });

        Button carafe50 = new Button();
        carafe50.setText("Fill to 50%");
        carafe50.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    CarafeImage.setImage(CarafeState.C50.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    CarafeImage.setImage(CarafeState.C50.getRightCarafe());
                }
            }
        });

        Button carafe75 = new Button();
        carafe75.setText("Fill to 75%");
        carafe75.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    CarafeImage.setImage(CarafeState.C75.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    CarafeImage.setImage(CarafeState.C75.getRightCarafe());
                }
            }
        });

        Button carafeFull = new Button();
        carafeFull.setText("Fill to full");
        carafeFull.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    CarafeImage.setImage(CarafeState.C100.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    CarafeImage.setImage(CarafeState.C100.getRightCarafe());
                }
            }
        });

        // Water Buttons
        Text waterText = new Text("Water Menu");

        Button waterEmpty = new Button();
        waterEmpty.setText("Empty reservoir");

        Button water25 = new Button();
        water25.setText("Fill to 25%");

        Button water50 = new Button();
        water50.setText("Fill to 50%");

        Button water75 = new Button();
        water75.setText("Fill to 75%");

        Button waterFull = new Button();
        waterFull.setText("Fill to full");

        // Menu setup
        this.POVMenu.getChildren().addAll(POVText, FrontPOV, RightPOV, LeftPOV, TopPOV, BackPOV);
        this.POVMenu.setPrefWidth(150);
        this.POVMenu.setAlignment(Pos.CENTER_RIGHT);
        this.POVMenu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.VoltageMenu.getChildren().addAll(currentVolts, setVoltage50, setVoltage120, setVoltage300);
        this.VoltageMenu.setPrefWidth(150);
        this.VoltageMenu.setAlignment(Pos.CENTER_LEFT);
        this.VoltageMenu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.WaterMenu.getChildren().addAll(waterText, waterEmpty, water25, water50, water75, waterFull);
        this.WaterMenu.setPrefWidth(150);
        this.WaterMenu.setAlignment(Pos.CENTER_RIGHT);
        this.WaterMenu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.CarafeMenu.getChildren().addAll(carafeText, carafeEmpty, carafe25, carafe50, carafe75, carafeFull);
        this.CarafeMenu.setPrefWidth(150);
        this.CarafeMenu.setAlignment(Pos.CENTER_RIGHT);
        this.CarafeMenu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.ErrorMenu.getChildren().add(Overheat);
        this.ErrorMenu.setPrefWidth(150);
        this.ErrorMenu.setAlignment(Pos.CENTER_LEFT);
        this.ErrorMenu.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.RightMenu.getChildren().addAll(this.CarafeMenu, this.POVMenu, this.WaterMenu);
        this.RightMenu.setAlignment(Pos.CENTER_RIGHT);

        this.LeftMenu.getChildren().addAll(this.ErrorMenu, this.VoltageMenu);
        this.LeftMenu.setAlignment(Pos.CENTER_LEFT);
    }

    private void changeVisibility() {
        switch (this.currentPOV) {
            case FRONT -> {
                System.out.println("Front!");
                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(false);
                this.CarafeMenu.setVisible(true);
            }
            case LEFT  -> {
                System.out.println("Left!");
                this.VoltageMenu.setVisible(true);
                this.WaterMenu.setVisible(false);
                this.CarafeMenu.setVisible(false);
            }
            case RIGHT -> {
                System.out.println("Right!");
                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(true);
                this.CarafeMenu.setVisible(true);
            }
            case TOP   -> {
                System.out.println("Top!");
                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(false);
                this.CarafeMenu.setVisible(false);
            }
            case BACK  -> {
                System.out.println("Back!");
                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(true);
                this.CarafeMenu.setVisible(false);
            }
        }

    }

    private Scene makeFrontPOV() {

        Image FrontOFF = new Image("file:resources/CoffeeMakerImages/POV/Front/Front.png");
        this.FrontPOV.setImage(FrontOFF);
        this.FrontStack = new StackPane();

        DeviceComponent PowButton = new DeviceComponent("Power Button", "Provide Power", new Image("file:resources/CoffeeMakerImages/PowerButton.png"),writer);

        DeviceComponent BrewButton = new DeviceComponent("Brew", "Brew", new Image("file:resources/CoffeeMakerImages/BrewButton.png"),writer);

        DeviceComponent HeatButton = new DeviceComponent("Keep Warm", "Keep Warm", new Image("file:resources/CoffeeMakerImages/KeepWarmButton.png"),writer);

        DeviceComponent Carafe =  new DeviceComponent("Carafe", "Car", new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeEmpty.png"), writer);


        this.FrontStack.getChildren().addAll(this.FrontPOV, PowButton.getComponentView(), HeatButton.getComponentView(), BrewButton.getComponentView(), Carafe.getComponentView());
        PowButton.getComponentView().setTranslateX(180);
        PowButton.getComponentView().setTranslateY(-257);
        PowButton.getComponentView().setScaleX(1.65);
        PowButton.getComponentView().setScaleY(1.65);

        BrewButton.getComponentView().setTranslateX(12);
        BrewButton.getComponentView().setTranslateY(-257);
        BrewButton.getComponentView().setScaleX(1.35);
        BrewButton.getComponentView().setScaleY(1.35);

        HeatButton.getComponentView().setTranslateX(-142);
        HeatButton.getComponentView().setTranslateY(-257);
        HeatButton.getComponentView().setScaleX(1.35);
        HeatButton.getComponentView().setScaleY(1.35);

        Carafe.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (carafePresence.equals(CarafeInPlace.PRESENT)) {
                    carafePresence = CarafeInPlace.MISSING;
                    Carafe.getComponentView().setTranslateX(-300);
                    System.out.println("Carafe is now MISSING");
                }
                else {
                    carafePresence = CarafeInPlace.PRESENT;
                    Carafe.getComponentView().setTranslateX(-5);
                    System.out.println("Carafe is now PRESENT");
                }
            }
        });
        Carafe.getComponentView().setScaleX(0.5);
        Carafe.getComponentView().setScaleY(0.5);
        Carafe.getComponentView().setTranslateX(-5);
        Carafe.getComponentView().setTranslateY(75);

        this.FrontPane.setLeft(this.LeftMenu);
        this.FrontPane.setRight(this.RightMenu);
        this.FrontPane.setCenter(this.FrontStack);

        return new Scene(this.FrontPane);
    }

    private Scene makeLeftPOV() {
        Image LeftUnplugged = new Image("file:resources/CoffeeMakerImages/POV/Left/LeftUnplugged.png");
        Image LeftPlugged = new Image("file:resources/CoffeeMakerImages/POV/Left/LeftPlugged.png");
        this.LeftPOV.setImage(LeftUnplugged);
        this.LeftStack = new StackPane();
        this.LeftPane.setCenter(LeftStack);

        DeviceComponent powerBlock = new DeviceComponent("Power Block", "Provide Power", new Image("file:resources/CoffeeMakerImages/PowerBlock.png"), writer);

        this.LeftStack.getChildren().addAll(this.LeftPOV, powerBlock.getComponentView());

        powerBlock.getComponentView().setTranslateY(8);
        powerBlock.getComponentView().setTranslateX(-295);
        powerBlock.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPower.equals(PowerBlockState.UNPLUGGED)) {
                    currentPower = PowerBlockState.PLUGGED;
                    LeftPOV.setImage(LeftPlugged);
                }
                else {
                    currentPower = PowerBlockState.UNPLUGGED;
                    LeftPOV.setImage(LeftUnplugged);
                }
            }
        });

        return new Scene(this.LeftPane);
    }

    private Scene makeRightPOV() {
        Image WaterFull = new Image("file:resources/CoffeeMakerImages/POV/Right/RightWaterEmpty.png");
        this.RightPOV.setImage(WaterFull);
        this.RightStack = new StackPane();
        this.RightStack.getChildren().add(this.RightPOV);
        this.RightPane.setCenter(RightStack);

        return new Scene(this.RightPane);
    }

    private Scene makeBackPOV() {
        Image BackEmpty = new Image("file:resources/CoffeeMakerImages/POV/Back/BackWaterEmpty.png");
        this.BackPOV.setImage(BackEmpty);
        this.BackStack = new StackPane();
        this.BackStack.getChildren().add(this.BackPOV);
        this.BackPane.setCenter(BackStack);
        return new Scene(this.BackPane);
    }

    private Scene makeTopPOV() {
        Image TopClosedEmpty = new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterEmptyLidClosed.png");
        Image TopOpenEmpty = new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterEmptyLidOpened.png");
        this.TopPOV.setImage(TopClosedEmpty);
        this.TopStack = new StackPane();
        boolean coffeePresent = false;

        DeviceComponent coffeeLidOpen = new DeviceComponent("Open Lid", "Open", new Image("file:resources/CoffeeMakerImages/POV/top/LidOpen.png"), writer);
        DeviceComponent coffeeLidClosed = new DeviceComponent("Closed Lid", "Close", new Image("file:resources/CoffeeMakerImages/POV/top/LidClosed.png"), writer);
        DeviceComponent coffeeGrind = new DeviceComponent("Coffee Grind", "Coffee", new Image("file:resources/CoffeeMakerImages/InsertCoffee.png"), writer);
        DeviceComponent coffeeHolder = new DeviceComponent("Coffee Holder", "Hold", new Image("file:resources/CoffeeMakerImages/CoffeeHolder.png"), writer);

        this.TopStack.getChildren().addAll(this.TopPOV, coffeeLidClosed.getComponentView(), coffeeLidOpen.getComponentView(), coffeeGrind.getComponentView(), coffeeHolder.getComponentView());

        coffeeHolder.getComponentView().setVisible(false);
        coffeeHolder.getComponentView().setTranslateY(190);
        coffeeHolder.getComponentView().setScaleX(1.08);
        coffeeHolder.getComponentView().setScaleY(1.08);
        coffeeHolder.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coffeeHolder.getComponentView().setVisible(false);
                coffeeGrind.getComponentView().setVisible(true);
                currentGrindState = CoffeeGrindState.PRESENT;
            }
        });

        coffeeGrind.getComponentView().setVisible(false);
        coffeeGrind.getComponentView().setTranslateY(190);
        coffeeGrind.getComponentView().setScaleX(1.08);
        coffeeGrind.getComponentView().setScaleY(1.08);
        coffeeGrind.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coffeeGrind.getComponentView().setVisible(false);
                coffeeHolder.getComponentView().setVisible(true);
                currentGrindState = CoffeeGrindState.MISSING;
            }
        });

        coffeeLidOpen.getComponentView().setTranslateX(-3);
        coffeeLidOpen.getComponentView().setTranslateY(50);
        coffeeLidOpen.getComponentView().setVisible(false);
        coffeeLidOpen.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coffeeLidOpen.getComponentView().setVisible(false);
                coffeeLidClosed.getComponentView().setVisible(true);

                if (currentGrindState.equals(CoffeeGrindState.MISSING)) {
                    coffeeHolder.getComponentView().setVisible(false);
                }
                else {
                    coffeeGrind.getComponentView().setVisible(false);
                }

                TopPOV.setImage(TopClosedEmpty);
            }
        });

        coffeeLidClosed.getComponentView().setTranslateX(-3);
        coffeeLidClosed.getComponentView().setTranslateY(182);
        coffeeLidClosed.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coffeeLidClosed.getComponentView().setVisible(false);
                coffeeLidOpen.getComponentView().setVisible(true);

                if(currentGrindState.equals(CoffeeGrindState.MISSING)) {
                    coffeeGrind.getComponentView().setVisible(false);
                    coffeeHolder.getComponentView().setVisible(true);
                }
                else {
                    coffeeGrind.getComponentView().setVisible(true);
                    coffeeHolder.getComponentView().setVisible(false);
                }

                TopPOV.setImage(TopOpenEmpty);
            }
        });

        this.TopPane.setCenter(TopStack);
        return new Scene(this.TopPane);
    }

    @Override
    public void start(Stage Cafe) throws Exception {

        this.Cafe = Cafe;
        this.Cafe.setTitle("Coffee Making");
        this.Cafe.setScene(FrontView);
        this.Cafe.show();

        Socket serverSocket;
    }
}