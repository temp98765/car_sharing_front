package gui.action;

import gui.Canvas;
import gui.ToolState;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import static javax.swing.Action.LARGE_ICON_KEY;
import javax.swing.ImageIcon;


public class CursorAction extends AbstractAction {
    private final Canvas canvas;
    
    public CursorAction(Canvas canvas) throws IOException {
        this.canvas = canvas;
        putValue(NAME, "Cursor");
        putValue(SHORT_DESCRIPTION, "Cursor. Right click to delete any entity in the tile.");
        putValue(LARGE_ICON_KEY, new ImageIcon(new ImageIcon(getClass().getResource("../cursor.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setCurrentTool(ToolState.CURSOR);
    }
}
