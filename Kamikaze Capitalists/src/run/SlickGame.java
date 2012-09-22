package run;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import core.Building;
import core.Game;
import core.Game.Direction;
import core.Player;

public class SlickGame extends BasicGame {

    private Game game;

    public SlickGame() {
        super("Kamikaze Capitalists");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.gray);
        g.fillRect(0, 0, 800, 600);
        for (int i = 0; i < game.width; i++) {
            for (int j = 0; j < game.height; j++) {
                Building b = game.buildings[i][j];
                if (b == null) {
                } else {
                    if (b.owner == game.player1) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.blue);
                    }
                    g.fillRect((i + .1f) * 100, (j + .1f) * 100, 80, 80);
                    if (b.isCapital) {
                        g.setColor(Color.orange);
                        g.fillRect((i + .3f) * 100, (j + .3f) * 100, 40, 40);
                    }
                    g.setColor(Color.black);
                    g.drawString(Integer.toString(b.height), (i + .5f) * 100 - 5, (j + .5f) * 100 - 10);
                }
            }
        }
        renderPlayer(container, g, game.player1, Color.red);
        renderPlayer(container, g, game.player2, Color.blue);
    }

    private void renderPlayer(GameContainer container, Graphics g, Player player, Color color) throws SlickException {
        g.setColor(color);
        g.setLineWidth(5);
        g.drawRect(player.xCursor * 100, player.yCursor * 100, 100, 100);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        game = new Game(8, 6);
        game.state = Game.GameState.RUNNING;
    }

    private static int bound(int num, int min, int max) {
        if (num < min) {
            return min;
        }
        if (num >= max) {
            return max - 1;
        }
        return num;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (game.state == Game.GameState.RUNNING) {
            updatePlayer(container, delta, game.player1, Input.KEY_W, Input.KEY_A, Input.KEY_S, Input.KEY_D,
                    Input.KEY_T);
            updatePlayer(container, delta, game.player2, Input.KEY_UP, Input.KEY_LEFT, Input.KEY_DOWN, Input.KEY_RIGHT,
                    Input.KEY_PERIOD);
        }
    }

    private void updatePlayer(GameContainer container, int delta, Player player, int up, int left, int down, int right,
            int action) throws SlickException {

        player.actionCount += delta;
        player.actionCount = Math.min(player.actionCount, 1000); // arbitrary actionCount limit

        if (!container.getInput().isKeyDown(action)) {
            if (container.getInput().isKeyPressed(action)) {
                if (player.actionCount == 1000) {
                    Game.Direction dir = null;
                    if (container.getInput().isKeyDown(right)) {
                        dir = Direction.RIGHT;
                    } else if (container.getInput().isKeyDown(left)) {
                        dir = Direction.LEFT;
                    } else if (container.getInput().isKeyDown(down)) {
                        dir = Direction.DOWN;
                    } else if (container.getInput().isKeyDown(up)) {
                        dir = Direction.UP;
                    }
                    if (game.isConnectedToCapital(player.xCursor, player.yCursor, player)) {
                        if (dir == null) {
                            Building b = game.buildings[player.xCursor][player.yCursor];
                            if (b == null) {
                                game.buildings[player.xCursor][player.yCursor] = new Building(player);
                                player.actionCount = 0;
                            } else if (b.owner == player) {
                                b.height++;
                                player.actionCount = 0;
                            }
                        } else {
                            Building b = game.buildings[player.xCursor][player.yCursor];
                            if (b != null && b.owner == player) {
                                game.knockOverBuilding(player.xCursor, player.yCursor, dir);
                                player.actionCount = 0;
                                if (!game.player1.hasCapital || !game.player2.hasCapital) {
                                    game.state = Game.GameState.DONE;
                                }
                            }
                        }
                    }
                }
            } else {
                if (container.getInput().isKeyDown(right)) {
                    player.xCursor++;
                } else if (container.getInput().isKeyDown(left)) {
                    player.xCursor--;
                }
                player.xCursor = bound(player.xCursor, 0, game.width);
                if (container.getInput().isKeyDown(down)) {
                    player.yCursor++;
                } else if (container.getInput().isKeyDown(up)) {
                    player.yCursor--;
                }
                player.yCursor = bound(player.yCursor, 0, game.height);
            }
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new SlickGame());
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(30); // should be app.setVSync(true) for final version
        app.start();
    }

}
