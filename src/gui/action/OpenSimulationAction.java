package gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;


public class OpenSimulationAction extends AbstractAction {

    public OpenSimulationAction() {
        super("Open simulation");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
    }
    
}
