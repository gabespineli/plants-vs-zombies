import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends BackgroundPanel {
    private static final String BACKGROUND_PATH = "assets/background/GamePanel.png";
    private static final String CONTAINER_IMAGE_PATH = "assets/ui/SeedSlot.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    private static final Dimension CONTAINER_SIZE = new Dimension(320, 62); // Adjust size as needed
    
    private JPanel buttonContainer;
    private List<ImageButton> plantButtons;

    public GamePanel() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        createButtonContainer();
        createPlantButtons();
        layoutComponents();
    }

    private void createButtonContainer() {
        buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage containerBg = ImageIO.read(new File(CONTAINER_IMAGE_PATH));
                    g.drawImage(containerBg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    System.err.println("Failed to load container background: " + e.getMessage());
                }
            }
        };
        buttonContainer.setOpaque(false);
        buttonContainer.setPreferredSize(CONTAINER_SIZE);
        buttonContainer.setBounds(10, 10, CONTAINER_SIZE.width, CONTAINER_SIZE.height); // Position at top-left
    }

    private void createPlantButtons() {
        plantButtons = new ArrayList<>();
        
        // Add your plant buttons - example with 5 plants
        String[] plantTypes = {"Peashooter", "Sunflower", "Cherry", "Walnut", "Potato"};
        
        for (String plantType : plantTypes) {
            ImageButton plantButton = new ImageButton(
                "assets/plants/" + plantType + ".png", 
                plantType.toLowerCase(), 
                60, // button width
                60  // button height
            );
            plantButtons.add(plantButton);
        }
    }

    private void layoutComponents() {
        // Add all plant buttons to the container
        for (ImageButton button : plantButtons) {
            buttonContainer.add(button);
        }
        
        // Add container to the panel
        add(buttonContainer);
    }

    public void setActionListener(ActionListener listener) {
        for (ImageButton button : plantButtons) {
            button.addActionListener(listener);
        }
    }
}