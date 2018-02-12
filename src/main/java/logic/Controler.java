package logic;

import java.awt.Point;


public class Controler {
    
    public static boolean addCar(SimulationState state, int x, int y) {
        if (state.getEntities()[x][y] != null) {
            return false;
        }
        state.addCar(x, y);
        return true;
    }
    
    public static Passenger addPassenger(SimulationState state, int x, int y) {
        if (state.getEntities()[x][y] != null) {
            return null;
        }
        return state.addPassenger(x, y);
    }
     
    public static boolean addDestination(SimulationState state, int x, int y, Passenger passenger) {
        if (state.getEntities()[x][y] != null) {
            return false;
        }
        return state.addDestination(x, y, passenger);
    }
    
    private static boolean checkIfCoordAreInBound(SimulationState state, int x, int y) {
        return x >= 0 & x < state.getSize() && y >= 0 && y < state.getSize();
    }
    
    public static boolean removeAt(SimulationState state, int x, int y) {
        if (!checkIfCoordAreInBound(state, x, y)) {
            return false;
        }
        
        Entity entity = state.entities[x][y];
        if (entity != null) {
            if (entity instanceof Passenger) {
                Passenger passenger = (Passenger) entity;
                if (passenger.destination != null) {
                    state.entities[passenger.destination.position.x][passenger.destination.position.y] = null;
                    passenger.destination = null;
                }
            } else if (entity instanceof Destination) {
                Destination destination = (Destination) entity;
                if (destination.origin != null) {
                    state.entities[destination.origin.position.x][destination.origin.position.y] = null;
                    destination.origin = null;
                }
            }
           state.entities[x][y] = null;
           return true;
       }
        return false;
    }
    
    public static Entity grabAt(SimulationState state, int x, int y) {
        if (!checkIfCoordAreInBound(state, x, y)) {
            return null;
        } else {
            return state.entities[x][y];
        }
    }
    
    public static boolean moveAt(SimulationState state, Entity entityGrabbed, int x, int y) {
        if (!checkIfCoordAreInBound(state, x, y) || state.entities[x][y] != null) {
            return false;
        }
        assert(entityGrabbed != null);
        state.entities[entityGrabbed.position.x][entityGrabbed.position.y] = null;
        state.entities[x][y] = entityGrabbed;
        entityGrabbed.position = new Point(x, y);
        if (entityGrabbed instanceof Car) {
            ((Car) entityGrabbed).pastMove.clear();
        }
        return true;
    }
}
