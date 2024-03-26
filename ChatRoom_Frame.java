import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ChatRoom_Frame extends JFrame implements MouseListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private String player;
    // stores all the game data
    private GameData gameData = null;
    private JList<String> user = new JList();

    private ArrayList<String> arr= new ArrayList<String>();
    JScrollPane users;
    JTextArea c = new JTextArea();


    // output stream to the server
    ObjectOutputStream os;

    public ChatRoom_Frame(GameData gameData, ObjectOutputStream os, String player) throws IOException {
        super("Chat Room");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        os.writeObject(new CommandFromClient(0, player));


        // adds a KeyListener to the Frame

        // makes closing the frame close the program

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message


        setSize(750,750);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);

        //send button
        JButton send = new JButton("Send");
        send.setBounds(550,525,150,35);
        add(send);
        //exit button
        JButton exit = new JButton("Exit");
        exit.setBounds(550,570,150,35);
        add(exit);
        //type in
        JTextField ti = new JTextField();
        ti.setBounds(50,525,480,95);
        add(ti);
        /// chat
        /*
        JLabel ct = new JLabel();
        ct.setBounds(30,100,20,20);
        add(ct);
         */

        JScrollPane chat = new JScrollPane(c, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chat.setBounds(50,100,480,400);
        c.setBounds(50,100,480,400);
        add(chat);
        //user list
        //System.out.println(gameData.getNames().size() + "123" + gameData.getNames().get(0));

        String[] names = new String[gameData.getNames().size()];
        for(int x = 0;x<gameData.getNames().size();x++)
        {
            names[x] = gameData.getNames().get(x);
            c.append(names[x]);
            System.out.println(gameData.getNames().get(x));
        }

        user.setListData(names);
        user.setBounds(550,100,150,400);
        //System.out.println(names[0]);
        users = new JScrollPane(user, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        users.getViewport().setView(user);

        users.setBounds(550,100,150,400);
        add(users);
        send.addActionListener(e ->{

                //gameData.getTexts().add(ti.getText());
                //c.append(player + ": " + ti.getText() + "\n");
                //ti.setText("");
                try{
                    os.writeObject(new CommandFromClient(10, ti.getText()));
                }
                catch(Exception f){
                    f.printStackTrace();

            }

        });
        exit.addActionListener(e ->{
            System.exit(0);
        });
        c.append(player + " has connected." + "\n");

    }
    public void appendTexts(ArrayList<String> texts)
    {
        //System.out.println("IT COMES HERE AND THE SIZE IS" + texts.size());
        //code for(everything in texts) -> append to text area
        for(int x = 0;x<texts.size();x++)
        {
            c.append(player + ": " + texts.get(x) + "\n");
        }
    }

    /*public void paint(Graphics g)
    {

        //g.setColor(Color.lightGray);
        //g.fillRect(0,0,getWidth(),getHeight());
        /*
        for (int row = 0; row < gameData.getGrid().length; row++) {
            System.out.print("Row " + (row + 1) + ": ");
            for (int col = 0; col < gameData.getGrid()[0].length; col++) {
                System.out.print(gameData.getGrid()[row][col] + " ");
            }
            System.out.println();
        }
        // draws the background
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,getWidth(),getHeight());

        // draws the display text to the screen

            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman",Font.BOLD,30));
            g.drawString(text,20    ,55);
            gameData.setjustOne();

            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString(text, 20, 55);
            gameData.setjustOne();


            // draws the c4 grid lines to the screen
            int inc = 0;
            for (int i = 1; i <= 7; i++) {
                inc += 70;
                g.setColor(Color.WHITE);
                g.fillOval(inc, 60, 60, 60);
            }
            inc = 0;
            for (int i = 1; i <= 7; i++) {
                inc += 70;
                g.setColor(Color.WHITE);
                g.fillOval(inc, 130, 60, 60);
            }
            inc = 0;
            for (int i = 1; i <= 7; i++) {
                inc += 70;
                g.setColor(Color.WHITE);
                g.fillOval(inc, 200, 60, 60);
            }
            inc = 0;
            for (int i = 1; i <= 7; i++) {
                inc += 70;
                g.setColor(Color.WHITE);
                g.fillOval(inc, 270, 60, 60);
            }
            inc = 0;
            for (int i = 1; i <= 7; i++) {
                inc += 70;
                g.setColor(Color.WHITE);
                g.fillOval(inc, 340, 60, 60);
            }
            inc = 0;
            for (int i = 1; i <= 7; i++) {
                inc += 70;
                g.setColor(Color.WHITE);
                g.fillOval(inc, 410, 60, 60);
            }

            for (int u = 0; u < gameData.getGrid().length; u++) {
                for (int v = 0; v < gameData.getGrid()[0].length; v++) {
                    if (gameData.getGrid()[u][v] != ' ') {

                        if (gameData.getGrid()[u][v] == 'X') {
                            g.setColor(Color.RED);
                            g.fillOval((v * 70) + 70, (u * 70) + 60, 60, 60);
                        }
                        if (gameData.getGrid()[u][v] == 'O') {
                            g.setColor(Color.BLACK);
                            g.fillOval((v * 70) + 70, (u * 70) + 60, 60, 60);
                        }
                    }
                }

        }
        /*g.setColor(Color.RED);
        for(int y =0;y<=1; y++)
            g.drawLine(0,(y+1)*133+60,getWidth(),(y+1)*133+60);
        for(int x =0;x<=1; x++)
            g.drawLine((x+1)*133,60,(x+1)*133,getHeight());*/

        // draws the player moves to the screen
        /*g.setFont(new Font("Times New Roman",Font.BOLD,70));
        for(int r=0; r<gameData.getGrid().length; r++)
            for(int c=0; c<gameData.getGrid().length; c++)
                g.drawString(""+gameData.getGrid()[r][c],c*133+42,r*133+150);


    }
    */

    public void addName(ArrayList<String> names1) throws IOException {
            gameData.setGrid(names1);
            System.out.println("It is repainting");
            System.out.println(names1.size());
            String[] names12 = new String[gameData.getNames().size()];
            for (int x = 0; x < gameData.getNames().size(); x++) {
                names12[x] = gameData.getNames().get(x);
            }
            user.setListData(names12);
            users.getViewport().setView(user);

    }

    public void setJustText(String text)
    {
        /*
        this.text = text;
        gameData.setjustOne();
        repaint();


         */
    }


    public void setTurn(char turn) {
        /*
        String s = "";
        if(turn==player)
            text = "Your turn";
        else
        {
            if(turn == 'X')
            {
                s = "Red";
            }
            else{
                s = "Black";
            }
            text = s+"'s turn.";
        }
        repaint();

         */
    }

    public void makeMove(int r, int c, char letter)
    {
        /*
        if(r != -1 && c != -1)
        {
            gameData.getGrid()[r][c] = letter;
            repaint();
        }

         */

    }

  /*  @Override
    public void keyTyped(KeyEvent event) {
        char key = event.getKeyChar();
        int r;
        int c;

        // sets the row and column, based on the entered key
        switch(key)
        {
            case '1':
                r=0;
                c=0;
                break;
            case '2':
                r=0;
                c=1;
                break;
            case '3':
                r=0;
                c=2;
                break;
            case '4':
                r=1;
                c=0;
                break;
            case '5':
                r=1;
                c=1;
                break;
            case '6':
                r=1;
                c=2;
                break;
            case '7':
                r=2;
                c=0;
                break;
            case '8':
                r=2;
                c=1;
                break;
            case '9':
                r=2;
                c=2;
                break;
            default:
                r=c=-1;
        }
        // if a valid enter was entered, send the move to the server
        if(c!=-1) {
            try {
                os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + c + r + player));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


   */


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {


       /* if(e.getButton() == MouseEvent.BUTTON3)
        {
            for(int x= 0;x<gameData.getGrid().length;x++)
            {
                for(int y =0;y<gameData.getGrid()[0].length;y++)
                {
                    gameData.getGrid()[x][y] = ' ';
                }
            }

            if(player == 'X')
            {
                text = "Waiting for Black to agree for a New Game";
                gameData.setCounter();

            }
            if(player == 'O'){
                text = "Waiting for Red to agree for a New Game";
                gameData.setCounter();



            }
            if(gameData.getCounter() == 2)
            {
                gameData.resetCounter();
                try {
                    gameData.reset();
                    for (int row = 0; row < gameData.getGrid().length; row++) {
                        System.out.print("Row " + (row + 1) + ": ");
                        for (int col = 0; col < gameData.getGrid()[0].length; col++) {
                            System.out.print(gameData.getGrid()[row][col] + " ");
                        }
                        System.out.println();
                    }
                    os.writeObject(new CommandFromClient(CommandFromClient.RESTART,  "secret"));
                    setTurn('X');
                } catch (Exception f) {
                    f.printStackTrace();
                }
                repaint();
            }


        }

        else {


            int c = 0;
            System.out.println(getMousePosition().getX());
            if (getMousePosition().getX() >= 140 && getMousePosition().getX() <= 200) {
                c = 1;
            }
            if (getMousePosition().getX() >= 210 && getMousePosition().getX() <= 270) {
                c = 2;
            }
            if (getMousePosition().getX() >= 280 && getMousePosition().getX() <= 340) {
                c = 3;
            }
            if (getMousePosition().getX() >= 350 && getMousePosition().getX() <= 410) {
                c = 4;
            }
            if (getMousePosition().getX() >= 420 && getMousePosition().getX() <= 480) {
                c = 5;
            }
            if (getMousePosition().getX() >= 490 && getMousePosition().getX() <= 550) {
                c = 6;
            }
            int r = 0;
            System.out.println(c);

            // if a valid enter was entered, send the move to the server
            if (c != -11) {
                try {
                    os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + r + c + player));
                } catch (Exception f) {
                    f.printStackTrace();
                }

            }
        }

        */
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}