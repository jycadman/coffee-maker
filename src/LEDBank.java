import java.io.PrintWriter;

public class LEDBank {
    public void AllOFF(PrintWriter writer) {
        writer.println(MachineState.ALL_LEDS_OFF.getCommand());
    }
    public void StandByWithWater(PrintWriter writer){
        writer.println(MachineState.STANDBY_LEDS_WITH_WATER.getCommand());
    }
    public void StandByWithoutWater(PrintWriter writer){
        writer.println(MachineState.STANDBY_LEDS_WITHOUT_WATER.getCommand());
    }
    public void Error(PrintWriter writer){
        writer.println(MachineState.ERROR_LEDS.getCommand());
    }
    public void Heating(PrintWriter writer){
        writer.println(MachineState.HEATING_LEDS.getCommand());
    }
    public void Brewing(PrintWriter writer){
        writer.println(MachineState.BREWING_LEDS.getCommand());
    }
}
