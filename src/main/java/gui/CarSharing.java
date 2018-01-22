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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.Box;
import logic.TestAlgo;

public class CarSharing extends JFrame {
    private final JMenuItem newSimulation;
    private final JMenuItem openSimulation;
    private final JMenuItem saveSimulation;
    private final JMenuItem quit;
    
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
        
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn,BoxLayout.PAGE_AXIS));
        
        JPanel algorithmeLayout = new JPanel();
        JLabel algorithmeLabel = new JLabel("Algorithme :");
        leftColumn.add(algorithmeLabel);
        //final String[] algosString = {"Deterministic" , "Simulated Annealing", "Genetic"};
        final String[] algosString = {"Test Algo"};
        final JComboBox algos = new JComboBox(algosString);
        algos.setMaximumSize(new Dimension(200, 30));
        algorithmeLayout.setLayout(new BoxLayout(algorithmeLayout,BoxLayout.LINE_AXIS));
        algorithmeLayout.setAlignmentX(Component.LEFT_ALIGNMENT);
        algorithmeLayout.add(algos);
        leftColumn.add(algorithmeLayout);
        
        leftColumn.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel sizeLayout = new JPanel();
        JLabel sizeLabel = new JLabel("Size : ");
        leftColumn.add(sizeLabel);
        size.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                simulationState.setSize((int) size.getValue());
                canvas.repaint();
            }
        });
        size.setMaximumSize(new Dimension(200, 30));
        sizeLayout.setLayout(new BoxLayout(sizeLayout,BoxLayout.LINE_AXIS));
        sizeLayout.setAlignmentX(Component.LEFT_ALIGNMENT);
        sizeLayout.add(size);
        leftColumn.add(sizeLayout);
        
        leftColumn.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel solveLayout = new JPanel();
        solveLayout.setLayout(new BoxLayout(solveLayout,BoxLayout.PAGE_AXIS));
        sizeLayout.setAlignmentX(Component.LEFT_ALIGNMENT);
        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestAlgo.step(simulationState);
                canvas.repaint();
            }
        });
        solveLayout.add(step);
        solveLayout.add(solve);
        leftColumn.add(solveLayout);
        
        
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
        
        setJMenuBar(menuBar);
        setContentPane(container);
        setTitle("Car Sharing front");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
        setVisible(true);
    }
    
    public static void main(String[] arg) throws Exception {
        new CarSharing();
    }
}
