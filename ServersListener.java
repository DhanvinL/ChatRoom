import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class  ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    // Stores the which player this listener is for
    private char player;

    // static data that is shared between both listeners
    private static char turn = 'X';
    private static GameData gameData = new GameData();
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();


    public ServersListener(ObjectInputStream is, ObjectOutputStream os, char player) {
        this.is = is;
        this.os = os;
        this.player = player;
        outs.add(os);
    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                CommandFromClient cfc = (CommandFromClient) is.readObject();

                // handle the received command
                if(cfc.getCommand() == CommandFromClient.RESTART)
                {
                    gameData.reset();
                    sendCommand(new CommandFromServer(CommandFromServer.X_TURN, "secret"));
                }
                if(cfc.getCommand()==CommandFromClient.MOVE &&
                    turn==player && !gameData.isWinner('X')
                        && !gameData.isWinner('O')
                        && !gameData.isCat()) {




                        boolean go = false;
                        // pulls data for the move from the data field
                        String data = cfc.getData();
                        int c = data.charAt(1) - '0';
                        int r = data.charAt(0) - '0';
                        System.out.println(r + " " + c + "r and c");
                        for (int x = gameData.getGrid().length - 1; x >= 0; x--) {
                            System.out.println("The x is:" + x);
                            if (gameData.getGrid()[x][c] == ' ') {
                                r = x;
                                go = true;
                                break;
                            }
                        }
                        if (go) {
                            //System.out.println(r);
                            data = Integer.toString(r) + c + player;
                            System.out.println(data);

                            // if the move is invalid it, do not process it
                            //if(r!=-11)
                            //   continue;


                            // changes the server side game board
                            gameData.getGrid()[r][c] = player;

                            // sends the move out to both players
                            sendCommand(new CommandFromServer(CommandFromServer.MOVE, data));

                            // changes the turn and checks to see if the game is over
                            changeTurn();
                            checkGameOver();
                        }


                    //emd
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void changeTurn()
    {
        // changes the turn
        if(turn=='X')
            turn = 'O';
        else
            turn ='X';

        // informs both client of the new player turn
        if (turn == 'X')
            sendCommand(new CommandFromServer(CommandFromServer.X_TURN, null));
        else
            sendCommand(new CommandFromServer(CommandFromServer.O_TURN, null));
    }

    public void checkGameOver()
    {
        int command = -1;
        if(gameData.isCat())
            command = CommandFromServer.TIE;
        else if(gameData.isWinner('X'))
            command = CommandFromServer.X_WINS;
        else if(gameData.isWinner('O'))
            command = CommandFromServer.O_WINS;

        // if the game ended, informs both clients of the game's end state
        if(command!=-1)
            sendCommand(new CommandFromServer(command, null));
    }
    public void sendCommand(CommandFromServer cfs)
    {
        // Sends command to both players
        for (ObjectOutputStream o : outs) {
            try {
                o.writeObject(cfs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
