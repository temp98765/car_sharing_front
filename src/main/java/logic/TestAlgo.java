package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class TestAlgo {
    
    private static boolean isTargeted(Entity entity, List<Car> cars) {
        for (Car c : cars) {
            if (c.target != null && c.target.equals(entity)) {
                return true;
            }
        }
        return false;
    }
    
    public static void step(SimulationState state) {
        List<Car> cars = new ArrayList<>();
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < state.getSize(); i++) {
            for (int j = 0; j < state.getSize(); j++) {
                Entity entity = state.entities[i][j];
                if (entity == null) {
                    continue;
                }
                
                if (entity instanceof Car) {
                    cars.add((Car) entity);
                } else if (entity instanceof Passenger) {
                    passengers.add((Passenger) entity);
                }
            }    
        }
        
        for (Car c : cars) {
            if (c.target == null) {
                for (Passenger p : passengers) {
                    if (!isTargeted(p, cars)) {
                        c.target = p;
                    }
                }
            }
        }
        
        for (Car c : cars) {
            if (c.target != null) {
                Point oldPosition = (Point) c.position.clone();
                if (c.position.x > c.target.position.x) {
                    c.position.x--;
                } else  if (c.position.x < c.target.position.x) {
                    c.position.x++;
                } else if (c.position.y > c.target.position.y) {
                    c.position.y--;
                } else  if (c.position.y < c.target.position.y) {
                    c.position.y++;
                }
                
                
                if (c.position.equals(c.target.position)) {
                    if (c.target instanceof Passenger) {
                        Passenger passenger = (Passenger)c.target;
                        c.target = passenger.destination;
                        c.passenger = passenger;
                    } else if (c.target instanceof Destination) {
                        c.passenger = null;
                        c.target = null;
                    }
                    state.entities[c.position.x][c.position.y] = null;
                }
                
                if (!oldPosition.equals(c.position)) {
                    state.entities[oldPosition.x][oldPosition.y] = null;
                    assert(state.entities[c.position.x][c.position.y] == null);
                    state.entities[c.position.x][c.position.y] = c;
                }
            }
        }
    }
    
}
