import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;
    private final Dimension PANEL_SIZE;
    
    public BackgroundPanel(String backgroundPath, Dimension size) {
        this.PANEL_SIZE = size;
        setLayout(null);
        setOpaque(false);
        loadBackgroundImage(backgroundPath);
    }
    
    private void loadBackgroundImage(String backgroundPath) {
        try {
            backgroundImage = ImageIO.read(new File(backgroundPath));
        } catch (IOException e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }
    
    private void drawBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return PANEL_SIZE;
    }
}