import javax.print.DocFlavor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class  ServersListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;

    // Stores the which player this listener is for
    private String player;

    // static data that is shared between both listeners
    private static char turn = 'X';
    private static  GameData gameData = new GameData();
    private static ArrayList<ObjectOutputStream> outs = new ArrayList<>();


    public ServersListener(ObjectInputStream is, ObjectOutputStream os, String player) {
        this.is = is;
        this.os = os;
        this.player = player;
        outs.add(os);
    }

    @Override
    public void run() {
        Scanner sc = new Scanner((System.in));

        try
        {

            while(true)

            {
                os.reset();
                CommandFromClient cfc = (CommandFromClient) is.readObject();
                String s = cfc.getData();
                if(cfc.getCommand() == 0)
                {

                    while(gameData.getNames().contains(s))
                    {
                        System.out.println("Pick a different name: ");
                        s = sc.next();
                    }
                    gameData.add(s);
                    System.out.println("The number in the main list is:" + gameData.getNames().size());
                    ArrayList<String> temp = gameData.getNames();
                    System.out.println("The number in the test list is:" + temp.size() + "--The number of clients is : " + outs.size());

                    sendCommand(new CommandFromServer(12, s, temp));
                }
                if(cfc.getCommand() == 10)
                {


                    System.out.println("YIPPEEE");

                    gameData.getTexts().add(cfc.getData());
                    sendCommand(new CommandFromServer(18, s, gameData.getTexts()));

                }


            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*public void changeTurn()
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
*/

    public void sendCommand(CommandFromServer cfs) throws IOException {
        ArrayList<String> temp = cfs.getNames();
        CommandFromServer cur = new CommandFromServer(12, cfs.getData(), temp);
        // Sends command to both players
        for (ObjectOutputStream o : outs) {
            try {
                o.writeObject(cur);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
