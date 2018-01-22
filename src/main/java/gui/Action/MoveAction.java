package gui.Action;

import gui.Canvas;
import gui.ToolState;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import static javax.swing.Action.LARGE_ICON_KEY;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SHORT_DESCRIPTION;
import javax.swing.ImageIcon;


public class MoveAction extends AbstractAction {

    private final Canvas canvas;

    public MoveAction(Canvas canvas) throws IOException {
        this.canvas = canvas;
        putValue(NAME, "Move");
        putValue(SHORT_DESCRIPTION, "Move Entity. Hold to move entity. Right click to delete any entity in the tile.");
        putValue(LARGE_ICON_KEY, new ImageIcon(new ImageIcon(getClass().getResource("../hand.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setCurrentTool(ToolState.MOVE);
    }
    
}
