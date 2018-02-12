package gui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import logic.Car;
import logic.Entity;


public class Inspector extends JPanel {
    
    private Entity entity;
    
    public Inspector() {
       //setPreferredSize(new Dimension(150, 500));
       //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       add(new EntityItem());
       add(Box.createRigidArea(new Dimension(1, 5)));
    }

    public void setSelectedEntity(Entity entity) {
        this.entity = entity;
        if (entity != null) {
            if (entity instanceof Car) {
                add(new EntityItem());
            }
        }
    }
}
