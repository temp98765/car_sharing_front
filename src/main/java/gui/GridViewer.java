package gui;

import logic.Car;
import logic.Passenger;
import logic.SimulationState;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GridViewer extends JPanel implements ActionListener {

    private final SimulationState simulationState;
    private final Canvas canvas;
    
    final JButton cursorTool;
    final JButton carTool;
    final JButton passengerTool;
    
    public GridViewer(SimulationState simulationState) throws IOException {
        this.simulationState = simulationState;
        canvas = new Canvas(simulationState);
        
        simulationState.addCar(new Car(3, 0));//temp
        simulationState.addPassenger(new Passenger(1, 1));
        
        setLayout(new BorderLayout());
        JPanel rightColumn = new JPanel();
        {
            rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
            
            cursorTool = new JButton();
            cursorTool.setToolTipText("Cursor. Right click to delete any entity in the tile.");
            cursorTool.setMaximumSize(new Dimension(30, 30));
            cursorTool.addActionListener(this);
            rightColumn.add(cursorTool);
            
            carTool = new JButton();
            carTool.setToolTipText("Car. Add Car with left click. Right click to delete any entity in the tile.");
            carTool.setMaximumSize(new Dimension(30, 30));
            carTool.addActionListener(this);
            rightColumn.add(carTool);
            
            passengerTool = new JButton();
            passengerTool.setToolTipText("Passenger. Add passenger with left click. Right click to delete any entity in the tile.");
            passengerTool.setMaximumSize(new Dimension(30, 30));
            passengerTool.addActionListener(this);
            rightColumn.add(passengerTool);
            
        }
        add(rightColumn, BorderLayout.EAST);
        add(canvas, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == cursorTool) {
            canvas.setCurrentTool(CanvasTool.TOOL_CURSOR);
        } else if (source == carTool) {
            canvas.setCurrentTool(CanvasTool.TOOL_NEW_CAR);
        } else if (source == passengerTool) {
            canvas.setCurrentTool(CanvasTool.TOOL_NEW_PASSENGER);
        }
    }
}
