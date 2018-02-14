package logic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Controler {
    
    private List<Listener> listeners = new ArrayList<>();
    private SimulationState simulation;
    private int carId = 0;
    private int passengerDestinationId = 0;
    
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
        notifyAllListener();
        return true;
    }
    
    public Passenger addPassenger(int x, int y) {
        if (simulation.entities[x][y] != null) {
            return null;
        }
        Passenger passenger = new Passenger(x, y, null);
        passenger.id = passengerDestinationId++;
        simulation.entities[x][y] = passenger;
        notifyAllListener();
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
        notifyAllListener();
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
           notifyAllListener();
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
        notifyAllListener();
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
        passengerDestinationId = 0;
        return true;
    }
    
    public void clear() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                simulation.entities[i][j] = null;
            }  
        }
        carId = 0;
        passengerDestinationId = 0;
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    private void notifyAllListener() {
        for (Listener l : listeners) {
            l.needRefresh();
        }
    }
    
     public void save(File file) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("" + simulation.size + System.lineSeparator());
            for (int x = 0; x < simulation.size; x++) {
                for (int y = 0; y < simulation.size; y++) {
                     Entity entity = simulation.entities[x][y];
                    if (entity != null) {
                        writer.write("" + x + " " + y + " " + entity.id + " ");
                        if (entity instanceof Car) { //@fix: put write in entity ?
                            writer.write("Car");
                        } else if (entity instanceof Passenger) {
                            Passenger passenger = (Passenger) entity;
                            writer.write("Passenger " + passenger.destination.position.x + " " + passenger.destination.position.y);
                        } else if (entity instanceof Destination) {
                            Destination destination = (Destination) entity;
                            writer.write("Destination " + destination.origin.position.x + " " + destination.origin.position.y);
                        }
                        writer.write(System.lineSeparator());
                    }
                }
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(SimulationState.class.getName()).log(Level.SEVERE, null, ex); //@fix : add error box
        }
    }
     
    private class Tuple<T> {
        T left;
        Point right;
        Tuple(T left, Point right) {
            this.left = left;
            this.right = right;
        }
    }
    
    public void load(File file) {
        //@fix: check if destination and passenger share the same id
        int lineNb = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            int newSize = -1;
            Entity[][] newEntities = null;
            List<Tuple<Passenger>> passengerToResolve = new ArrayList<>();
            List<Tuple<Destination>> destinationToResolve = new ArrayList<>();
            while (line != null && !line.isEmpty()) {
                lineNb++;
                if (lineNb == 1) {
                    Scanner scanner = new Scanner(line);
                    newSize = scanner.nextInt();
                    if (newSize < 3) {
                        System.out.println("Error : size is not valid");
                        br.close();
                        return;
                    }
                    newEntities = new Entity[newSize][newSize];
                } else {
                    String[] tokens = line.split(" ");
                    if (tokens.length < 4) {
                        System.out.println("Error : Not enough argument at line " + lineNb);
                        br.close();
                        return;
                    }
                    int x = Integer.parseInt(tokens[0]);
                    int y = Integer.parseInt(tokens[1]);
                    if (newEntities[x][y] != null) {
                        System.out.println("Error : Two entity on the same tile at line " + lineNb);
                        br.close();
                        return;
                    }
                     if (x < 0 || x >= simulation.size || y < 0 || y >= simulation.size) {
                        System.out.println("Error : entity out of bound at line " + lineNb);
                        br.close();
                        return;
                    }
                    int id = Integer.parseInt(tokens[2]);
                    String type = tokens[3];
                    if (type.equals("Car")) {
                        Car car = new Car(x, y);
                        car.id = id;
                        newEntities[x][y] = car;
                    } else if (type.equals("Passenger")) {
                        if (tokens.length < 6) {
                            System.out.println("Error : Not enough argument for a Passenger at line " + lineNb);
                            br.close();
                            return;
                        }
                        int destX = Integer.parseInt(tokens[4]);
                        int destY = Integer.parseInt(tokens[5]);
                        Passenger passenger = new Passenger(x, y, null);
                        passenger.id = id;
                        newEntities[x][y] = passenger;
                        passengerToResolve.add(new Tuple(passenger, new Point(destX, destY)));
                    } else if (type.equals("Destination")) {
                        if (tokens.length < 6) {
                            System.out.println("Error : Not enough argument for a Destination at line " + lineNb);
                            br.close();
                            return;
                        }
                        int originX = Integer.parseInt(tokens[4]);
                        int originY = Integer.parseInt(tokens[5]);
                        Destination destination = new Destination(x, y, null);
                        newEntities[x][y] = destination;
                        destination.id = id;
                        destinationToResolve.add(new Tuple(destination, new Point(originX, originY)));
                    }
                }
                line = br.readLine();
            }
            for (Tuple<Passenger> p : passengerToResolve) {
                Iterator<Tuple<Destination>> dIt = destinationToResolve.iterator();
                while (dIt.hasNext()) {
                    Tuple<Destination> d = dIt.next();
                    if (p.right.equals(d.left.position)) {
                        p.left.destination = d.left;
                        d.left.origin = p.left;
                        dIt.remove();
                    }
                }
            }
            if (!destinationToResolve.isEmpty()) {
                System.out.println("Error : destination witout origin");
                br.close();
                return;
            }
            simulation.size = newSize;
            simulation.entities = newEntities;
            carId = 0;
            passengerDestinationId = 0;
            for (int x = 0; x < simulation.size; x++) {
                for (int y = 0; y < simulation.size; y++) {
                    if (simulation.entities[x][y] != null) {
                        Entity entity = simulation.entities[x][y];
                        if (entity instanceof Car) {
                            if (entity.id >= carId) carId = entity.id + 1;
                        } else if (entity instanceof Destination || entity instanceof Passenger) {
                            if (entity.id >= passengerDestinationId) passengerDestinationId = entity.id + 1;
                        }
                    }
                }
            }
            
            notifyAllListener();
        } catch (NumberFormatException | NoSuchElementException ex) {
            System.out.println("Malformed file at line " + lineNb);
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex1) {
                    throw new RuntimeException(ex1);
                }
            }
            return;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
