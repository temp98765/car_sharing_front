package logic;

public class Passenger extends Entity {
    
    public Passenger(int x, int y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return "Passenger {id = " + getId() + " position = (" + getPosition().x + ", " + getPosition().y + ")}";
    }
}
