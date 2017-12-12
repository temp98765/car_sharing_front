package logic;

public class Passenger extends Entity {

    private static int id = 0;
    
    public Passenger(int x, int y) {
        super(id++, x, y);
    }
    
    @Override
    public String toString() {
        return "Passenger {id = " + id + " position = (" + position.x + ", " + position.y + ")}";
    }
}
