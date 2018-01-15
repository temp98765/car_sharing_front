package gui;

import logic.Car;
import logic.Passenger;
import logic.SimulationState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener {
    private final SimulationState simulationState;
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private int blockSize;
    
    private float mouseX = -1, mouseY = -1;
    
    private CanvasTool currentTool = CanvasTool.TOOL_CURSOR;
    
    public Canvas(SimulationState simulationState) {
        this.simulationState = simulationState;
       
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    
    public void setCurrentTool(CanvasTool tool) {
        currentTool = tool;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        blockSize = WIDTH / simulationState.getSize();

        //clear
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        drawGrid(g);
        drawCars(g);
        drawPassengers(g);
        drawCursor(g);
    }
    
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < WIDTH ; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x % blockSize == 0) {
                    g.drawLine(x, 0, x, HEIGHT);
                }
                if (y % blockSize == 0) {
                    g.drawLine(0, y, WIDTH, y);
                }
            }
        }
    }
    
    private void drawCars(Graphics g) {
        List<Car> cars = simulationState.getAllCars();
        for (Car car : cars) {
            int pixelX = car.getPosition().x * blockSize;
            int pixelY = car.getPosition().y * blockSize;
            drawCar(g, pixelX, pixelY);
            drawId(g, car.getId(), pixelX, pixelY);
        }
    }
    
    private void drawCar(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillPolygon(new int[]{x, x + blockSize / 4, x + blockSize / 2, x + 3 * blockSize / 4, x + blockSize, x + blockSize, x},
                      new int[]{y + blockSize / 2, y + blockSize / 4, y + blockSize / 4,
                                y + blockSize / 2, y + blockSize / 2, y + 3 * blockSize / 4, y + 3 * blockSize / 4},
                      7);
        g.fillOval(x + 1 * blockSize / 8, y + 5 * blockSize / 8, blockSize / 4, blockSize /4);
        g.fillOval(x + 5 * blockSize / 8, y + 5 * blockSize / 8, blockSize / 4, blockSize / 4);
    }
    
    private void drawPassengers(Graphics g) {
        List<Passenger> passengers = simulationState.getAllPassengers();
        for (Passenger passenger : passengers) {
            int pixelX = passenger.getPosition().x * blockSize;
            int pixelY = passenger.getPosition().y * blockSize;
            drawPassenger(g, pixelX, pixelY);
            drawId(g, passenger.getId(), pixelX, pixelY);
        }
    }
    
    private void drawPassenger(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillPolygon(new int[]{x + blockSize / 4, x + 3 * blockSize / 4, x + 3 * blockSize / 4, x + blockSize / 2, x + blockSize / 4},
                      new int[]{ y + blockSize / 4, y + blockSize / 4, y + blockSize, y + 3 * blockSize / 4, y + blockSize},
                      5);
        g.fillOval(x + 3 * blockSize / 8, y, blockSize / 4, blockSize / 4);
    }
    
    private void drawId(Graphics g, int id, int x, int y) {
        g.setColor(Color.BLACK);
        //use a bigger font
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(20f); //@fix : hardcoded size
        g.setFont(newFont);
        g.drawString("" + id, x, y + blockSize);
    }
    
    private void drawCursor(Graphics g) {
        if (mouseX != -1 && mouseY != -1) {
            if (currentTool == CanvasTool.TOOL_NEW_CAR) {
                drawCar(g, (int)mouseX, (int)mouseY);
            } else if (currentTool == CanvasTool.TOOL_NEW_PASSENGER) {
                drawPassenger(g, (int)mouseX, (int)mouseY);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       int tileX = e.getX() / blockSize;
       int tileY = e.getY() / blockSize;
       
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (currentTool == CanvasTool.TOOL_NEW_CAR) { 
                List<Car> cars = simulationState.getAllCars();
                for (Car c : cars) {
                    if (c.getPosition().equals(new Point(tileX, tileY))) {
                        return; //@fix : temporarily disable multiple cars at the same place
                    }
                }
                simulationState.addCar(new Car(tileX, tileY));
            } else if (currentTool == CanvasTool.TOOL_NEW_PASSENGER) {
                List<Passenger> passengers = simulationState.getAllPassengers();
                for (Passenger p : passengers) {
                    if (p.getPosition().equals(new Point(tileX, tileY))) {
                        return; //@fix : temporarily disable multiple passengers at the same place
                    }
                }
                simulationState.addPassenger(new Passenger(tileX, tileY));
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) { 
            simulationState.removeAt(tileX, tileY);
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = -1;
        mouseY = -1;
        repaint();
    }
}
