import java.io.PrintWriter;

public class Heater {
    public void heatUp(PrintWriter writer) {
        writer.println(MachineState.HEAT_UP.getCommand());
    }

    public void coolDown(PrintWriter writer) {
        writer.println(MachineState.COOL_DOWN.getCommand());
    }
}
