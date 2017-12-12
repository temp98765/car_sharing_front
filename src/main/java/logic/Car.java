package logic;


import logic.Entity;

public class Car extends Entity {
    public Car(int x, int y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return "Car {position = " + position.x + ", " + position.y + "}";
    }
}
