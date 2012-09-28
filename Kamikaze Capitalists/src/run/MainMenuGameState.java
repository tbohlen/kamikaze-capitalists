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

public class MainMenuGameState extends BasicGameState {

    private StickyListener stickyListener = new StickyListener();

    private SimpleButton[] buttons = new SimpleButton[2];

    private Image logoImage;

    @Override
    public void init(GameContainer container, final StateBasedGame game) throws SlickException {
        for (int i = 0; i < buttons.length; i++) {

            final float buttonX = (100 + i * 348);
            final float buttonY = (container.getHeight() - 100);
            final int buttonWidth = 252;
            final int buttonHeight = 63;

            Rectangle buttonContainer = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

            Image buttonImage = null;
            if (i == 0)
                buttonImage = new Image("resources/startgame.png");
            if (i == 1)
                buttonImage = new Image("resources/instructions.png");

            Sound buttonSound = new Sound("resources/crash.wav");

            buttons[i] = new SimpleButton(buttonContainer, buttonImage, buttonImage, buttonSound);

            stickyListener.add(buttons[i]);
        }

        logoImage = new Image("resources/logo.png");

        buttons[0].addListener(new ClickListener() {

            @Override
            public void onClick(Button clicked, float mx, float my) {
                game.enterState(1);
            }

            @Override
            public void onRightClick(Button clicked, float mx, float my) {
            }

            @Override
            public void onDoubleClick(Button clicked, float mx, float my) {
            }
        });

        buttons[1].addListener(new ClickListener() {
            @Override
            public void onClick(Button clicked, float mx, float my) {
                game.enterState(2);
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
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].render(container, g);
        }
        logoImage.draw(40, -50);
        g.setColor(Color.white);
        String msg = "by Daniel Heins, Todd Layton, Wei Wei Lu, Turner Bohlen, and Jeremy Sharpe";
        g.drawString(msg, (container.getWidth() - g.getFont().getWidth(msg)) / 2, 425);
        g.drawString("ESCAPE or BACKSPACE to exit", 50, 50);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE) && container.getInput().isKeyDown(Input.KEY_ESCAPE)
                || container.getInput().isKeyPressed(Input.KEY_BACK) && container.getInput().isKeyDown(Input.KEY_BACK)) {
            container.exit();
        }
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].update(container, delta);
        }
    }

    @Override
    public int getID() {
        return 0;

    }

}