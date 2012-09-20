package run;

import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class LWJGLTest {
    
    @Test
    public void runTest() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(800, 600));
        Display.create();

        // init OpenGL here

        while (!Display.isCloseRequested()) {

            // render OpenGL here

            Display.update();
        }

        Display.destroy();
    }
}
