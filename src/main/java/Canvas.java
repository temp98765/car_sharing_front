import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener {
    private final SimulationState simulationState;
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final int BLOCK_SIZE;
    
    float mouseX = -1, mouseY = -1;
    
    public enum CanvasTool {
        TOOL_CURSOR,
        TOOL_NEW_CAR,
        TOOL_NEW_PASSENGER,
    }
    
    private CanvasTool currentTool = CanvasTool.TOOL_CURSOR;
    
    public Canvas(SimulationState simulationState) {
        this.simulationState = simulationState;
        BLOCK_SIZE = WIDTH / simulationState.tiles[0].length;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    
    public void setCurrentTool(CanvasTool tool) {
        currentTool = tool;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        //clear
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        
        //draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < WIDTH ; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x % BLOCK_SIZE == 0) {
                    g.drawLine(x, 0, x, HEIGHT);
                }
                if (y % BLOCK_SIZE == 0) {
                    g.drawLine(0, y, WIDTH, y);
                }
            }
        }
        
        for (int x = 0; x < simulationState.tiles.length ; x++) {
            for (int y = 0; y < simulationState.tiles[0].length; y++) {
            
                //draw car
                if (simulationState.tiles[x][y].cars.size() > 0) {
                    int pixelX = x * BLOCK_SIZE;
                    int pixelY = y * BLOCK_SIZE;
                    drawCar(g, pixelX, pixelY);
                }
                
                if (simulationState.tiles[x][y].passengers.size() > 0) {
                    int pixelX = x * BLOCK_SIZE;
                    int pixelY = y * BLOCK_SIZE;
                    drawPassenger(g, pixelX, pixelY);
                }
            }
        }
        
        if (mouseX != -1 && mouseY != -1) {
            if (currentTool == CanvasTool.TOOL_NEW_CAR) {
                drawCar(g, (int)mouseX, (int)mouseY);
            } else if (currentTool == CanvasTool.TOOL_NEW_PASSENGER) {
                 drawPassenger(g, (int)mouseX, (int)mouseY);
            }
        }
    }
    
    private void drawCar(Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.fillPolygon(new int[]{x, x + BLOCK_SIZE / 4, x + BLOCK_SIZE / 2, x + 3 * BLOCK_SIZE / 4, x + BLOCK_SIZE, x + BLOCK_SIZE, x},
                      new int[]{y + BLOCK_SIZE / 2, y + BLOCK_SIZE / 4, y + BLOCK_SIZE / 4,
                                y + BLOCK_SIZE / 2, y + BLOCK_SIZE / 2, y + 3 * BLOCK_SIZE / 4, y + 3 * BLOCK_SIZE / 4},
                      7);
        g.fillOval(x + 1 * BLOCK_SIZE / 8, y + 5 * BLOCK_SIZE / 8, BLOCK_SIZE / 4, BLOCK_SIZE /4);
        g.fillOval(x + 5 * BLOCK_SIZE / 8, y + 5 * BLOCK_SIZE / 8, BLOCK_SIZE / 4, BLOCK_SIZE / 4);
    }
    
    private void drawPassenger(Graphics g, int x, int y) {
        g.setColor(Color.BLUE);
        g.fillPolygon(new int[]{x + BLOCK_SIZE / 4, x + 3 * BLOCK_SIZE / 4, x + 3 * BLOCK_SIZE / 4, x + BLOCK_SIZE / 2, x + BLOCK_SIZE / 4},
                      new int[]{ y + BLOCK_SIZE / 4, y + BLOCK_SIZE / 4, y + BLOCK_SIZE, y + 3 * BLOCK_SIZE / 4, y + BLOCK_SIZE},
                      5);
        g.fillOval(x + 3 * BLOCK_SIZE / 8, y, BLOCK_SIZE / 4, BLOCK_SIZE / 4);
    }


    @Override
    public void mouseDragged(MouseEvent e) {
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        //System.out.println("e" + e.getPoint());
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       int tileX = e.getX() / BLOCK_SIZE;
       int tileY = e.getY() / BLOCK_SIZE;
       
        if (e.getButton() == MouseEvent.BUTTON1) {
           if (currentTool == CanvasTool.TOOL_NEW_CAR) {
                if (simulationState.tiles[tileX][tileY].cars.isEmpty()) {
                    simulationState.tiles[tileX][tileY].cars.add(new Car());
                }
           } else if (currentTool == CanvasTool.TOOL_NEW_PASSENGER) {
                if (simulationState.tiles[tileX][tileY].passengers.isEmpty()) {
                    simulationState.tiles[tileX][tileY].passengers.add(new Passenger());
                }
           }
       } else if (e.getButton() == MouseEvent.BUTTON3) {
            simulationState.tiles[tileX][tileY].cars.clear();
            simulationState.tiles[tileX][tileY].passengers.clear();
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
