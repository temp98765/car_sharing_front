package logic;


public class Destination extends Entity {
    
    public Passenger origin;
    
    public Destination(int x, int y, Passenger origin) {
        super(x, y);
        this.origin = origin;
    }
    
}
