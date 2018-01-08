package gui;

import gui.action.CarAction;
import gui.action.CursorAction;
import gui.action.NewSimulationAction;
import gui.action.OpenSimulationAction;
import gui.action.PassengerAction;
import gui.action.QuitAction;
import gui.action.SaveSimulationAction;
import logic.SimulationState;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CarSharing {
    private final JMenuItem newSimulation;
    private final JMenuItem openSimulation;
    private final JMenuItem saveSimulation;
    private final JMenuItem quit;
    
    private final JFrame frame = new JFrame();
    
    private final JSpinner size = new JSpinner(new SpinnerNumberModel(10, 3, 20, 1));
    private final JButton step = new JButton("Step");
    private final JButton solve = new JButton("Solve");
    
    private final SimulationState simulationState = new SimulationState();
    private final Canvas canvas;
    
    private final JButton cursorTool = new JButton();
    private final JButton carTool = new JButton();
    private final JButton passengerTool = new JButton();
    
    
    public CarSharing() throws IOException {
        canvas = new Canvas(simulationState);
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menuFile = new JMenu("File");

        newSimulation = new JMenuItem();
        newSimulation.setAction(new NewSimulationAction(simulationState, canvas));
        menuFile.add(newSimulation);

        openSimulation = new JMenuItem();
        openSimulation.setAction(new OpenSimulationAction());
        menuFile.add(openSimulation);

        saveSimulation = new JMenuItem();
        saveSimulation.setAction(new SaveSimulationAction());
        menuFile.add(saveSimulation);

        menuFile.addSeparator();
        
        quit = new JMenuItem();
        quit.setAction(new QuitAction());
        menuFile.add(quit);

        menuBar.add(menuFile);
        
        
        final JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn,BoxLayout.Y_AXIS));

        leftColumn.add(new JLabel("Size :"));
        size.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                simulationState.setSize((int) size.getValue());
                canvas.repaint();
            }
            
        });
        leftColumn.add(size);

        leftColumn.add(new JLabel("Algorithm :"));
        final String[] algosString = {"Deterministic" , "Simulated Annealing", "Genetic"};
        final JComboBox algos = new JComboBox(algosString);
        leftColumn.add(algos);

        leftColumn.add(step);
        leftColumn.add(solve);
        
        
        final JToolBar toolBar = new JToolBar();
        
        cursorTool.setAction(new CursorAction(canvas));
        toolBar.add(cursorTool);

        carTool.setAction(new CarAction(canvas));
        toolBar.add(carTool);

        passengerTool.setAction(new PassengerAction(canvas));
        toolBar.add(passengerTool);
        
        
        final JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(toolBar, BorderLayout.PAGE_START);
        container.add(leftColumn, BorderLayout.WEST);
        container.add(canvas, BorderLayout.CENTER);
        
        frame.setJMenuBar(menuBar);
        frame.setContentPane(container);
        frame.setTitle("Car Sharing front");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] arg) throws Exception {
        new CarSharing();
    }
}
