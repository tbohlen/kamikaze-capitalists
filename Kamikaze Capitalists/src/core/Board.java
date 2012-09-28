package core;

public class Board {

    public final Player player1, player2, rubblePlayer;

    public final int width, height;

    /**
     * the first index is x-axis, the second is y-axis, e.g. buildings[x][y]
     */
    public final Building[][] buildings;

    public enum BoardState {
        STARTING, RUNNING, PAUSED, DONE;
    }

    public BoardState state;

    public Board(int width, int height) {
        player1 = new Player("Player 1"); // could be modifiable by the players
        player2 = new Player("Player 2");
        rubblePlayer = new Player("Rubble");

        this.width = width;
        this.height = height;

        buildings = new Building[width][height];

        buildings[0][0] = new Building(player1, true); // placeholder capital locations
        player1.hasCapital = true;

        buildings[width - 1][height - 1] = new Building(player2, true);
        player2.hasCapital = true;

        player1.xCursor = 0;
        player1.yCursor = 0;

        player2.xCursor = width - 1;
        player2.yCursor = height - 1;

        state = BoardState.STARTING;
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
        if (b == null || !b.owner.equals(player) || b.isRubble) {
            if (!isInitial) {
                return false;
            }
        } else {
            if (b.isCapital) {
                return true;
            }
        }
        return isConnectedToCapital(x - 1, y, player, searched, false)
                || isConnectedToCapital(x + 1, y, player, searched, false)
                || isConnectedToCapital(x, y - 1, player, searched, false)
                || isConnectedToCapital(x, y + 1, player, searched, false);
    }

    /**
     * Check whether the given location is connected to a player's capital through only buildings owned by that player
     * (the location itself does not need to contain a building owned by that player in order to be connected)
     * 
     * @param x
     *            x-value of location to check; must be 0 <= x < this.width
     * @param y
     *            y-value of location to check; must be 0 <= x < this.height
     * @param player
     *            the player whose capital and buildings are to be checked against
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

    public enum Direction {
        UP(0, -1), LEFT(-1, 0), DOWN(0, 1), RIGHT(1, 0), NONE(0, 0);

        public final int dx, dy;

        private Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public void knockOverBuilding(int x, int y, Direction dir) {
        // TODO: figure out exact knockover logic
        for (int h = buildings[x][y].height; h > 0; h--) {
            Building b = buildings[x][y];
            if (b != null) {
                if (b.isCapital) {
                    b.owner.hasCapital = false;
                }
            }
            if (buildings[x][y] != null) {
                buildings[x][y].makeRubble();
            }
            x += dir.dx;
            y += dir.dy;
            if (x < 0 || x >= width || y < 0 || y >= height) {
                break;
            }
        }

        // int h = buildings[x][y].height * 2;
        // while (h > 0) {
        // Building b = buildings[x][y];
        // if (b != null) {
        // h -= b.height;
        // if (b.isCapital) {
        // b.owner.hasCapital = false;
        // }
        // }
        // h--;
        // buildings[x][y] = null;
        // x += dir.dx;
        // y += dir.dy;
        // if (x < 0 || x >= width || y < 0 || y >= height) {
        // break;
        // }
        // }
    }
}
