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

    @Override
    protected void paintComponent(Graphics g) {
        if (buttonImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(buttonImage, 0, 0, width, height, null);
            g2d.dispose();
        }
        super.paintComponent(g);
    }
}