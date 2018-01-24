package logic;

public class SimulationState {    
    
    private int size = 10;
    public Entity[][] entities = new Entity[size][size];
    
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
        entities = new Entity[size][size];
        staticCarId = 0;
        staticPassengerId = 0;
    }
    
    public void addCar(int x, int y) {
        if (entities[x][y] != null) {
            return;
        }
        Car car = new Car(x, y);
        car.id = staticCarId++;
        entities[x][y] = car;
    }
    
    public Passenger addPassenger(int x, int y) {
        if (entities[x][y] != null) {
            return null;
        }
        Passenger passenger = new Passenger(x, y, null);
        passenger.id = staticPassengerId++;
        entities[x][y] = passenger;
        return passenger;
    }
    
    public boolean addDestination(int x, int y, Passenger passenger) {
        if (entities[x][y] != null) {
            return false;
        }
        Destination destination = new Destination(x, y, passenger);
        destination.id = passenger.id;
        assert(passenger.destination == null);
        passenger.destination = destination;
        entities[x][y] = destination;
        return true;
    }
    
    public void removeAt(int x, int y) {
        Entity entity = entities[x][y];
        if (entity != null) {
            if (entity instanceof Passenger) {
                Passenger passenger = (Passenger) entity;
                if (passenger.destination != null) {
                    entities[passenger.destination.position.x][passenger.destination.position.y] = null;
                    passenger.destination = null;
                }
            } else if (entity instanceof Destination) {
                Destination destination = (Destination) entity;
                if (destination.origin != null) {
                    entities[destination.origin.position.x][destination.origin.position.y] = null;
                    destination.origin = null;
                }
            }
           entities[x][y] = null;
       }
    }
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                entities[i][j] = null;
            }  
        }
        staticCarId = 0;
        staticPassengerId = 0;
    }
}
