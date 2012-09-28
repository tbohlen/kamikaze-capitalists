package run;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

    public Game() {
        super("Kamikaze Capitalists");
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new MainMenuGameState());
        addState(new PlayGameState());
        addState(new InstructionState());
    }

    public static void main(String[] args) throws SlickException {
        String separator = System.getProperty("file.separator");
        System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + separator + "lwjgl" + separator + "native" + separator + "all");
        AppGameContainer app = new AppGameContainer(new Game());
        app.setVerbose(false);
        app.setDisplayMode(800, 600, true);
        app.setVSync(true);
        app.setShowFPS(false);
        app.start();
    }

}
