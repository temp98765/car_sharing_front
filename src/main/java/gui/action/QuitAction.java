package gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


public class QuitAction extends AbstractAction {

    public QuitAction() {
        putValue(NAME, "Quit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    

    
}
