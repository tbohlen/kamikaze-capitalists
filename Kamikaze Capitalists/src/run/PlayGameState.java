package run;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Board;
import core.Board.Direction;
import core.Building;
import core.Player;

public class PlayGameState extends BasicGameState {

    private Board board;

    private Image player1Building, player1Capital, player2Building, player2Capital;

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.gray);
        g.fillRect(100, 0, 600, 600);
        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                Building b = board.buildings[i][j];
                if (b == null) {
                } else {
                    Image im;
                    if (b.owner == board.player1) {
                        if (b.isCapital) {
                            im = player1Capital;
                        } else {
                            im = player1Building;
                        }
                    } else {
                        if (b.isCapital) {
                            im = player2Capital;
                        } else {
                            im = player2Building;
                        }
                    }
                    im.draw(100 + (i + 0.1f) * 100, (j + 0.1f) * 100);

                    // if (b.owner == board.player1) {
                    // g.setColor(Color.red);
                    // } else {
                    // g.setColor(Color.blue);
                    // }
                    // g.fillRect((i + .1f) * 100, (j + .1f) * 100, 80, 80);
                    // if (b.isCapital) {
                    // g.setColor(Color.orange);
                    // g.fillRect((i + .3f) * 100, (j + .3f) * 100, 40, 40);
                    // }

                    g.setColor(Color.black);
                    g.drawString(Integer.toString(b.height), 100 + (i + .5f) * 100 - 5, (j + .5f) * 100 - 10);
                }
            }
        }
        renderPlayer(container, game, g, board.player1, Color.red, 50);
        renderPlayer(container, game, g, board.player2, Color.blue, container.getWidth() - 50);
        switch (board.state) {
        case PAUSED:
            g.setColor(Color.white);
            g.drawString("PAUSED", container.getWidth() / 2, container.getHeight() / 2);
            break;
        case DONE:
            g.setColor(Color.white);
            Player winner = board.player1.hasCapital ? board.player1 : board.player2;
            g.drawString(winner.name + " WINS!", container.getWidth() / 2, container.getHeight() / 2);
            break;
        }
    }

    private void renderPlayer(GameContainer container, StateBasedGame game, Graphics g, Player player, Color color,
            float xActionBar) throws SlickException {
        g.setColor(color);
        g.setLineWidth(5);
        g.drawRect(100 + player.getXCursorIndex() * 100, player.getYCursorIndex() * 100, 100, 100);
        g.fillRect(xActionBar - 25, 5, 50,
                (float) player.actionCount / Player.MAX_ACTION_COUNT * (container.getHeight() - 10));
        // g.setColor(Color.gray);
        // g.drawRect(xActionBar - 10, 10, 10,
        // (float) player.actionCount / Player.MAX_ACTION_COUNT * (container.getHeight() - 10));
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        player1Building = new Image("resources/player1.png");
        player1Capital = new Image("resources/player1capital.png");
        player2Building = new Image("resources/player2.png");
        player2Capital = new Image("resources/player2capital.png");
    }

    private static float bound(float num, int min, int max) {
        if (Math.round(num) < min) {
            return min - 0.5f;
        }
        if (Math.round(num) >= max) {
            return max - 1 + 0.499999f;
        }
        return num;
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_LSHIFT) || container.getInput().isKeyPressed(Input.KEY_RSHIFT)) {
            game.enterState(0);
        }
        switch (board.state) {
        case RUNNING:
            if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                board.state = Board.BoardState.PAUSED;
                update(container, game, delta);
            }
            updatePlayer(container, game, delta, board.player1, Input.KEY_W, Input.KEY_A, Input.KEY_S, Input.KEY_D,
                    Input.KEY_T);
            updatePlayer(container, game, delta, board.player2, Input.KEY_UP, Input.KEY_LEFT, Input.KEY_DOWN,
                    Input.KEY_RIGHT, Input.KEY_PERIOD);
            break;
        case PAUSED:
            if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                board.state = Board.BoardState.RUNNING;
                update(container, game, delta);
            }
        case DONE:
            if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                game.enterState(1);
            }
        }
    }

    private void updatePlayer(GameContainer container, StateBasedGame game, int delta, Player player, int up, int left,
            int down, int right, int action) throws SlickException {

        player.actionCount += delta;
        player.actionCount = Math.min(player.actionCount, Player.MAX_ACTION_COUNT); // arbitrary actionCount limit

        if (!container.getInput().isKeyDown(action)) {
            if (container.getInput().isKeyPressed(action)) {
                if (player.actionCount == 1000) {
                    Board.Direction dir = null;
                    if (container.getInput().isKeyDown(right)) {
                        dir = Direction.RIGHT;
                    } else if (container.getInput().isKeyDown(left)) {
                        dir = Direction.LEFT;
                    } else if (container.getInput().isKeyDown(down)) {
                        dir = Direction.DOWN;
                    } else if (container.getInput().isKeyDown(up)) {
                        dir = Direction.UP;
                    }
                    if (board.isConnectedToCapital(player.getXCursorIndex(), player.getYCursorIndex(), player)) {
                        if (dir == null) {
                            Building b = board.buildings[player.getXCursorIndex()][player.getYCursorIndex()];
                            if (b == null) {
                                board.buildings[player.getXCursorIndex()][player.getYCursorIndex()] = new Building(
                                        player);
                                player.actionCount = 0;
                            } else if (b.owner == player) {
                                b.height++;
                                player.actionCount = 0;
                            }
                        } else {
                            Building b = board.buildings[player.getXCursorIndex()][player.getYCursorIndex()];
                            if (b != null && b.owner == player) {
                                board.knockOverBuilding(player.getXCursorIndex(), player.getYCursorIndex(), dir);
                                player.actionCount = 0;
                                if (!board.player1.hasCapital || !board.player2.hasCapital) {
                                    board.state = Board.BoardState.DONE;
                                }
                            }
                        }
                    }
                }
            } else {
                if (container.getInput().isKeyDown(right)) {
                    player.xCursor += .01 * delta;
                } else if (container.getInput().isKeyDown(left)) {
                    player.xCursor -= .01 * delta;
                } else {
                    player.xCursor = player.getXCursorIndex();
                }
                player.xCursor = bound(player.xCursor, 0, board.width);
                if (container.getInput().isKeyDown(down)) {
                    player.yCursor += .01 * delta;
                } else if (container.getInput().isKeyDown(up)) {
                    player.yCursor -= .01 * delta;
                } else {
                    player.yCursor = player.getYCursorIndex();
                }
                player.yCursor = bound(player.yCursor, 0, board.height);
            }
        }
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        board = new Board(6, 6);
        board.state = Board.BoardState.RUNNING;
    }

}
