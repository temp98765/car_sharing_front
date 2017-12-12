package logic;

public class Car extends Entity {
    private static int id = 0;
    
    public Car(int x, int y) {
        super(id++, x, y);
    }
    
    @Override
    public String toString() {
        return "Car {id = " + id + " position = (" + position.x + ", " + position.y + ")}";
    }
}
