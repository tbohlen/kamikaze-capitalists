package core;

public class Player {

    public final String name;

    public boolean hasCapital;

    public int actionCount;

    public int xCursor, yCursor;

    public Player(String name) {
        this.name = name;

        hasCapital = false;

        actionCount = 0;

        xCursor = 0;
        yCursor = 0;
    }
}
