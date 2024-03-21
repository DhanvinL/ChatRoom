import java.io.Serializable;
import java.util.ArrayList;

public class CommandFromClient implements Serializable
{
    // The command being sent to the server
    private int command;
    // Text data for the command
    private ArrayList data = new ArrayList();

    // Command list
    public static final int MOVE    =0;
    public static final int RESTART =1;

    public CommandFromClient( ArrayList data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public ArrayList getData() {
        return data;
    }
}
