package core;

import java.util.Date;

public class Building {

    public int height;

    public Player owner;

    public final boolean isCapital;

    public boolean isRubble;

    public long rubbleStart;

    public boolean rubbleDisplayed;

    public Building(Player owner) {
        height = 1;
        this.owner = owner;
        isCapital = false;
        rubbleDisplayed = false;
    }

    public Building(Player owner, boolean isCapital) {
        height = 1;
        this.owner = owner;
        this.isCapital = isCapital;
    }

    public void makeRubble(Player newOwner) {
        this.isRubble = true;
        this.owner = newOwner;
        this.rubbleStart = (new Date()).getTime();
    }
}
