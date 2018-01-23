package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Car extends Entity {
    
    protected Entity target;
    protected Passenger passenger;
    public final List<Point> pastMove = new ArrayList<>();
    
    public Car(int x, int y) {
        super(x, y);
    }
}
