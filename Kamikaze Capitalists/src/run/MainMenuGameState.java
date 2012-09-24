package run;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.aem.sticky.StickyListener;
import com.aem.sticky.button.Button;
import com.aem.sticky.button.ButtonSkeleton;
import com.aem.sticky.button.events.ClickListener;

public class MainMenuGameState extends BasicGameState {

    private StickyListener stickyListener = new StickyListener();

    private ButtonSkeleton[] buttons = new ButtonSkeleton[4];

    @Override
    public void init(GameContainer container, final StateBasedGame game) throws SlickException {
        for (int i = 0; i < buttons.length; i++) {
            // change to SimpleButton once we have images
            buttons[i] = new ButtonSkeleton();
            buttons[i].setShape(new Rectangle((container.getWidth() - 200) / 2, (container.getHeight() - 100)
                    * (i + .5f) / buttons.length, 200, 100));
            stickyListener.add(buttons[i]);
        }
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

        container.getInput().addListener(stickyListener);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].render(container, g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].update(container, delta);
        }
    }

    @Override
    public int getID() {
        return 0;
    }

}
