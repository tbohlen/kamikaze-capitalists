package run;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.aem.sticky.StickyListener;
import com.aem.sticky.button.Button;
import com.aem.sticky.button.SimpleButton;
import com.aem.sticky.button.events.ClickListener;

public class InstructionState extends BasicGameState {

    private StickyListener stickyListener = new StickyListener();

    private SimpleButton[] buttons = new SimpleButton[1];

    @Override
    public void init(GameContainer container, final StateBasedGame game) throws SlickException {

        final float buttonX = 150;// (container.getWidth() - 200) / 2;
        final float buttonY = 50;// (container.getHeight() - 100) * (1 + .5f);
        final int buttonWidth = 200;
        final int buttonHeight = 100;

        Rectangle buttonContainer = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        Image buttonImage = new Image("lwjgl/res/ball.png");
        Sound buttonSound = new Sound("lwjgl/res/ding.wav");

        buttons[0] = new SimpleButton(buttonContainer, buttonImage, buttonImage, buttonSound);

        // stickyListener.add(buttons[0]);

        buttons[0].addListener(new ClickListener() {

            @Override
            public void onClick(Button clicked, float mx, float my) {
                game.enterState(0);
            }

            @Override
            public void onRightClick(Button clicked, float mx, float my) {
            }

            @Override
            public void onDoubleClick(Button clicked, float mx, float my) {
            }
        });

        container.getInput().addListener(stickyListener);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Your goal is to destroy your opponent's capital building.\n"
                + "Buildings can be destroyed by collapsing your buildings on them.\n" + "\n"
                + "Taller building will collapse across more spaces (building's height minus 1).\n"
                + "You can only build on or collapse spaces which are connected to your capital\n"
                + "by your own buildings, and only once your action bar has filled.\n" + "\n"
                + "To move: use directional keys\n" + "To build: press build key\n"
                + "To collapse: hold collapse key and press a directional key\n" + "\n" + "Player 1 (red):\n"
                + "directional keys = WASD, build key = T, collapse key = Y\n" + "Player 2 (blue):\n"
                + "directional keys = arrow keys, build key = PERIOD, collapse key = COMMA\n" + "\n"
                + "During the game, press SPACE to pause/unpause or SHIFT to quit.\n"
                + "Once the game is over, prese SPACE to start a new game or SHIFT to quit.\n", 100, 150);
        // buttons[0].render(container, g);
        g.setColor(Color.white);
        g.drawString("ESC to go back", 50, 50);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // buttons[0].update(container, delta);
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE) && container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            game.enterState(0);
        }
    }

    @Override
    public int getID() {
        return 2;
    }

}
