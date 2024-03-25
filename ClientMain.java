import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        try {
            // create an object for the TTT game
            GameData gameData = new GameData();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Name: ");
            String name = sc.next();
            //System.out.println("Enter the IP: ");
            //String ip = sc.next();
            // create a connection to server
            gameData.elapse();
            /*
            File f = new
                    File("C:\\Users\\K1328854\\OneDrive - Katy Independent School District\\chatroomstuff\\data.csv");
            ArrayList<String> tempnames = new ArrayList<>();
            Scanner fromFile = new Scanner(f);
            boolean check = false;
            while (fromFile.hasNextLine()) {
                String[] parts = fromFile.nextLine().split(",");
                for (int y = 0; y < parts.length; y++) {
                    if(!parts[y].isEmpty())
                    {
                        tempnames.add(parts[y]);
                        if (parts[y].equals(name)) {
                            check = true;
                            break;
                        }
                        gameData.getNames().add( parts[y]);
                    }

                }
            }



            while(check)
            {
                boolean go = false;
                System.out.println("The name is already there. Choose another one");
                name = sc.next();
                for(int u = 0;u<tempnames.size();u++)
                {
                    if(tempnames.get(u) == name)
                    {
                        go = true;
                        break;
                    }
                }
                if(!go)
                {
                    check = false;
                }
            }
            gameData.add(name);
            tempnames.add(name)
            ;*/
            /*FileWriter fw = new
                    FileWriter("C:\\Users\\K1328854\\OneDrive - Katy Independent School District\\chatroomstuff\\data.csv");
            PrintWriter pw = new PrintWriter(fw);
            if (name.isEmpty()) {
                fw.write("");
            } else {
                for (int x = 0; x < tempnames.size(); x++) {
                    pw.println(tempnames.get(x) + "," );
                }
                pw.close();
            }
            for (int x = 0; x < gameData.getNames().size(); x++) {
                System.out.println(gameData.getNames().get(x));
            }

             */
            Socket socket = new Socket("127.0.0.1", 8001);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            ChatRoom_Frame frame;
            frame = new ChatRoom_Frame(gameData, os, name);
            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is, os, frame);
            Thread t = new Thread(cl);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
