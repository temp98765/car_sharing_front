package logic;

import java.awt.Point;

public class Entity {
    final int id;
    final public Point position;
    
    Entity(int id, int x, int y) {
        this.id = id;
        position = new Point(x, y);
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Entity {id = " + id + " position = (" + position.x + ", " + position.y + ")}";
    }
}
