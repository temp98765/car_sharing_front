package logic;

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
        //@improve : remove entities outside the new size
    }
    
    public void addCar(Car car) {
        List<Car> cars = getAllCars();
        for (Car c : cars) {
            if (c.position.x == car.position.x && c.position.y == car.position.y) {
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
            if (p.position.x == passenger.position.x && p.position.y == passenger.position.y) {
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
            if (entity.position.x == x && entity.position.y == y) {
                it.remove();
            }
        }
    }
    
    public void clear() {
        entities.clear();
    }
}
