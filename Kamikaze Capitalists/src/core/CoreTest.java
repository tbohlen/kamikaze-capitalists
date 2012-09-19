package core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CoreTest {

    @Test
    public void PlayerTest(){
        Player p = new Player("Player 1");
        
        assertEquals(p.name, "Player 1");
        assertEquals(p.actionCount, 0);
    }
}
