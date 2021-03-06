package gui;

import static gui.ToolState.*;
import logic.Car;
import logic.Passenger;
import logic.SimulationState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import logic.Controler;
import logic.Destination;
import logic.Entity;
import logic.Listener;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener, Listener {
    private final Controler controlerSimultaion;
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private int blockSize;
    
    private float mouseX = -1, mouseY = -1;
    
    private ToolState currentToolState = CURSOR;
    private Inspector inspector;
    
    public Canvas(Controler controlerSimultaion, Inspector inspector) {
        this.controlerSimultaion = controlerSimultaion;
        controlerSimultaion.addListener(this);
        this.inspector = inspector;
       
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    
    public void setCurrentTool(ToolState tool) {
        if (currentToolState == ADD_DESTINATION_PASSENGER && passengerCurrentlyCreated != null) {
            controlerSimultaion.removeAt(passengerCurrentlyCreated.position.x, passengerCurrentlyCreated.position.y);
             passengerCurrentlyCreated = null;
        }
        entityGrabbed = null;
        currentToolState = tool;
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        blockSize = WIDTH / controlerSimultaion.getSize();

        //clear
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < WIDTH ; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (x % blockSize == 0) {
                    g.drawLine(x, 0, x, HEIGHT);
                }
                if (y % blockSize == 0) {
                    g.drawLine(0, y, WIDTH, y);
                }
            }
        }
        
        Entity[][] entities = controlerSimultaion.getEntities();
        for (int x = 0; x < controlerSimultaion.getSize(); x++) {
            for (int y = 0; y < controlerSimultaion.getSize(); y++) {
                Entity entity = entities[x][y];
                if (entity == null) {
                    continue;
                }
                if (entity instanceof Car) {
                    Car car = (Car) entity;
                    g.setColor(Color.blue);
                    int halfSize = blockSize / 2;
                    for (int i = 0; i < car.pastMove.size(); i++) {
                        if (i == car.pastMove.size()-1) {
                            g.drawLine(car.pastMove.get(i).x * blockSize + halfSize, car.pastMove.get(i).y * blockSize + halfSize,
                                       car.position.x * blockSize + halfSize, car.position.y * blockSize + halfSize);
                        } else {
                            g.drawLine(car.pastMove.get(i).x * blockSize + halfSize, car.pastMove.get(i).y * blockSize + halfSize,
                                       car.pastMove.get(i+1).x * blockSize + halfSize, car.pastMove.get(i+1).y * blockSize + halfSize);
                        }
                    }
                }
                drawEntity(g, entity, null);
            }  
        }
        
        //Draw cursor
        if (mouseX != -1 && mouseY != -1) {
            switch (currentToolState) {
                case ADD_CAR:
                    drawCar(g, (int)mouseX, (int)mouseY);
                break;
                case ADD_PASSENGER:
                    drawPassenger(g, (int)mouseX, (int)mouseY);
                break;
                case ADD_DESTINATION_PASSENGER:
                    drawDestination(g, (int)mouseX, (int)mouseY);
                break;
                case CURRENTLY_MOVING:
                    assert(entityGrabbed != null);
                    drawEntity(g, entityGrabbed, new Point((int)mouseX, (int)mouseY));
                break;
            }
        }
    }
    
    private void drawEntity(Graphics g, Entity entity, Point position) {
        int x;
        int y;
        if (position == null) {
            x = entity.position.x * blockSize;
            y = entity.position.y * blockSize;
        } else {
            x = position.x;
            y = position.y;
        }

        if (entity instanceof Car) {
            drawCar(g, x, y);
            drawId(g, entity.id, x, y);
        } else if (entity instanceof Passenger) {
            drawPassenger(g, x, y);
            drawId(g, entity.id, x, y);
        } else if (entity instanceof Destination) {
            drawDestination(g, x, y);
            drawId(g, entity.id, x, y);
        }
    }
    
    private void drawCar(Graphics g, int x, int y) {
        BufferedImage image;
    	try {
            image = ImageIO.read(getClass().getResource("car.png"));
	} catch (IOException e) {
            throw new RuntimeException(e);
	}
    	g.drawImage(image, x, y, blockSize, blockSize, null, null);
    }
    
    private void drawPassenger(Graphics g, int x, int y) {
        BufferedImage image;
    	try {
            image = ImageIO.read(getClass().getResource("passenger.png"));
	} catch (IOException e) {
            throw new RuntimeException(e);
	}
    	g.drawImage(image, x, y, blockSize, blockSize, null, null);
       
    }
    
    private void drawDestination(Graphics g, int x, int y) {
        BufferedImage image;
    	try {
            image = ImageIO.read(getClass().getResource("flag.png"));
	} catch (IOException e) {
            throw new RuntimeException(e);
	}
    	g.drawImage(image, x, y, blockSize, blockSize, null, null);
        
    }
    
    private void drawId(Graphics g, int id, int x, int y) {
        g.setColor(Color.RED);
        //use a bigger font
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(20f); //@fix : hardcoded size
        g.setFont(newFont);
        g.drawString("" + id, x, y + blockSize);
    }
    
    
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentToolState == CURRENTLY_MOVING) {
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
            
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (currentToolState != CURSOR && currentToolState != CAN_MOVE) {
            repaint();
        }
    }

    private Passenger passengerCurrentlyCreated = null;
    
    @Override
    public void mouseClicked(MouseEvent e) {
       int tileX = e.getX() / blockSize;
       int tileY = e.getY() / blockSize;
       
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (currentToolState) {
                case CURSOR: {
                    Entity entity = controlerSimultaion.grabAt(tileX, tileY);
                    if (entity != null) {
                        inspector.setSelectedEntity(entity);
                    }
                } break;
                case ADD_CAR: {
                    controlerSimultaion.addCar(tileX, tileY);
                } break;
                case ADD_PASSENGER: {
                    passengerCurrentlyCreated = controlerSimultaion.addPassenger(tileX, tileY);
                    if (passengerCurrentlyCreated != null) {
                        setCurrentTool(ADD_DESTINATION_PASSENGER);
                    }
                } break;
                case ADD_DESTINATION_PASSENGER: {
                   if (controlerSimultaion.addDestination(tileX, tileY, passengerCurrentlyCreated)) {
                       passengerCurrentlyCreated = null;
                       setCurrentTool(ADD_PASSENGER);
                   }
                } break;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) { 
            controlerSimultaion.removeAt(tileX, tileY);
            if (currentToolState == ADD_DESTINATION_PASSENGER && passengerCurrentlyCreated != null && passengerCurrentlyCreated.position.equals(new Point(tileX, tileY))) {
                currentToolState = ADD_PASSENGER;
            }
        }
    }

    Entity entityGrabbed = null;
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (currentToolState == CAN_MOVE) {
            int tileX = e.getX() / blockSize;
            int tileY = e.getY() / blockSize;
            entityGrabbed = controlerSimultaion.grabAt(tileX, tileY);
            if (entityGrabbed != null) {
                currentToolState = CURRENTLY_MOVING;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentToolState == CURRENTLY_MOVING) {
            assert(entityGrabbed != null);
            currentToolState = CAN_MOVE;
            int tileX = e.getX() / blockSize;
            int tileY = e.getY() / blockSize;
            controlerSimultaion.moveAt(entityGrabbed, tileX, tileY);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = -1;
        mouseY = -1;
        if (currentToolState != CURSOR && currentToolState != CAN_MOVE) {
            repaint();
        }
    }

    @Override
    public void needRefresh() {
        repaint();
    }
}
