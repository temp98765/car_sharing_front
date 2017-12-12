package logic;

import java.awt.Point;

public class Entity {
    final public Point position;
    
    Entity(int x, int y) {
        position = new Point(x, y);
    }
    
    @Override
    public String toString() {
        return "Entity {position = " + position.x + ", " + position.y + "}";
    }
}
