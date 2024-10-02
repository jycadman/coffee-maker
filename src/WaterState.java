import javafx.scene.image.Image;

public enum WaterState {
    WEMPTY( new Image("file:resources/CoffeeMakerImages/POV/Right/RightWaterEmpty.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Back/BackWaterEmpty.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterEmptyLidOpened.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterEmptyLidClosed.png")),
    W25(    new Image("file:resources/CoffeeMakerImages/POV/Right/RightWater25.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Back/BackWater25.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWater25LidOpened.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWater25LidClosed.png")),
    W50(    new Image("file:resources/CoffeeMakerImages/POV/Right/RightWater50.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Back/BackWater50.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWater50LidOpened.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWater50LidClosed.png")),
    W75(    new Image("file:resources/CoffeeMakerImages/POV/Right/RightWater75.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Back/BackWater75.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWater75LidOpened.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWater75LidClosed.png")),
    W100(   new Image("file:resources/CoffeeMakerImages/POV/Right/RightWaterFull.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Back/BackWaterFull.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterFullLidOpened.png"),
            new Image("file:resources/CoffeeMakerImages/POV/Top/TopWaterFullLidClosed.png"));

    final Image right;
    final Image back;
    final Image topOpen;
    final Image topClosed;

    WaterState(Image right, Image back, Image topO, Image topC) {
        this.right = right;
        this.back = back;
        this.topOpen = topO;
        this.topClosed = topC;
    }

    public Image getRight() {
        return right;
    }

    public Image getBack() {
        return back;
    }

    public Image getTopOpen() {
        return topOpen;
    }

    public Image getTopClosed() {
        return topClosed;
    }
}
