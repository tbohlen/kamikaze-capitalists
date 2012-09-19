package core;

public class Building {
    
    public int height;
    
    public final Player owner;
    
    public final boolean isCapital;
    
    public Building(Player owner){
	height = 1;
	this.owner = owner;
	isCapital = false;
    }
    
    public Building(Player owner, boolean isCapital){
	height = 1;
	this.owner = owner;
	this.isCapital = isCapital;
    }
    
}
