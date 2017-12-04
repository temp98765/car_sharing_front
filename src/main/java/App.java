import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    private final JFrame frame = new JFrame();
    private final JButton step = new JButton("step");
    private final JButton solve = new JButton("solve");
    
    private final SimulationState simulationState = new SimulationState();
    private final GridViewer gridViewer = new GridViewer(simulationState);
    
    public App() {
        JPanel rightColumn = new JPanel();
        {
            rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.PAGE_AXIS));
            rightColumn.add(step);
            rightColumn.add(solve);
        }
        
        JPanel container = new JPanel();
        {
            container.setLayout(new FlowLayout());
            container.add(gridViewer);
            container.add(rightColumn);
        }
        
        frame.setContentPane(container);
        frame.setTitle("Car Sharing front");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
        frame.setVisible(true);
    }
}
