package gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;


public class SaveSimulationAction extends AbstractAction {

    public SaveSimulationAction() {
        putValue(NAME, "Save simulation");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
         final JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(null);
    }
    
}
