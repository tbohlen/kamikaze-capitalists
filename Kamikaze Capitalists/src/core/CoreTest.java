package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoreTest {

    @Test
    public void playerTest() {
        Player p = new Player("Player 1");

        assertEquals(p.name, "Player 1");
        assertEquals(p.actionCount, 0);
    }

    @Test
    public void buildingTest() {
        Player p = new Player("Player 1");
        Building b = new Building(p);

        assertEquals(b.owner, p);
        assertEquals(b.isCapital, false);
        assertEquals(b.height, 1);
    }

    @Test
    public void buildingCapitalTest() {
        Player p = new Player("Player 1");
        Building b = new Building(p, true);

        assertEquals(b.owner, p);
        assertEquals(b.isCapital, true);
        assertEquals(b.height, 1);
    }

    @Test
    public void gameTest() {
        Game g = new Game(4, 5);

        assertEquals(g.width, 4);
        assertEquals(g.height, 5);
        for (int i = 0; i < g.width; i++) {
            for (int j = 0; j < g.height; j++) {
                if (i == 0 && j == 0) {
                    assertNotNull(g.buildings[i][j]);
                    assertEquals(g.buildings[i][j].owner, g.player1);
                    assertEquals(g.buildings[i][j].height, 1);
                    assertTrue(g.buildings[i][j].isCapital);
                } else if (i == g.width - 1 && j == g.height - 1) {
                    assertNotNull(g.buildings[i][j]);
                    assertEquals(g.buildings[i][j].owner, g.player2);
                    assertEquals(g.buildings[i][j].height, 1);
                    assertTrue(g.buildings[i][j].isCapital);
                } else {
                    assertNull(g.buildings[i][j]);
                }
            }
        }
        assertEquals(g.state, Game.GameState.STARTING);
    }

    @Test
    public void connectedToCapitalTest() {
        Game g = new Game(5, 5);
        g.buildings[0][1] = new Building(g.player1);
        g.buildings[0][2] = new Building(g.player1);

        g.buildings[1][0] = new Building(g.player2);
        g.buildings[2][0] = new Building(g.player1);

        g.buildings[3][3] = new Building(g.player1);

        assertTrue(g.isConnectedToCapital(0, 0, g.player1));

        assertTrue(g.isConnectedToCapital(0, 1, g.player1));
        assertTrue(g.isConnectedToCapital(0, 2, g.player1));
        assertTrue(g.isConnectedToCapital(0, 3, g.player1));

        assertTrue(g.isConnectedToCapital(1, 0, g.player1));
        assertFalse(g.isConnectedToCapital(2, 0, g.player1));

        assertFalse(g.isConnectedToCapital(3, 3, g.player1));

        assertFalse(g.isConnectedToCapital(4, 4, g.player1));
    }
}
