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

        buildings[0][0] = new Building(player1, true); // placeholder capital locations

        buildings[width - 1][height - 1] = new Building(player2, true); //

        state = GameState.STARTING;
    }

    private boolean isConnectedToCapital(int x, int y, Player player, boolean[][] searched, boolean isInitial) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        if (searched[x][y]) {
            return false;
        }
        searched[x][y] = true;

        Building b = buildings[x][y];
        if (!isInitial && (b == null || !b.owner.equals(player))) {
            return false;
        } else if (b.isCapital) {
            return true;
        } else {
            return isConnectedToCapital(x - 1, y, player, searched, false)
                    || isConnectedToCapital(x + 1, y, player, searched, false)
                    || isConnectedToCapital(x, y - 1, player, searched, false)
                    || isConnectedToCapital(x, y + 1, player, searched, false);
        }
    }

    /**
     * 
     * @param x
     *            x-value of location to check; must be 0 <= x < this.width
     * @param y
     *            y-value of location to check; must be 0 <= x < this.height
     * @param player
     *            player whose capital to check against
     */
    public boolean isConnectedToCapital(int x, int y, Player player) {
        boolean[][] searched = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                searched[i][j] = false;
            }
        }
        return isConnectedToCapital(x, y, player, searched, true);
    }
}
