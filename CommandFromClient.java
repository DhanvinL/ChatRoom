import java.io.Serializable;

public class CommandFromClient implements Serializable
{
    // The command being sent to the server
    private int command;
    // Text data for the command
    private String data = "";

    // Command list
    public static final int MOVE    =0;
    public static final int RESTART =1;

    public CommandFromClient(int i, String data) {
        this.command = i;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }
}
