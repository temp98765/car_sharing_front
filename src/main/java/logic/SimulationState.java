package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationState {    
    private final List<Entity> entities = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();
    private final List<Passenger> passengers = new ArrayList<>();
    private int size = 10;
    private static int staticCarId = 0;
    private static int staticPassengerId = 0;
    
    public SimulationState() {
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        assert(size > 3);
        this.size = size;
        clear();
    }
    
    public boolean addCar(Car car) {
        for (Car c : cars) {
            if (c.getPosition().equals(car.getPosition())) {
                return false; //@fix : temporarily disable multiple cars at the same place
            }
        }
        car.setId(staticCarId++);
        cars.add(car);
        entities.add(car);
        return true;
    }
    
    public List<Car> getAllCars() {
        return cars;
    }
    
    public boolean addPassenger(Passenger passenger) {
        for (Passenger p : passengers) {
            if (p.getPosition().equals(passenger.getPosition())) {
                return false; //@fix : temporarily disable multiple passengers at the same place
            }
        }
        passenger.setId(staticPassengerId++);
        passengers.add(passenger);
        entities.add(passenger);
        return true;
    }
    
    public List<Passenger> getAllPassengers() {
        return passengers;
    }
    
    public void removeAt(int x, int y) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity entity = it.next();
            if (entity.getPosition().equals(new Point(x, y))) {
                cars.remove(entity);
                passengers.remove(entity);
                it.remove();
            }
        }
    }
    
    public void clear() {
        cars.clear();
        passengers.clear();
        entities.clear();
    }
}
