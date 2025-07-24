import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageButton extends JButton {
    private BufferedImage buttonImage;
    private final int width;
    private final int height;

    public ImageButton(String imagePath, String actionCommand, int width, int height) {
        this.width = width;
        this.height = height;
        
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setActionCommand(actionCommand);
        
        try {
            buttonImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Failed to load button image: " + e.getMessage());
        }

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buttonImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(buttonImage, 0, 0, width, height, null);
        }
    }
}