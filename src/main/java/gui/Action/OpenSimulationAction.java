package gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import logic.Controler;
import logic.SimulationState;


public class OpenSimulationAction extends AbstractAction {

    private final Controler controler;
    
    public OpenSimulationAction(Controler controler) {
        super("Open simulation");
        this.controler = controler;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == APPROVE_OPTION) {
            controler.load(fileChooser.getSelectedFile());
        }
    }
    
}
