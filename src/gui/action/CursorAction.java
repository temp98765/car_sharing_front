package gui.action;

import gui.Canvas;
import gui.CanvasTool;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;


public class CursorAction extends AbstractAction {
    private final Canvas canvas;
    
    public CursorAction(Canvas canvas) throws IOException {
        this.canvas = canvas;
        putValue(NAME, "Cursor");
        putValue(SHORT_DESCRIPTION, "Cursor. Right click to delete any entity in the tile.");
        putValue(LARGE_ICON_KEY, new ImageIcon(ImageIO.read(getClass().getResource("../cursor.png"))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setCurrentTool(CanvasTool.TOOL_CURSOR);
    }
}
