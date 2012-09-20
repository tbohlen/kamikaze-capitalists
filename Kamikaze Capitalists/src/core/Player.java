package core;

public class Player {

    public final String name;

    public int actionCount;

    public int xCursor, yCursor;

    public Player(String name) {
        this.name = name;
        actionCount = 0;

        xCursor = 0;
        yCursor = 0;
    }
}
