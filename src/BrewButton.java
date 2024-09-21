// Temp Sensor, Reservoir Sensor, Carafe Sensor, LED Bank
public class BrewButton {
    private TemperatureSensor temperatureSensor;
    private ReservoirSensor reservoirSensor;
    private CarafeSensor carafeSensor;
    private Timer timer;

    public BrewButton(){
        TemperatureSensor temperatureSensor = new TemperatureSensor();
        ReservoirSensor reservoirSensor = new ReservoirSensor();
        CarafeSensor carafeSensor = new CarafeSensor();
        Timer timer = new Timer();
    }




}
