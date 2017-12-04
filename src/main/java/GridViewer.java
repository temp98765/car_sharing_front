import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GridViewer extends JPanel {

    private final SimulationState simulationState;
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final int BLOCK_SIZE;
    
    public GridViewer(SimulationState simulationState) {
        this.simulationState = simulationState;
        BLOCK_SIZE = WIDTH / simulationState.tiles[0].length;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        simulationState.tiles[0][0].cars.add(new Car());
  
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
                    g.setColor(Color.RED);
                    g.fillPolygon(new int[]{x, x + BLOCK_SIZE / 4, x + BLOCK_SIZE / 2, x + 3 * BLOCK_SIZE / 4, x + BLOCK_SIZE, x + BLOCK_SIZE, x},
                                  new int[]{y + BLOCK_SIZE / 2, y + BLOCK_SIZE / 4, y + BLOCK_SIZE / 4,
					    y + BLOCK_SIZE / 2, y + BLOCK_SIZE / 2, y + 3 * BLOCK_SIZE / 4, y + 3 * BLOCK_SIZE / 4},
                                  7
                    );
                    g.fillOval(x + 1 * BLOCK_SIZE / 8, y + 5 * BLOCK_SIZE / 8, BLOCK_SIZE / 4, BLOCK_SIZE /4);
                    g.fillOval(x + 5 * BLOCK_SIZE / 8, y + 5 * BLOCK_SIZE / 8, BLOCK_SIZE / 4, BLOCK_SIZE / 4);
                }
            }
        }
    }   
}
