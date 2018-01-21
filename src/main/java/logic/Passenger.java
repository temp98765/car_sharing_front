package logic;

import java.awt.Point;

public class Passenger extends Entity {
    
	private Point destination;
	
    public Passenger(int x, int y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return "Passenger {id = " + getId() + " position = (" + getPosition().x + ", " + getPosition().y + ")}";
    }

	public Point getDestination() {
		return destination;
	}

	public void setDestination(Point destination) {
		this.destination = destination;
	}
}
