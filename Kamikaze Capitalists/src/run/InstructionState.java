package run;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InstructionState extends BasicGameState {

    // private StickyListener stickyListener = new StickyListener();
    //
    // private SimpleButton[] buttons = new SimpleButton[1];

    @Override
    public void init(GameContainer container, final StateBasedGame game) throws SlickException {

        // final float buttonX = 150;// (container.getWidth() - 200) / 2;
        // final float buttonY = 50;// (container.getHeight() - 100) * (1 + .5f);
        // final int buttonWidth = 200;
        // final int buttonHeight = 100;
        //
        // Rectangle buttonContainer = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        // Image buttonImage = new Image("lwjgl/res/ball.png");
        // Sound buttonSound = new Sound("lwjgl/res/ding.wav");
        //
        // buttons[0] = new SimpleButton(buttonContainer, buttonImage, buttonImage, buttonSound);
        //
        // // stickyListener.add(buttons[0]);
        //
        // buttons[0].addListener(new ClickListener() {
        //
        // @Override
        // public void onClick(Button clicked, float mx, float my) {
        // game.enterState(0);
        // }
        //
        // @Override
        // public void onRightClick(Button clicked, float mx, float my) {
        // }
        //
        // @Override
        // public void onDoubleClick(Button clicked, float mx, float my) {
        // }
        // });
        //
        // container.getInput().addListener(stickyListener);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Red Player: directional keys: WASD, build: T, collapse: Y\n"
                + "Blue Player: directional keys: arrow keys, build: COMMA, collapse: PERIOD\n\n"
                + "Your goal is to destroy your opponent's capital building. Buildings are destroyed\n"
                + "when another building is knocked over on top of them.\n" + "\n"
                + "Knocked-down buildings destroy a number of squares equal to their height minus 1.\n\n"
                + "Move your cursor using the directional keys, build a building or additional\n"
                + "story by pressing the build key, and knock one of your buildings over by holding\n"
                + "the collapse key and pressing a directional key to indicate where the building\n"
                + "should fall.\n\n"
                + "You may only build on or collapse buildings on spaces which are connected to your\n"
                + "capital through a chain of your own buildings.\n\n"
                + "You may take one action every time your action bar fills up. Your action bar\n"
                + "is the vertical rectangle toward the side of the screen colored your color.\n\n"
                + "Press SPACE to pause/unpause the game, ESC or BACKSPACE to quit.\n", 50, 100);
        // buttons[0].render(container, g);
        g.drawString("ESCAPE or BACKSPACE to go back", 50, 50);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // buttons[0].update(container, delta);
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE) && container.getInput().isKeyDown(Input.KEY_ESCAPE)
                || container.getInput().isKeyPressed(Input.KEY_BACK) && container.getInput().isKeyDown(Input.KEY_BACK)) {
            game.enterState(0);
        }
    }

    @Override
    public int getID() {
        return 2;
    }

}
