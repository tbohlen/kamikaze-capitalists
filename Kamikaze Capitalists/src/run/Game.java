package run;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

    public Game() {
        super("Kamikaze Capitalists");
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(30); // should be app.setVSync(true) for final version
        app.start();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainMenuGameState());
        addState(new PlayGameState());
    }

}
