import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class App {
    private final JFrame frame = new JFrame();
    private final JButton step = new JButton("step");
    private final JButton solve = new JButton("solve");
    
    private final SimulationState simulationState = new SimulationState();
    private final GridViewer gridViewer = new GridViewer(simulationState);
    
    public App() {
        final JMenuBar menuBar = new JMenuBar();
        {
            final JMenu menuFile = new JMenu("File");
            final JMenuItem newSimulation = new JMenuItem("New simulation");
            menuFile.add(newSimulation);
            final JMenuItem openSimulation = new JMenuItem("Open simulation");
            menuFile.add(openSimulation);
            final JMenuItem saveSimulation = new JMenuItem("Save simulation");
            menuFile.add(saveSimulation);
            menuFile.addSeparator();
            final JMenuItem quit = new JMenuItem("Quit");
            menuFile.add(quit);
            
            menuBar.add(menuFile);
        }
        
        JPanel leftColumn = new JPanel();
        {
            leftColumn.setLayout(new BoxLayout(leftColumn,BoxLayout.Y_AXIS));
            leftColumn.add(new JLabel("Algorithm :"));
            final String[] algosString = {"Deterministic" , "Simulated Annealing", "Genetic"};
            final JComboBox algos = new JComboBox(algosString);
            leftColumn.add(algos);
            
            leftColumn.add(step);
            leftColumn.add(solve);
        }
        
        JPanel container = new JPanel();
        {
            container.setLayout(new FlowLayout());
            container.add(leftColumn);
            container.add(gridViewer);
  
        }
        
        frame.setJMenuBar(menuBar);
        frame.setContentPane(container);
        frame.setTitle("Car Sharing front");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] arg) {
        new App();
    }
}
