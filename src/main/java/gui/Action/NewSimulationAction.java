package gui.action;

import gui.Canvas;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import logic.Controler;
import logic.SimulationState;


public class NewSimulationAction extends AbstractAction {

    private final Controler controler;
    private final Canvas canvas;
    
    public NewSimulationAction(Controler controler, Canvas canvas) {
        this.controler = controler;
        this.canvas = canvas;
        putValue(NAME, "New simulation");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        controler.clear();
        canvas.repaint();
    }
}
