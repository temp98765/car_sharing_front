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

        newSimulation = new JMenuItem("New simulation");
        newSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationState.clear();
                canvas.repaint();
            }
        });
        menuFile.add(newSimulation);

        openSimulation = new JMenuItem("Open simulation");
        openSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
            }
        });
        menuFile.add(openSimulation);

        saveSimulation = new JMenuItem("Save simulation");
        saveSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(null);
            }
        });
        menuFile.add(saveSimulation);

        menuFile.addSeparator();

        quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
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
        
        cursorTool.setToolTipText("Cursor. Right click to delete any entity in the tile.");
        cursorTool.setPreferredSize(new Dimension(30, 30));
        cursorTool.setMinimumSize(new Dimension(30, 30));
        cursorTool.setMaximumSize(new Dimension(30, 30));
        cursorTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentTool(CanvasTool.TOOL_CURSOR);
            }
        });
        toolBar.add(cursorTool);

        carTool.setToolTipText("Car. Add Car with left click. Right click to delete any entity in the tile.");
        carTool.setPreferredSize(new Dimension(30, 30));
        carTool.setMinimumSize(new Dimension(30, 30));
        carTool.setMaximumSize(new Dimension(30, 30));
        carTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentTool(CanvasTool.TOOL_NEW_CAR);
            }
        });
        toolBar.add(carTool);

        passengerTool.setToolTipText("Passenger. Add passenger with left click. Right click to delete any entity in the tile.");
        passengerTool.setPreferredSize(new Dimension(30, 30));
        passengerTool.setMinimumSize(new Dimension(30, 30));
        passengerTool.setMaximumSize(new Dimension(30, 30));
        passengerTool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setCurrentTool(CanvasTool.TOOL_NEW_PASSENGER);
            }
        });
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
