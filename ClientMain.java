import javax.print.DocFlavor;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain
{
    public static void main(String[] args)
    {
        try {
            // create an object for the TTT game
            GameData gameData = new GameData();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the IP: ");
            String ip = sc.next();
            // create a connection to server
            Socket socket = new Socket(ip,8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            // determine if playing as X or O
            CommandFromServer cfs = (CommandFromServer) is.readObject();
            CC4_Frame frame;

            // Create the Frame based on which player the server says this client is
            if(cfs.getCommand() == CommandFromServer.CONNECTED_AS_X)
                frame = new CC4_Frame(gameData,os,'X');
            else
                frame = new CC4_Frame(gameData,os, 'O');

            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is,os,frame);
            Thread t = new Thread(cl);
            t.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
