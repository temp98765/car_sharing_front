package gui;

import logic.SimulationState;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import static javax.swing.SwingConstants.VERTICAL;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CarSharing implements ActionListener, ChangeListener {
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
        
        
        final JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn,BoxLayout.Y_AXIS));

        leftColumn.add(new JLabel("Size :"));
        size.addChangeListener(this);
        leftColumn.add(size);

        leftColumn.add(new JLabel("Algorithm :"));
        final String[] algosString = {"Deterministic" , "Simulated Annealing", "Genetic"};
        final JComboBox algos = new JComboBox(algosString);
        leftColumn.add(algos);

        leftColumn.add(step);
        leftColumn.add(solve);
        
        
        final JToolBar toolBar = new JToolBar(VERTICAL);
        
        cursorTool.setToolTipText("Cursor. Right click to delete any entity in the tile.");
        cursorTool.setPreferredSize(new Dimension(30, 30));
        cursorTool.setMinimumSize(new Dimension(30, 30));
        cursorTool.setMaximumSize(new Dimension(30, 30));
        cursorTool.addActionListener(this);
        toolBar.add(cursorTool);

        carTool.setToolTipText("Car. Add Car with left click. Right click to delete any entity in the tile.");
        carTool.setPreferredSize(new Dimension(30, 30));
        carTool.setMinimumSize(new Dimension(30, 30));
        carTool.setMaximumSize(new Dimension(30, 30));
        carTool.addActionListener(this);
        toolBar.add(carTool);

        passengerTool.setToolTipText("Passenger. Add passenger with left click. Right click to delete any entity in the tile.");
        passengerTool.setPreferredSize(new Dimension(30, 30));
        passengerTool.setMinimumSize(new Dimension(30, 30));
        passengerTool.setMaximumSize(new Dimension(30, 30));
        passengerTool.addActionListener(this);
        toolBar.add(passengerTool);
        
        
        final JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(toolBar, BorderLayout.EAST);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
       
        if (source == newSimulation) {
            simulationState.clear();
            canvas.repaint();
        } else if (source == openSimulation) {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
        } else if (source == saveSimulation) {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(null);
        } else if (source == quit) {
            System.exit(0);
        } else if (source == cursorTool) {
            canvas.setCurrentTool(CanvasTool.TOOL_CURSOR);
        } else if (source == carTool) {
            canvas.setCurrentTool(CanvasTool.TOOL_NEW_CAR);
        } else if (source == passengerTool) {
            canvas.setCurrentTool(CanvasTool.TOOL_NEW_PASSENGER);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        if (source == size) {
            simulationState.setSize((int) size.getValue());
            canvas.repaint();
        }
    }
}
