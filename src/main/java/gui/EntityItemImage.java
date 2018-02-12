package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class EntityItemImage extends JPanel {
    
    private BufferedImage image;
    private static int SIZE = 50;
    
    public EntityItemImage(URL path) {
        try {
            image = ImageIO.read(path);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        setPreferredSize(new Dimension(SIZE, SIZE));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, SIZE, SIZE, null, null);
    }
}
