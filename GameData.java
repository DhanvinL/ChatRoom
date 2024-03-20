import java.util.ArrayList;

public class GameData
{
    private int elapsedMessages = 0;
    private ArrayList<String> texts = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    public ArrayList<String> getTexts()
    {
        return texts;
    }

    public ArrayList<String> getNames()
    {
        return texts;
    }

    public void add(String name)
    {
        names.add(name);
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

