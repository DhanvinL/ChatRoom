import java.awt.image.AreaAveragingScaleFilter;
import java.io.Serializable;
import java.util.ArrayList;

public class CommandFromServer implements Serializable
{
    // The command being sent to the client
    private int command;
    // Text data for the command
    private String data ="";
    private ArrayList<String> names;
    // Command list
    public static final int CONNECTED = 0;
    public static final int DISCONNECTED = 8;

    public static final int SHARE_MESSAGE = 2;
    public CommandFromServer(int command, String data, ArrayList<String> names) {
        this.command = command;
        this.data = data;
        this.names = names;
    }

    public int getCommand() {
        return command;
    }
    public ArrayList<String> getNames()
    {
        return names;
    }

    public String getData() {
        return data;
    }
}

