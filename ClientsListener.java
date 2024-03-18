import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class ClientsListener implements Runnable
{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private CC4_Frame frame = null;

    public ClientsListener(ObjectInputStream is,
                           ObjectOutputStream os,
                           CC4_Frame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;

    }

    @Override
    public void run() {
        try
        {
            while(true) {
                CommandFromServer cfs = (CommandFromServer) is.readObject();
                if (Objects.equals(cfs.getData(), "secret")) {
                    frame.setTurn('X');

                } else {


                    // processes the received command
                    if (cfs.getCommand() == CommandFromServer.X_TURN)
                        frame.setTurn('X');
                    else if (cfs.getCommand() == CommandFromServer.O_TURN)
                        frame.setTurn('O');
                    else if (cfs.getCommand() == cfs.MOVE) {
                        String data = cfs.getData();
                        // pulls data for the move from the data field
                        int c = data.charAt(1) - '0';
                        int r = data.charAt(0) - '0';
                        // changes the board and redraw the screen
                        frame.makeMove(r, c, data.charAt(2));
                    }
                    // handles the various end game states
                    else if (cfs.getCommand() == CommandFromServer.TIE) {
                        frame.setText("Tie game.");
                    } else if (cfs.getCommand() == CommandFromServer.X_WINS) {
                        frame.setText("Red wins!");
                    } else if (cfs.getCommand() == CommandFromServer.O_WINS) {
                        frame.setText("Black wins!");
                    }
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}