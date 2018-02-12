package logic;

public class SimulationState {    
    
    private int size = 10;
    protected Entity[][] entities = new Entity[size][size];
    
    private static int staticCarId = 0;
    private static int staticPassengerId = 0;
    
    
    public SimulationState() {
    }
    
    public int getSize() {
        return size;
    }
    
    public Entity[][] getEntities() {
        return entities;
    }
    
    public void setSize(int size) {
        assert(size > 3);
        this.size = size;
        entities = new Entity[size][size];
        staticCarId = 0;
        staticPassengerId = 0;
    }
    
    protected void addCar(int x, int y) {
        Car car = new Car(x, y);
        car.id = staticCarId++;
        entities[x][y] = car;
    }
    
    protected Passenger addPassenger(int x, int y) {
        Passenger passenger = new Passenger(x, y, null);
        passenger.id = staticPassengerId++;
        entities[x][y] = passenger;
        return passenger;
    }
    
    protected boolean addDestination(int x, int y, Passenger passenger) {
        Destination destination = new Destination(x, y, passenger);
        destination.id = passenger.id;
        assert(passenger.destination == null);
        passenger.destination = destination;
        entities[x][y] = destination;
        return true;
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
