import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum CarafeState {
    C0("C00",   new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeEmpty.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeEmptyPour.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/CarafeEmptyRight.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/CarafeEmptyRightPour.png")),
    C25("C25",  new Image("file:resources/CoffeeMakerImages/Carafe/Front/Carafe25.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Front/Carafe25Pour.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/Carafe25Right.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/Carafe25RightPour.png")),
    C50("C50%", new Image("file:resources/CoffeeMakerImages/Carafe/Front/Carafe50.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Front/Carafe50Pour.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/Carafe50Right.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/Carafe50RightPour.png")),
    C75("C75", new Image("file:resources/CoffeeMakerImages/Carafe/Front/Carafe75.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Front/Carafe75Pour.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/Carafe75Right.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/Carafe75RightPour.png")),
    C100("CI1", new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeFull.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Front/CarafeFullPour.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/CarafeFullRight.png"),
                   new Image("file:resources/CoffeeMakerImages/Carafe/Right/CarafeFullRightPour.png"));

    private final String level;
    private final Image frontCarafe;
    private final Image frontPouring;
    private final Image rightCarafe;
    private final Image rightPouring;

    CarafeState(String s, Image frontImage, Image frontPour, Image rightImage, Image rightPour) {
        this.level = s;
        this.frontCarafe = frontImage;
        this.frontPouring = frontPour;
        this.rightCarafe = rightImage;
        this.rightPouring = rightPour;

    }

    public Image getFrontCarafe() {
        return this.frontCarafe;
    }

    public Image getFrontPouring() {
        return this.frontPouring;
    }

    public Image getRightCarafe() {
        return this.rightCarafe;
    }

    public Image getRightPouring() {
        return this.rightPouring;
    }

    public String getLevel() {
        return this.level;
    }
}

