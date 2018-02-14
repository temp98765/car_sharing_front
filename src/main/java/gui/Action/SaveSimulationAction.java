package gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import logic.SimulationState;


public class SaveSimulationAction extends AbstractAction {

    private final SimulationState simulationState;
    
    public SaveSimulationAction(SimulationState simulationState) {
        this.simulationState = simulationState;
        putValue(NAME, "Save simulation");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        final JFileChooser fileChooser = new JFileChooser();
        String filename = "simulationSave.txt";
        fileChooser.setSelectedFile(new File(filename));
        int result = fileChooser.showSaveDialog(null);
        if (result == APPROVE_OPTION) {
            simulationState.save(fileChooser.getSelectedFile());
        }
    }
    
}
