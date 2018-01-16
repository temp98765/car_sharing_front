package logic;

import java.awt.Point;

public class Entity {
    private int id;
    private final Point position;
    
    public Entity(int x, int y) {
        position = new Point(x, y);
    }
    
    public int getId() {
        return id;
    }
    
     public void setId(int id) {
        this.id = id;
    }
    
    public Point getPosition() {
        return position;
    }
    
    @Override
    public String toString() {
        return "Entity {id = " + id + " position = (" + position.x + ", " + position.y + ")}";
    }
}
