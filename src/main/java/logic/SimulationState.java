package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationState {    
    private final List<Entity> entities = new ArrayList<>();
    private int size = 10;
    
    public SimulationState() {
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        assert(size > 3);
        this.size = size;
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity entity = it.next();
            if (entity.getPosition().x >= size || entity.getPosition().y >= size) {
                it.remove();
            }
        }
    }
    
    public void addCar(Car car) {
        List<Car> cars = getAllCars();
        for (Car c : cars) {
            if (c.getPosition().equals(car)) {
                return; //@fix : temporarily disable multiple cars at the same place
            }
        }
        entities.add(car);
    }
    
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Car) {
                cars.add((Car) entity);
            }
        }
        return cars;
    }
    
    public void addPassenger(Passenger passenger) {
        List<Passenger> passengers = getAllPassengers();
        for (Passenger p : passengers) {
            if (p.getPosition().equals(passenger)) {
                return; //@fix : temporarily disable multiple passengers at the same place
            }
        }
        entities.add(passenger);
    }
    
    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Passenger) {
                passengers.add((Passenger) entity);
            }
        }
        return passengers;
    }
    
    public void removeAt(int x, int y) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity entity = it.next();
            if (entity.getPosition().equals(new Point(x, y))) {
                it.remove();
            }
        }
    }
    
    public void clear() {
        entities.clear();
    }
}
