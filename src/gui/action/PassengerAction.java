package gui.action;

import gui.Canvas;
import gui.CanvasTool;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import static javax.swing.Action.LARGE_ICON_KEY;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;


public class PassengerAction extends AbstractAction {

    private Canvas canvas;
    
    public PassengerAction(Canvas canvas) throws IOException {
        this.canvas = canvas;
        putValue(NAME, "Passenger");
        putValue(SHORT_DESCRIPTION, "Passenger. Add passenger with left click. Right click to delete any entity in the tile.");
        putValue(LARGE_ICON_KEY, new ImageIcon(ImageIO.read(getClass().getResource("../passenger.png"))));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setCurrentTool(CanvasTool.TOOL_NEW_PASSENGER);
    }
    
}
