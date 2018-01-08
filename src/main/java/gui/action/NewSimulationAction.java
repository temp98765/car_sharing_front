package gui.action;

import gui.Canvas;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import logic.SimulationState;


public class NewSimulationAction extends AbstractAction {

    SimulationState simulationState;
    Canvas canvas;
    
    public NewSimulationAction(SimulationState simulationState, Canvas canvas) {
        super("New simulation");
        this.simulationState = simulationState;
        this.canvas = canvas;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        simulationState.clear();
        canvas.repaint();
    }
}
