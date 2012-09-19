package core;

public class Game {

    public final Player player1, player2;

    public final int width, height;

    /**
     * the first index is x-axis, the second is y-axis, e.g. buildings[x][y]
     */
    public final Building[][] buildings;

    public enum GameState {
        STARTING, RUNNING, PAUSED, DONE;
    }

    public GameState state;

    public Game(int width, int height) {
        player1 = new Player("Player 1"); // could be modifiable by the players
        player2 = new Player("Player 2");

        this.width = width;
        this.height = height;

        buildings = new Building[width][height];

        buildings[0][0] = new Building(player1, true); // placeholder capital
                                                       // locations
        buildings[width - 1][height - 1] = new Building(player2, true); //

        state = GameState.STARTING;
    }

    private boolean connectedToCapital(int x, int y, Player player, boolean[][] searched) {
        if (searched[x][y]) {
            return false;
        }
        searched[x][y] = true;

        Building b = buildings[x][y];
        if (b == null || !b.owner.equals(player)) {
            return false;
        } else if (b.isCapital) {
            return true;
        } else {
            return connectedToCapital(x - 1, y, player, searched) || connectedToCapital(x + 1, y, player, searched)
                    || connectedToCapital(x, y - 1, player, searched) || connectedToCapital(x, y + 1, player, searched);
        }
    }

    public boolean connectedToCapital(int x, int y, Player player) {
        boolean[][] searched = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                searched[i][j] = false;
            }
        }
        return connectedToCapital(x, y, player, searched);
    }
}