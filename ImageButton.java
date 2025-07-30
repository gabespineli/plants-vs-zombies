import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Custom JButton for displaying an image as a button.
 * Handles loading and drawing the button image, and sets button size.
 */
public class ImageButton extends JButton {
    private BufferedImage buttonImage;
    private final int WIDTH;
    private final int HEIGHT;

    /**
     * Constructs an ImageButton with the specified image path, action command, and size.
     * @param imagePath the path to the button image file
     * @param actionCommand the action command for the button
     * @param width the width of the button
     * @param height the height of the button
     */
    public ImageButton(String imagePath, String actionCommand, int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        setOpaque(false);
        setVisible(true);
        setContentAreaFilled(false);  // important for transparent buttons
        setBorderPainted(false);
        setFocusPainted(false);
        setActionCommand(actionCommand);

        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                System.err.println("Image file does not exist: " + imagePath);
            } else {
                buttonImage = ImageIO.read(file);
            }
        } catch (IOException e) {
            System.err.println("Failed to load button image: " + imagePath + " - " + e.getMessage());
        }

        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Paints the button image onto the button.
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (buttonImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(buttonImage, 0, 0, WIDTH, HEIGHT, null);
            g2d.dispose();
        }
        super.paintComponent(g);
    }
}