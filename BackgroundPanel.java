
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Custom JPanel for displaying a background image.
 * Handles loading and drawing the background image, and sets panel size.
 */
public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;
    private final Dimension PANEL_SIZE;
    
    /**
     * Constructs a BackgroundPanel with the specified image path and size.
     * @param backgroundPath the path to the background image file
     * @param size the preferred size of the panel
     */
    public BackgroundPanel(String backgroundPath, Dimension size) {
        this.PANEL_SIZE = size;
        setLayout(null);
        setOpaque(false);
        loadBackgroundImage(backgroundPath);
    }
    
    /**
     * Loads the background image from the specified file path.
     * @param backgroundPath the path to the background image file
     */
    private void loadBackgroundImage(String backgroundPath) {
        try {
            backgroundImage = ImageIO.read(new File(backgroundPath));
        } catch (IOException e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }
    }
    
    /**
     * Paints the background image onto the panel.
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }
    
    /**
     * Draws the background image or a fallback color if image is missing.
     * @param g the Graphics context
     */
    private void drawBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    /**
     * Gets the preferred size of the panel.
     * @return the preferred Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return PANEL_SIZE;
    }
}