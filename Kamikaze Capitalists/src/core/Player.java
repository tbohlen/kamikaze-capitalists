package core;

public class Player {

    public final String name;

    public boolean hasCapital;

    public int actionCount;

    public static final int MAX_ACTION_COUNT = 1000;

    public float xCursor, yCursor;

    public int getXCursorIndex() {
        return Math.round(xCursor);
    }

    public int getYCursorIndex() {
        return Math.round(yCursor);
    }

    public Player(String name) {
        this.name = name;

        hasCapital = false;

        actionCount = 0;

        xCursor = 0;
        yCursor = 0;
    }
}
