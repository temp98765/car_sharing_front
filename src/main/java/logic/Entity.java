package logic;

import java.awt.Point;

public class Entity {
    public int id;
    public final Point position;
    
    public Entity(int x, int y) {
        position = new Point(x, y);
    }
}
