package logic;

public class Car extends Entity {
    
    public Car(int x, int y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return "Car {id = " + getId() + " position = (" + getPosition().x + ", " + getPosition().y + ")}";
    }
}
