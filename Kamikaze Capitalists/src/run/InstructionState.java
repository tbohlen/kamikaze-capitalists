package run;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

        final float buttonX = 100;// (container.getWidth() - 200) / 2;
        final float buttonY = 100;// (container.getHeight() - 100) * (1 + .5f);
        final int buttonWidth = 200;
        final int buttonHeight = 100;

        Rectangle buttonContainer = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        Image buttonImage = new Image("lwjgl/res/ball.png");
        Sound buttonSound = new Sound("lwjgl/res/ding.wav");

        buttons[0] = new SimpleButton(buttonContainer, buttonImage, buttonImage, buttonSound);

        stickyListener.add(buttons[0]);

        buttons[0].addListener(new ClickListener() {

            @Override
            public void onClick(Button clicked, float mx, float my) {
                System.out.println("Instructions -> MainMenu");
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
        g.drawString("Instructions go here! \n" + "InstructionState.java.render()", 300, 300);
        buttons[0].render(container, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        buttons[0].update(container, delta);
    }

    @Override
    public int getID() {
        return 2;
    }

}
