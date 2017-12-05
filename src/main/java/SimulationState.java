import java.util.ArrayList;
import java.util.List;

public class SimulationState {
    
    public class Tile {
        public final List<Car> cars = new ArrayList<>();
        public final List<Passenger> passengers = new ArrayList<>();
    }
    
    public Tile[][] tiles = new Tile[10][10];

    public SimulationState() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new Tile();
            }
        }
    }
}
