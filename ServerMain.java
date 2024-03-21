import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain
{
    public static void main(String[] args) throws IOException {
        boolean go = false;
        try
        {
            // creates a socket that allows connections on port 8001
            ServerSocket serverSocket = new ServerSocket(8001);

            //1
            // allow X to connect and build streams to / from X
            Socket xCon = serverSocket.accept();
            ObjectOutputStream xos = new ObjectOutputStream(xCon.getOutputStream());
            ObjectInputStream xis = new ObjectInputStream(xCon.getInputStream());

            // Lets the client know they are the X player
            xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            System.out.println("Red has Connected.");

            // Creates a Thread to listen to the X client
            ServersListener sl = new ServersListener(xis,xos,'X');
            Thread t = new Thread(sl);
            t.start();


            //2
            // allow O to connect and build streams to / from O
            Socket oCon = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(oCon.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(oCon.getInputStream());

            // Lets the client know they are the X player
            oos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            System.out.println("Black has Connected.");

            // Creates a Thread to listen to the O client
            sl = new ServersListener(ois,oos,'O');
            t = new Thread(sl);
            t.start();

            //3
            // allow O to connect and build streams to / from O
            Socket zCon = serverSocket.accept();
            ObjectOutputStream zos = new ObjectOutputStream(zCon.getOutputStream());
            ObjectInputStream zis = new ObjectInputStream(zCon.getInputStream());

            // Lets the client know they are the X player
            zos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            System.out.println("Black has Connected.");

            // Creates a Thread to listen to the O client
            sl = new ServersListener(zis,zos,'O');
            t = new Thread(sl);
            t.start();

            //4
            // allow O to connect and build streams to / from O
            Socket bCon = serverSocket.accept();
            ObjectOutputStream bos = new ObjectOutputStream(bCon.getOutputStream());
            ObjectInputStream bis = new ObjectInputStream(bCon.getInputStream());

            // Lets the client know they are the X player
            bos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            System.out.println("Black has Connected.");

            // Creates a Thread to listen to the O client
            sl = new ServersListener(bis,bos,'O');
            t = new Thread(sl);
            t.start();



            xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            oos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            zos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));
            bos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED,null));



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
