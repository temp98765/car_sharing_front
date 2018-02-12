package logic;

import java.awt.Point;


public class Controler {
    
    private SimulationState simulation;
    private int carId = 0;
    private int passengerId = 0;
    
    public Controler(SimulationState simulation) {
        this.simulation = simulation;
    }
    
    public Entity[][] getEntities() {
        return simulation.entities;
    }
    
    public boolean addCar(int x, int y) {
        if (simulation.entities[x][y] != null) {
            return false;
        }
        Car car = new Car(x, y);
        car.id = carId++;
        simulation.entities[x][y] = car;
        return true;
    }
    
    public Passenger addPassenger(int x, int y) {
        if (simulation.entities[x][y] != null) {
            return null;
        }
        Passenger passenger = new Passenger(x, y, null);
        passenger.id = passengerId++;
        simulation.entities[x][y] = passenger;
        return passenger;
    }
     
    public boolean addDestination(int x, int y, Passenger passenger) {
        if (simulation.entities[x][y] != null) {
            return false;
        }
        Destination destination = new Destination(x, y, passenger);
        destination.id = passenger.id;
        assert(passenger.destination == null);
        passenger.destination = destination;
        simulation.entities[x][y] = destination;
        return true;
    }
    
    private boolean checkIfCoordAreInBound(int x, int y) {
        return x >= 0 & x < getSize() && y >= 0 && y < getSize();
    }
    
    public boolean removeAt(int x, int y) {
        if (!checkIfCoordAreInBound(x, y)) {
            return false;
        }
        
        Entity entity = simulation.entities[x][y];
        if (entity != null) {
            if (entity instanceof Passenger) {
                Passenger passenger = (Passenger) entity;
                if (passenger.destination != null) {
                    simulation.entities[passenger.destination.position.x][passenger.destination.position.y] = null;
                    passenger.destination = null;
                }
            } else if (entity instanceof Destination) {
                Destination destination = (Destination) entity;
                if (destination.origin != null) {
                    simulation.entities[destination.origin.position.x][destination.origin.position.y] = null;
                    destination.origin = null;
                }
            }
           simulation.entities[x][y] = null;
           return true;
       }
        return false;
    }
    
    public Entity grabAt(int x, int y) {
        if (!checkIfCoordAreInBound(x, y)) {
            return null;
        } else {
            return simulation.entities[x][y];
        }
    }
    
    public boolean moveAt(Entity entityGrabbed, int x, int y) {
        if (!checkIfCoordAreInBound(x, y) || simulation.entities[x][y] != null) {
            return false;
        }
        assert(entityGrabbed != null);
        simulation.entities[entityGrabbed.position.x][entityGrabbed.position.y] = null;
        simulation.entities[x][y] = entityGrabbed;
        entityGrabbed.position = new Point(x, y);
        if (entityGrabbed instanceof Car) {
            ((Car) entityGrabbed).pastMove.clear();
        }
        return true;
    }
    
    public int getSize() {
        return simulation.size;
    }
    
    public boolean setSize(int size) {
        if (size < 3) {
            return false;
        }
        simulation.size = size;
        simulation.entities = new Entity[size][size];
        carId = 0;
        passengerId = 0;
        return true;
    }
    
    public void clear() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                simulation.entities[i][j] = null;
            }  
        }
        carId = 0;
        passengerId = 0;
    }
}
