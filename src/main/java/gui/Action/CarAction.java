package gui.action;

import gui.Canvas;
import static gui.ToolState.ADD_CAR;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import static javax.swing.Action.LARGE_ICON_KEY;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;


public class CarAction extends AbstractAction {

    private Canvas canvas;
    
    public CarAction(Canvas canvas) throws IOException {
        this.canvas = canvas;
        putValue(NAME, "Car");
        putValue(SHORT_DESCRIPTION, "Car. Add Car with left click. Right click to delete any entity in the tile.");
        putValue(LARGE_ICON_KEY, new ImageIcon(new ImageIcon(getClass().getResource("../car.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setCurrentTool(ADD_CAR);
    }
    
}
