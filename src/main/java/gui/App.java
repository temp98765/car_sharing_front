package gui;

import gui.GridViewer;
import logic.SimulationState;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class App implements ActionListener {
    final JMenuItem newSimulation;
    final JMenuItem openSimulation;
    final JMenuItem saveSimulation;
    final JMenuItem quit;
    
    private final JFrame frame = new JFrame();
    private final JButton step = new JButton("Step");
    private final JButton solve = new JButton("Solve");
    
    private final SimulationState simulationState = new SimulationState();
    private final GridViewer gridViewer;
    
    public App() throws IOException {
        gridViewer = new GridViewer(simulationState);
        final JMenuBar menuBar = new JMenuBar();
        {
            final JMenu menuFile = new JMenu("File");
            
            newSimulation = new JMenuItem("New simulation");
            newSimulation.addActionListener(this);
            menuFile.add(newSimulation);
            
            openSimulation = new JMenuItem("Open simulation");
            openSimulation.addActionListener(this);
            menuFile.add(openSimulation);
            
            saveSimulation = new JMenuItem("Save simulation");
            saveSimulation.addActionListener(this);
            menuFile.add(saveSimulation);
            
            menuFile.addSeparator();
            
            quit = new JMenuItem("Quit");
            quit.addActionListener(this);
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
    
    public static void main(String[] arg) throws Exception {
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
       
        if (source == newSimulation) {
            simulationState.clear();
            gridViewer.repaint();
        } else if (source == openSimulation) {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
        } else if (source == saveSimulation) {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(null);
        } else if (source == quit) {
            System.exit(0);
        }
    }
}
