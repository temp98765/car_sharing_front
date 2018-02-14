package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationState {    
    
    public int size = 10;
    public Entity[][] entities = new Entity[size][size];
    
    
    public SimulationState() {
    }
    
    public void save(File file) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("" + size + System.lineSeparator());
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                     Entity entity = entities[x][y];
                    if (entity != null) {
                        writer.write("" + x + " " + y + " ");
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
}
