package core;

public class Rubble {
    public final Player owner;

    public int startTime;
    public int lifeTime;

    public Rubble(Player owner) {
        this.owner = owner;
    }
}
