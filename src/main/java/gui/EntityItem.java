package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class EntityItem extends JPanel {
    
    public EntityItem() {
        
        setBorder(BorderFactory.createLineBorder(Color.blue));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel carHeaderPanel = new JPanel();
        carHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        carHeaderPanel.setLayout(new BoxLayout(carHeaderPanel, BoxLayout.X_AXIS));
        //carHeaderPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        carHeaderPanel.add(new EntityItemImage(getClass().getResource("car.png")));
        carHeaderPanel.add(new JLabel("Car"));
        add(carHeaderPanel);
        add(Box.createRigidArea(new Dimension(1, 5)));
        
        JPanel passengerLabelPanel = new JPanel();
        passengerLabelPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        passengerLabelPanel.add(new JLabel("Passengers : "));
        add(passengerLabelPanel);
        add(Box.createRigidArea(new Dimension(1, 5)));
        
        JPanel passengerPanel = new JPanel();
        passengerPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        passengerPanel.add(new EntityItemImage(getClass().getResource("passenger.png")));
        passengerPanel.add(new EntityItemImage(getClass().getResource("passenger.png")));
        passengerPanel.add(new EntityItemImage(getClass().getResource("passenger.png")));
        add(passengerPanel);
        //add(Box.createRigidArea(new Dimension(100, 10)));
    }
}
