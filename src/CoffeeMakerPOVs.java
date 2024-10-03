import javafx.animation.AnimationTimer;
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

import javax.crypto.Mac;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

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

    // Component Interactables that need to be accessed by multiple methods
    private final DeviceComponent frontCarafe =  new DeviceComponent("Carafe", "CSS", new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeEmpty.png"), writer);
    private final DeviceComponent rightCarafe =  new DeviceComponent("Carafe", "CSS", new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeEmptyRight.png"), writer);
    DeviceComponent coffeeGrind = new DeviceComponent("Coffee Grind", "Coffee", new Image("file:resources/CoffeeMakerImages/InsertCoffee.png"), writer);
    DeviceComponent coffeeHolder = new DeviceComponent("Coffee Holder", "Hold", new Image("file:resources/CoffeeMakerImages/CoffeeHolder.png"), writer);

    // Front POV items
    private final Scene FrontView;
    private final BorderPane FrontPane = new BorderPane();
    private final ImageView FrontImagePOV = new ImageView();

    // Left POV items
    private final Scene LeftView;
    private final BorderPane LeftPane = new BorderPane();
    private final ImageView LeftImagePOV = new ImageView();

    // Right POV items
    private final Scene RightView;
    private final BorderPane RightPane = new BorderPane();
    private final ImageView RightImagePOV = new ImageView();

    // Back POV items
    private final Scene BackView;
    private final BorderPane BackPane = new BorderPane();
    private final ImageView BackImagePOV = new ImageView();

    // Top POV items
    private final Scene TopView;
    private final BorderPane TopPane = new BorderPane();
    private final ImageView TopImagePOV = new ImageView();

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
    private WaterState currentWater = WaterState.WEMPTY;
    private LidPosition currentLidPos = LidPosition.CLOSED;
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
                currentPOV = POVList.FRONT;
                changeVisibility();
                FrontPane.setLeft(LeftMenu);
                FrontPane.setRight(RightMenu);
                Cafe.setScene(FrontView);
            }
        });

        Button RightPOV = new Button();
        RightPOV.setText("Switch to Right");
        RightPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentPOV = POVList.RIGHT;
                changeVisibility();
                RightPane.setLeft(LeftMenu);
                RightPane.setRight(RightMenu);
                Cafe.setScene(RightView);
            }
        });

        Button LeftPOV = new Button();
        LeftPOV.setText("Switch to Plug");
        LeftPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentPOV = POVList.LEFT;
                changeVisibility();
                LeftPane.setLeft(LeftMenu);
                LeftPane.setRight(RightMenu);
                Cafe.setScene(LeftView);
            }
        });

        Button TopPOV = new Button();
        TopPOV.setText("Switch to Top");
        TopPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentPOV = POVList.TOP;
                changeVisibility();
                TopPane.setLeft(LeftMenu);
                TopPane.setRight(RightMenu);
                Cafe.setScene(TopView);
            }
        });

        Button BackPOV = new Button();
        BackPOV.setText("Switch to Back");
        BackPOV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentPOV = POVList.BACK;
                changeVisibility();
                BackPane.setLeft(LeftMenu);
                BackPane.setRight(RightMenu);
                Cafe.setScene(BackView);
            }
        });

        // Error Buttons
        Button Overheat = new Button();
        Overheat.setText("Overheat");
        Overheat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("TSS");
                writer.println("1000");
                System.out.println("Do Overheat");
            }
        });

        Button stopSim = new Button();
        stopSim.setText("Stop Sim");
        stopSim.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("OFF");
            }
        });

        // Voltage Buttons
        Text currentVolts = new Text("Power Block Voltage: 120");
        Button setVoltage50 = new Button();
        setVoltage50.setText("Set Voltage to 50");
        setVoltage50.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("VSS");
                writer.println("50");
                currentVoltage = VoltState.VOLT50;
                currentVolts.setText("Power Block Voltage: " + VoltState.VOLT50.getVoltage());
            }
        });
        Button setVoltage120 = new Button();
        setVoltage120.setText("Set Voltage to 120");
        setVoltage120.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("VSS");
                writer.println("120");
                currentVoltage = VoltState.VOLT120;
                currentVolts.setText("Power Block Voltage: " + VoltState.VOLT120.getVoltage());
            }
        });
        Button setVoltage300 = new Button();
        setVoltage300.setText("Set Voltage to 300");
        setVoltage300.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("VSS");
                writer.println("300");
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
                    frontCarafe.getComponentView().setImage(CarafeState.C0.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    rightCarafe.getComponentView().setImage(CarafeState.C0.getRightCarafe());
                }
                currentCarafe = CarafeState.C0;
            }
        });

        Button carafe25 = new Button();
        carafe25.setText("Fill to 25%");
        carafe25.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    frontCarafe.getComponentView().setImage(CarafeState.C25.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    rightCarafe.getComponentView().setImage(CarafeState.C25.getRightCarafe());
                }
                currentCarafe = CarafeState.C25;
            }
        });

        Button carafe50 = new Button();
        carafe50.setText("Fill to 50%");
        carafe50.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    frontCarafe.getComponentView().setImage(CarafeState.C50.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    rightCarafe.getComponentView().setImage(CarafeState.C50.getRightCarafe());
                }
                currentCarafe = CarafeState.C50;
            }
        });

        Button carafe75 = new Button();
        carafe75.setText("Fill to 75%");
        carafe75.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    frontCarafe.getComponentView().setImage(CarafeState.C75.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    rightCarafe.getComponentView().setImage(CarafeState.C75.getRightCarafe());
                }
                currentCarafe = CarafeState.C75;
            }
        });

        Button carafeFull = new Button();
        carafeFull.setText("Fill to full");
        carafeFull.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPOV.equals(POVList.FRONT)) {
                    frontCarafe.getComponentView().setImage(CarafeState.C100.getFrontCarafe());
                }
                else if (currentPOV.equals(POVList.RIGHT)) {
                    rightCarafe.getComponentView().setImage(CarafeState.C100.getRightCarafe());
                }
                currentCarafe = CarafeState.C100;
            }
        });

        // Water Buttons
        Text waterText = new Text("Water Menu");

        Button waterEmpty = new Button();
        waterEmpty.setText("Empty reservoir");
        waterEmpty.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("RSF");
                currentWater = WaterState.WEMPTY;
                currentMachineState = MachineState.STANDBY_LEDS_WITHOUT_WATER;
                RightImagePOV.setImage(WaterState.WEMPTY.getRight());
                BackImagePOV.setImage(WaterState.WEMPTY.getBack());
                if (currentLidPos.equals(LidPosition.CLOSED)) {
                    TopImagePOV.setImage(WaterState.WEMPTY.getTopClosed());
                }
                else {
                    TopImagePOV.setImage(WaterState.WEMPTY.getTopOpen());
                }
            }
        });

        Button water25 = new Button();
        water25.setText("Fill to 25%");
        water25.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("RST");
                currentWater = WaterState.W25;
                currentMachineState = MachineState.STANDBY_LEDS_WITH_WATER;
                RightImagePOV.setImage(WaterState.W25.getRight());
                BackImagePOV.setImage(WaterState.W25.getBack());
                if (currentLidPos.equals(LidPosition.CLOSED)) {
                    TopImagePOV.setImage(WaterState.W25.getTopClosed());
                }
                else {
                    TopImagePOV.setImage(WaterState.W25.getTopOpen());
                }
            }
        });

        Button water50 = new Button();
        water50.setText("Fill to 50%");
        water50.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("RST");
                currentWater = WaterState.W50;
                currentMachineState = MachineState.STANDBY_LEDS_WITH_WATER;
                RightImagePOV.setImage(WaterState.W50.getRight());
                BackImagePOV.setImage(WaterState.W50.getBack());
                if (currentLidPos.equals(LidPosition.CLOSED)) {
                    TopImagePOV.setImage(WaterState.W50.getTopClosed());
                }
                else {
                    TopImagePOV.setImage(WaterState.W50.getTopOpen());
                }
            }
        });

        Button water75 = new Button();
        water75.setText("Fill to 75%");
        water75.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("RST");
                currentWater = WaterState.W75;
                currentMachineState = MachineState.STANDBY_LEDS_WITH_WATER;
                RightImagePOV.setImage(WaterState.W75.getRight());
                BackImagePOV.setImage(WaterState.W75.getBack());
                if (currentLidPos.equals(LidPosition.CLOSED)) {
                    TopImagePOV.setImage(WaterState.W75.getTopClosed());
                }
                else {
                    TopImagePOV.setImage(WaterState.W75.getTopOpen());
                }
            }
        });

        Button waterFull = new Button();
        waterFull.setText("Fill to full");
        waterFull.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("RST");
                currentWater = WaterState.W100;
                currentMachineState = MachineState.STANDBY_LEDS_WITH_WATER;
                RightImagePOV.setImage(WaterState.W100.getRight());
                BackImagePOV.setImage(WaterState.W100.getBack());
                if (currentLidPos.equals(LidPosition.CLOSED)) {
                    TopImagePOV.setImage(WaterState.W100.getTopClosed());
                }
                else {
                    TopImagePOV.setImage(WaterState.W100.getTopOpen());
                }
            }
        });

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

        stopSim.setVisible(false);

        this.ErrorMenu.getChildren().addAll(Overheat, stopSim);
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
                if (currentMachineState.equals(MachineState.BREW_BUTTON_PRESSED)) {
                    switch (this.currentCarafe) {
                        case C0, C25, C50, C75, C100 -> this.frontCarafe.getComponentView().setImage(this.currentCarafe.getFrontPouring());
                    }
                }
                else {
                    switch (this.currentCarafe) {
                        case C0, C25, C50, C75, C100 -> this.frontCarafe.getComponentView().setImage(this.currentCarafe.getFrontCarafe());
                    }
                }
                if (this.carafePresence.equals(CarafeInPlace.MISSING)) {
                    this.frontCarafe.getComponentView().setTranslateX(-300);
                }
                else {
                    this.frontCarafe.getComponentView().setTranslateX(-15);
                }
                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(false);
                this.CarafeMenu.setVisible(true);
            }
            case LEFT  -> {

                this.VoltageMenu.setVisible(true);
                this.WaterMenu.setVisible(false);
                this.CarafeMenu.setVisible(false);
            }
            case RIGHT -> {
                if (currentMachineState.equals(MachineState.BREW_BUTTON_PRESSED)) {
                    switch (this.currentCarafe) {
                        case C0, C25, C50, C75, C100 -> this.rightCarafe.getComponentView().setImage(this.currentCarafe.getRightPouring());
                    }
                }
                else {
                    switch (this.currentCarafe) {
                        case C0, C25, C50, C75, C100 -> this.rightCarafe.getComponentView().setImage(this.currentCarafe.getRightCarafe());
                    }
                }
                if (this.carafePresence.equals(CarafeInPlace.MISSING)) {
                    this.rightCarafe.getComponentView().setTranslateX(-400);
                    rightCarafe.getComponentView().setScaleX(0.33);
                    rightCarafe.getComponentView().setScaleY(0.33);
                }
                else {
                    this.rightCarafe.getComponentView().setTranslateX(-170);
                    rightCarafe.getComponentView().setScaleX(0.44);
                    rightCarafe.getComponentView().setScaleY(0.44);
                }
                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(true);
                this.CarafeMenu.setVisible(true);
            }
            case TOP   -> {

                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(false);
                this.CarafeMenu.setVisible(false);
            }
            case BACK  -> {

                this.VoltageMenu.setVisible(false);
                this.WaterMenu.setVisible(true);
                this.CarafeMenu.setVisible(false);
            }
        }

    }

    private Scene makeFrontPOV() {

        Image FrontOFF = new Image("file:resources/CoffeeMakerImages/POV/Front/FrontOff.png");
        this.FrontImagePOV.setImage(FrontOFF);
        StackPane frontStack = new StackPane();
        frontStack.setPrefWidth(1000);

        DeviceComponent PowButton = new DeviceComponent("Power Button", "PBP", new Image("file:resources/CoffeeMakerImages/PowerButton.png"),writer);

        DeviceComponent BrewButton = new DeviceComponent("Brew", "BBP", new Image("file:resources/CoffeeMakerImages/BrewButton.png"),writer);

        DeviceComponent HeatButton = new DeviceComponent("Keep Warm", "HBP", new Image("file:resources/CoffeeMakerImages/KeepWarmButton.png"),writer);


        frontStack.getChildren().addAll(this.FrontImagePOV, PowButton.getComponentView(), HeatButton.getComponentView(), BrewButton.getComponentView(), frontCarafe.getComponentView());
        PowButton.getComponentView().setTranslateX(171);
        PowButton.getComponentView().setTranslateY(-257);
        PowButton.getComponentView().setScaleX(1.65);
        PowButton.getComponentView().setScaleY(1.65);

        BrewButton.getComponentView().setTranslateX(3);
        BrewButton.getComponentView().setTranslateY(-257);
        BrewButton.getComponentView().setScaleX(1.35);
        BrewButton.getComponentView().setScaleY(1.35);

        HeatButton.getComponentView().setTranslateX(-151);
        HeatButton.getComponentView().setTranslateY(-257);
        HeatButton.getComponentView().setScaleX(1.35);
        HeatButton.getComponentView().setScaleY(1.35);

        frontCarafe.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("CSS");
                if (carafePresence.equals(CarafeInPlace.PRESENT)) {
                    carafePresence = CarafeInPlace.MISSING;
                    frontCarafe.getComponentView().setTranslateX(-300);
                }
                else {
                    carafePresence = CarafeInPlace.PRESENT;
                    frontCarafe.getComponentView().setTranslateX(-15);
                }
            }
        });
        this.frontCarafe.getComponentView().setScaleX(0.5);
        this.frontCarafe.getComponentView().setScaleY(0.5);
        this.frontCarafe.getComponentView().setTranslateX(-14);
        this.frontCarafe.getComponentView().setTranslateY(75);

        this.FrontPane.setLeft(this.LeftMenu);
        this.FrontPane.setRight(this.RightMenu);
        this.FrontPane.setCenter(frontStack);

        return new Scene(this.FrontPane);
    }

    private Scene makeLeftPOV() {
        Image LeftUnplugged = new Image("file:resources/CoffeeMakerImages/POV/Left/LeftUnplugged.png");
        Image LeftPlugged = new Image("file:resources/CoffeeMakerImages/POV/Left/LeftPlugged.png");
        this.LeftImagePOV.setImage(LeftUnplugged);
        StackPane leftStack = new StackPane();
        leftStack.setPrefWidth(1000);
        this.LeftPane.setCenter(leftStack);

        DeviceComponent powerBlock = new DeviceComponent("Power Block", "Provide Power", new Image("file:resources/CoffeeMakerImages/PowerBlock.png"), writer);

        leftStack.getChildren().addAll(this.LeftImagePOV, powerBlock.getComponentView());

        powerBlock.getComponentView().setTranslateY(8);
        powerBlock.getComponentView().setTranslateX(-295);
        powerBlock.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentPower.equals(PowerBlockState.UNPLUGGED)) {
                    currentPower = PowerBlockState.PLUGGED;
                    currentMachineState = MachineState.STANDBY;
                    LeftImagePOV.setImage(LeftPlugged);
                }
                else {
                    currentPower = PowerBlockState.UNPLUGGED;
                    currentMachineState = MachineState.ALL_LEDS_OFF;
                    LeftImagePOV.setImage(LeftUnplugged);
                }
            }
        });

        return new Scene(this.LeftPane);
    }

    private Scene makeRightPOV() {
        Image WaterEmpty = new Image("file:resources/CoffeeMakerImages/POV/Right/RightWaterEmpty.png");
        this.RightImagePOV.setImage(WaterEmpty);
        StackPane rightStack = new StackPane();
        rightStack.setPrefWidth(1000);
        this.RightPane.setCenter(rightStack);

        rightCarafe.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("CSS");
                if (carafePresence.equals(CarafeInPlace.PRESENT)) {
                    carafePresence = CarafeInPlace.MISSING;
                    rightCarafe.getComponentView().setTranslateX(-400);
                    System.out.println("Carafe is now MISSING");
                    rightCarafe.getComponentView().setScaleX(0.33);
                    rightCarafe.getComponentView().setScaleY(0.33);
                }
                else {
                    carafePresence = CarafeInPlace.PRESENT;
                    rightCarafe.getComponentView().setTranslateX(-170);
                    System.out.println("Carafe is now PRESENT");
                    rightCarafe.getComponentView().setScaleX(0.44);
                    rightCarafe.getComponentView().setScaleY(0.44);
                }
            }
        });
        this.rightCarafe.getComponentView().setScaleX(0.44);
        this.rightCarafe.getComponentView().setScaleY(0.44);
        this.rightCarafe.getComponentView().setTranslateY(70);

        rightStack.getChildren().addAll(this.RightImagePOV, rightCarafe.getComponentView());

        return new Scene(this.RightPane);
    }

    private Scene makeBackPOV() {
        Image BackEmpty = new Image("file:resources/CoffeeMakerImages/POV/Back/BackWaterEmpty.png");
        this.BackImagePOV.setImage(BackEmpty);
        StackPane backStack = new StackPane();
        backStack.setPrefWidth(1000);
        backStack.getChildren().add(this.BackImagePOV);
        this.BackPane.setCenter(backStack);
        return new Scene(this.BackPane);
    }

    private Scene makeTopPOV() {
        Image TopClosedEmpty = new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterEmptyLidClosed.png");
        Image TopOpenEmpty = new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterEmptyLidOpened.png");
        this.TopImagePOV.setImage(TopClosedEmpty);
        StackPane topStack = new StackPane();
        topStack.setPrefWidth(1000);

        DeviceComponent coffeeLidOpen = new DeviceComponent("Open Lid", "LST", new Image("file:resources/CoffeeMakerImages/POV/top/LidOpen.png"), writer);
        DeviceComponent coffeeLidClosed = new DeviceComponent("Closed Lid", "LSF", new Image("file:resources/CoffeeMakerImages/POV/top/LidClosed.png"), writer);

        topStack.getChildren().addAll(this.TopImagePOV, coffeeLidClosed.getComponentView(), coffeeLidOpen.getComponentView(), coffeeGrind.getComponentView(), coffeeHolder.getComponentView());

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
                writer.println("LST");
                coffeeLidOpen.getComponentView().setVisible(false);
                coffeeLidClosed.getComponentView().setVisible(true);

                if (currentGrindState.equals(CoffeeGrindState.MISSING)) {
                    coffeeHolder.getComponentView().setVisible(false);
                }
                else {
                    coffeeGrind.getComponentView().setVisible(false);
                }

                TopImagePOV.setImage(currentWater.getTopClosed());
            }
        });

        coffeeLidClosed.getComponentView().setTranslateX(-3);
        coffeeLidClosed.getComponentView().setTranslateY(182);
        coffeeLidClosed.getComponentView().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                writer.println("LSF");
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

                TopImagePOV.setImage(currentWater.getTopOpen());
            }
        });

        this.TopPane.setCenter(topStack);
        return new Scene(this.TopPane);
    }

    @Override
    public void start(Stage Cafe) throws Exception {

        this.Cafe = Cafe;
        this.Cafe.setTitle("Coffee Making");
        this.Cafe.setScene(FrontView);
        this.Cafe.show();

        AnimationTimer handleReader = new AnimationTimer() {
            private long repeater = 0;
            final ExecutorService readBuffer = Executors.newFixedThreadPool(1);
            final AtomicReference<Boolean> blink = new AtomicReference<>(false);
            @Override
            public void handle(long now) {
                if (now - repeater >= 500_000_000) {
                    repeater = now;
                    AtomicReference<String> next = new AtomicReference<>("");
                    switch (currentMachineState) {
                        case STANDBY -> {
                            if (currentPower.equals(PowerBlockState.PLUGGED)) {
                                FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontY.png"));
                            } else {
                                currentMachineState = MachineState.ALL_LEDS_OFF;
                                writer.println("VSS");
                                writer.println("0");
                            }
                        }
                        case STANDBY_LEDS_WITHOUT_WATER -> {
                            if (currentPower.equals(PowerBlockState.PLUGGED)) {
                                if (blink.get()) {
                                    FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontBYG.png"));
                                    blink.set(false);
                                } else {
                                    FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontYG.png"));
                                    blink.set(true);
                                }
                            } else {
                                currentMachineState = MachineState.ALL_LEDS_OFF;
                                writer.println("VSS");
                                writer.println("0");
                            }
                        }
                        case STANDBY_LEDS_WITH_WATER -> {
                            if (currentPower.equals(PowerBlockState.PLUGGED)) {
                                FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontYG.png"));
                            } else {
                                currentMachineState = MachineState.ALL_LEDS_OFF;
                                writer.println("VSS");
                                writer.println("0");
                            }
                        }
                        case BREW_BUTTON_PRESSED -> {
                            if (currentPower.equals(PowerBlockState.PLUGGED)) {
                                if (blink.get()) {
                                    FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontPYG.png"));
                                    blink.set(false);
                                } else {
                                    FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontPY.png"));
                                    blink.set(true);
                                }
                            } else {
                                currentMachineState = MachineState.ALL_LEDS_OFF;
                                writer.println("VSS");
                                writer.println("0");
                            }
                        }
                        case HEATING_BUTTON_PRESSED -> {
                            if (currentPower.equals(PowerBlockState.PLUGGED)) {
                                FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontPYG.png"));
                            } else {
                                currentMachineState = MachineState.ALL_LEDS_OFF;
                                writer.println("VSS");
                                writer.println("0");
                            }
                        }
                        case ALL_LEDS_OFF -> {
                            if (currentPower.equals(PowerBlockState.UNPLUGGED)) {
                                FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontOff.png"));
                            }
                            writer.println("VSS");
                            writer.println("0");
                        }
                        default -> {
                            if(currentPower.equals(PowerBlockState.PLUGGED)) {
                                if(blink.get()) {
                                    if (currentWater.equals(WaterState.WLOW) || currentWater.equals(WaterState.WEMPTY)) {
                                        FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontBYRG.png"));
                                        blink.set(false);
                                    } else {
                                        FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontYRG.png"));
                                        blink.set(false);
                                    }
                                } else {
                                    FrontImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Front/FrontYG.png"));
                                    blink.set(true);
                                }
                            } else {
                                currentMachineState = MachineState.ALL_LEDS_OFF;
                            }
                        }
                    }
                    readBuffer.submit(() -> {
                        if (currentPower.equals(PowerBlockState.PLUGGED)) {
                            try {
                                next.set(reader.readLine());

                                switch (next.get()) {
                                    case "SB" :
                                        currentMachineState = MachineState.BREW_BUTTON_PRESSED;
                                        frontCarafe.getComponentView().setImage(CarafeState.C0.getFrontPouring());
                                        rightCarafe.getComponentView().setImage(CarafeState.C0.getRightPouring());
                                        System.out.println("Start");
                                        break;
                                    case "FinB" :
                                        currentMachineState = MachineState.STANDBY_LEDS_WITHOUT_WATER;
                                        writer.println(MachineState.STANDBY.getCommand());
                                        frontCarafe.getComponentView().setImage(CarafeState.C100.getFrontCarafe());
                                        rightCarafe.getComponentView().setImage(CarafeState.C100.getRightCarafe());
                                        System.out.println("Finished");
                                        System.out.println(currentCarafe.getLevel());
                                        currentCarafe = CarafeState.C100;
                                        break;
                                    // Commands for Carafe
                                    case "C25": // Carafe 25%
                                        frontCarafe.getComponentView().setImage(CarafeState.C25.getFrontPouring());
                                        rightCarafe.getComponentView().setImage(CarafeState.C25.getRightPouring());
                                        RightImagePOV.setImage(WaterState.W75.getRight());
                                        BackImagePOV.setImage(WaterState.W75.getBack());
                                        TopImagePOV.setImage(WaterState.W75.getTopClosed());
                                        currentCarafe = CarafeState.C25;
                                        break;
                                    case "C50": // Carafe 50%
                                        frontCarafe.getComponentView().setImage(CarafeState.C50.getFrontPouring());
                                        rightCarafe.getComponentView().setImage(CarafeState.C50.getRightPouring());
                                        RightImagePOV.setImage(WaterState.W50.getRight());
                                        BackImagePOV.setImage(WaterState.W50.getBack());
                                        TopImagePOV.setImage(WaterState.W50.getTopClosed());
                                        currentCarafe = CarafeState.C50;
                                        break;
                                    case "C75": // Carafe 75%
                                        frontCarafe.getComponentView().setImage(CarafeState.C75.getFrontPouring());
                                        rightCarafe.getComponentView().setImage(CarafeState.C75.getRightPouring());
                                        RightImagePOV.setImage(WaterState.W25.getRight());
                                        BackImagePOV.setImage(WaterState.W25.getBack());
                                        TopImagePOV.setImage(WaterState.W25.getTopClosed());
                                        currentCarafe = CarafeState.C75;
                                        break;
                                    case "CI1": // Carafe is full
                                        frontCarafe.getComponentView().setImage(CarafeState.C100.getFrontCarafe());
                                        rightCarafe.getComponentView().setImage(CarafeState.C100.getRightCarafe());
                                        RightImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Right/RightWaterLow.png"));
                                        BackImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Back/BackWaterLow.png"));
                                        TopImagePOV.setImage(new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterLowLidClosed.png"));
                                        currentMachineState = MachineState.STANDBY_LEDS_WITHOUT_WATER;
                                        if (currentGrindState.equals(CoffeeGrindState.PRESENT)) {
                                            currentGrindState = CoffeeGrindState.MISSING;
                                        }
                                        currentCarafe = CarafeState.C100;
                                        writer.println("RSF");
                                        break;

                                    // Commands for LEDs
                                    case "BLED": // Brewing LEDs
                                        currentMachineState = MachineState.BREW_BUTTON_PRESSED;
                                        break;
                                    case "HLED": // Heating LEDs
                                        currentMachineState = MachineState.HEATING_BUTTON_PRESSED;
                                        break;
                                    case "ELED": // Error LEDs
                                        currentMachineState = MachineState.ERROR_LEDS;
                                        break;
                                    case "SLTW": // Standby with water
                                        currentMachineState = MachineState.STANDBY_LEDS_WITH_WATER;
                                        break;
                                    case "SLFW": // Standby without water
                                        currentMachineState = MachineState.STANDBY_LEDS_WITHOUT_WATER;
                                        break;
                                    // Commands for devices.
                                    case "HHU": // Heater heat up
                                        writer.println("TSS");
                                        writer.println("215");
                                        break;
                                    case "HCD": // Heater cool down
                                        writer.println("TSS");
                                        writer.println("0");
                                        break;
                                    case "PWON" :
                                        currentMachineState = MachineState.STANDBY_LEDS_WITH_WATER;
                                        break;
                                    case "PEON" :
                                        currentMachineState = MachineState.STANDBY_LEDS_WITHOUT_WATER;
                                        break;
                                    case "POFF" :
                                        currentMachineState = MachineState.STANDBY;
                                        break;
                                    case "OFF" :
                                        this.stop();
                                        break;
                                    default:
                                        System.out.println("Unknown command " + next);
                                }
                            } catch (IOException e) {
                                // Do nothing, continue code
                            }
                        }
                    });

                }

            }
        };
        handleReader.start();
    }
}