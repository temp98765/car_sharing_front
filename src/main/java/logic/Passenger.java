package logic;

public class Passenger extends Entity {
    
    public Destination destination;
	
    public Passenger(int x, int y, Destination destination) {
        super(x, y);
        this.destination = destination;
    }
}
