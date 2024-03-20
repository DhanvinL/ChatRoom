import java.util.ArrayList;

public class GameData
{
    private int elapsedMessages = 0;
    private ArrayList<String> texts = new ArrayList<>();
    public ArrayList<String> getGrid()
    {
        return texts;
    }
    public int getElapsedMessages()
    {
        return elapsedMessages;
    }
    public void elapse()
    {
        elapsedMessages++;
    }




}

