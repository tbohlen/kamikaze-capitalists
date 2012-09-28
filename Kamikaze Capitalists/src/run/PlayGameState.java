package run;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Date;

import core.Board;
import core.Board.Direction;
import core.Building;
import core.Player;

public class PlayGameState extends BasicGameState {

    private Board board;

    private Image player1BuildingImage, player1CapitalImage, player2BuildingImage, player2CapitalImage,
            disabledOverlayImage, player1RubbleImage, player2RubbleImage;

    private Sound buildSound, collapseSound;

    public static int RUBBLE_TIME = 4000; // rubble hangs around for a second

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 800, 600);
        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                Building b = board.buildings[i][j];
                g.setColor(Color.gray);
                g.fillRect(100 + (i + 0.05f) * 100, (j + 0.05f) * 100, 90, 90);
                if (b == null) {
                } else if (b.isRubble == false){
                    Image im;
                    if (b.owner == board.player1) {
                        if (b.isCapital) {
                            im = player1CapitalImage;
                        } else {
                            im = player1BuildingImage;
                        }
                    } else {
                        if (b.isCapital) {
                            im = player2CapitalImage;
                        } else {
                            im = player2BuildingImage;
                        }
                    }
                    im.draw(100 + (i + 0.1f) * 100, (j + 0.1f) * 100);
                    if (!board.isConnectedToCapital(i, j, b.owner)) {
                        disabledOverlayImage.draw(100 + (i + 0.1f) * 100, (j + 0.1f) * 100);
                    }

                    g.setColor(Color.black);
                    g.drawString(Integer.toString(b.height), 100 + (i + .5f) * 100 - 5, (j + .5f) * 100 - 10);
                }
                else {
                    Date currentDate = new Date();
                    if (currentDate.getTime() > b.rubbleStart + RUBBLE_TIME) {
                        board.buildings[i][j] = null;
                        System.out.println("destroying rubble based on age");
                    }
                    else {
                        Image im;
                        if (b.owner == board.player1) {
                            im = player1RubbleImage;
                        }
                        else {
                            im = player2RubbleImage;
                        }
                        im.draw(100 + (i + 0.1f) * 100, (j + 0.1f) * 100);
                    }
                }
            }
        }
        renderPlayer(container, game, g, board.player1, Color.red, 50);
        renderPlayer(container, game, g, board.player2, Color.blue, container.getWidth() - 50);
        switch (board.state) {
        case PAUSED:
            g.setColor(Color.white);
            String msg = "PAUSED";
            g.drawString(msg, (container.getWidth() - g.getFont().getWidth(msg)) / 2, container.getHeight() / 2);
            break;
        case DONE:
            g.setColor(Color.white);
            Player winner = board.player1.hasCapital ? board.player1 : board.player2;
            msg = winner.name + " WINS!";
            g.drawString(msg, (container.getWidth() - g.getFont().getWidth(msg)) / 2, container.getHeight() / 2);
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
        g.setColor(Color.black);
        g.drawRect(xActionBar - 25, 5, 50,
                (float) player.actionCount / Player.MAX_ACTION_COUNT * (container.getHeight() - 10));
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        player1BuildingImage = new Image("resources/player1.png");
        player1CapitalImage = new Image("resources/player1capital.png");
        player2BuildingImage = new Image("resources/player2.png");
        player2CapitalImage = new Image("resources/player2capital.png");
        disabledOverlayImage = new Image("resources/disabled.png");
        player1RubbleImage = new Image("resources/player1rubble.png");
        player2RubbleImage = new Image("resources/player2rubble.png");

        buildSound = new Sound("resources/boom.wav");
        collapseSound = new Sound("resources/crash.wav");
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
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE) || container.getInput().isKeyPressed(Input.KEY_BACK)) {
            game.enterState(0);
        }
        switch (board.state) {
        case RUNNING:
            if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                board.state = Board.BoardState.PAUSED;
                update(container, game, delta);
            }
            updatePlayer(container, game, delta, board.player1, Input.KEY_W, Input.KEY_A, Input.KEY_S, Input.KEY_D,
                    Input.KEY_T, Input.KEY_Y);
            updatePlayer(container, game, delta, board.player2, Input.KEY_UP, Input.KEY_LEFT, Input.KEY_DOWN,
                    Input.KEY_RIGHT, Input.KEY_COMMA, Input.KEY_PERIOD);
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
            int down, int right, int build, int collapse) throws SlickException {

        player.actionCount += delta;
        player.actionCount = Math.min(player.actionCount, Player.MAX_ACTION_COUNT); // arbitrary actionCount limit

        if (container.getInput().isKeyDown(collapse)) {
            if (player.actionCount == 1000) {
                if (board.isConnectedToCapital(player.getXCursorIndex(), player.getYCursorIndex(), player)) {
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
                    if (dir != null) {
                        // collapsing
                        Building b = board.buildings[player.getXCursorIndex()][player.getYCursorIndex()];
                        if (b != null && b.owner == player && !b.isRubble) {
                            board.knockOverBuilding(player.getXCursorIndex(), player.getYCursorIndex(), dir);
                            collapseSound.play();
                            player.actionCount = 0;
                            if (!board.player1.hasCapital || !board.player2.hasCapital) {
                                board.state = Board.BoardState.DONE;
                            }
                        }
                    }
                }
            }
        } else {
            // moving cursor
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

        if (container.getInput().isKeyPressed(build)) {
            if (player.actionCount == 1000) {
                // building
                if (board.isConnectedToCapital(player.getXCursorIndex(), player.getYCursorIndex(), player)) {
                    Building b = board.buildings[player.getXCursorIndex()][player.getYCursorIndex()];
                    if (b == null) {
                        board.buildings[player.getXCursorIndex()][player.getYCursorIndex()] = new Building(player);
                        player.actionCount = 0;
                    } else if (b.owner == player && !b.isRubble) {
                        b.height++;
                        player.actionCount = 0;
                    }
                    buildSound.play();
                }
            }
        }

        // if (!container.getInput().isKeyDown(action)) {
        // if (container.getInput().isKeyPressed(action)) {
        // if (player.actionCount == 1000) {
        // Board.Direction dir = null;
        // if (container.getInput().isKeyDown(right)) {
        // dir = Direction.RIGHT;
        // } else if (container.getInput().isKeyDown(left)) {
        // dir = Direction.LEFT;
        // } else if (container.getInput().isKeyDown(down)) {
        // dir = Direction.DOWN;
        // } else if (container.getInput().isKeyDown(up)) {
        // dir = Direction.UP;
        // }
        // if (board.isConnectedToCapital(player.getXCursorIndex(), player.getYCursorIndex(), player)) {
        // if (dir == null) {
        // // building
        // Building b = board.buildings[player.getXCursorIndex()][player.getYCursorIndex()];
        // if (b == null) {
        // board.buildings[player.getXCursorIndex()][player.getYCursorIndex()] = new Building(
        // player);
        // player.actionCount = 0;
        // } else if (b.owner == player) {
        // b.height++;
        // player.actionCount = 0;
        // }
        // } else {
        // // collapsing
        // Building b = board.buildings[player.getXCursorIndex()][player.getYCursorIndex()];
        // if (b != null && b.owner == player) {
        // board.knockOverBuilding(player.getXCursorIndex(), player.getYCursorIndex(), dir);
        // player.actionCount = 0;
        // if (!board.player1.hasCapital || !board.player2.hasCapital) {
        // board.state = Board.BoardState.DONE;
        // }
        // }
        // }
        // }
        // }
        // } else {
        // // moving cursor
        // if (container.getInput().isKeyDown(right)) {
        // player.xCursor += .01 * delta;
        // } else if (container.getInput().isKeyDown(left)) {
        // player.xCursor -= .01 * delta;
        // } else {
        // player.xCursor = player.getXCursorIndex();
        // }
        // player.xCursor = bound(player.xCursor, 0, board.width);
        // if (container.getInput().isKeyDown(down)) {
        // player.yCursor += .01 * delta;
        // } else if (container.getInput().isKeyDown(up)) {
        // player.yCursor -= .01 * delta;
        // } else {
        // player.yCursor = player.getYCursorIndex();
        // }
        // player.yCursor = bound(player.yCursor, 0, board.height);
        // }
        // }
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
