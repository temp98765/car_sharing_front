package gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import logic.SimulationState;


public class OpenSimulationAction extends AbstractAction {

    private final SimulationState simulationState;
    
    public OpenSimulationAction(SimulationState simulationState) {
        super("Open simulation");
        this.simulationState = simulationState;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == APPROVE_OPTION) {
            simulationState.load(fileChooser.getSelectedFile());
        }
    }
    
}
