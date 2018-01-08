package gui.action;

import gui.Canvas;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import logic.SimulationState;


public class NewSimulationAction extends AbstractAction {

    private final SimulationState simulationState;
    private final Canvas canvas;
    
    public NewSimulationAction(SimulationState simulationState, Canvas canvas) {
        this.simulationState = simulationState;
        this.canvas = canvas;
        putValue(NAME, "New simulation");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        simulationState.clear();
        canvas.repaint();
    }
}
