import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GridViewer extends JPanel implements ActionListener {

    private final SimulationState simulationState;
    private final Canvas canvas;
    
    final JButton cursorTool;
    final JButton carTool;
    final JButton passengerTool;
    
    public GridViewer(SimulationState simulationState) {
        this.simulationState = simulationState;
        canvas = new Canvas(simulationState);
        
        simulationState.tiles[3][0].cars.add(new Car()); //temp
        simulationState.tiles[1][1].passengers.add(new Passenger());
        
        setLayout(new BorderLayout());
        JPanel rightColumn = new JPanel();
        {
            rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
            
            cursorTool = new JButton();
            cursorTool.setMaximumSize(new Dimension(30, 30));
            cursorTool.addActionListener(this);
            rightColumn.add(cursorTool);
            
            carTool = new JButton();
            carTool.setMaximumSize(new Dimension(30, 30));
            carTool.addActionListener(this);
            rightColumn.add(carTool);
            
            passengerTool = new JButton();
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
            canvas.setCurrentTool(Canvas.CanvasTool.TOOL_CURSOR);
        } else if (source == carTool) {
            canvas.setCurrentTool(Canvas.CanvasTool.TOOL_NEW_CAR);
        } else if (source == passengerTool) {
            canvas.setCurrentTool(Canvas.CanvasTool.TOOL_NEW_PASSENGER);
        }
    }
}
