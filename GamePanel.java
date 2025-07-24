import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends BackgroundPanel {
    private static final String BACKGROUND_PATH = "assets/background/GamePanel.png";
    private static final String CONTAINER_IMAGE_PATH = "assets/ui/SeedSlot.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    private static final Dimension CONTAINER_SIZE = new Dimension(390, 80);

    private JPanel seedSlot;
    private BufferedImage seedSlotImage;
    private JLabel sunPointsLabel;
    private ArrayList<ImageButton> plantButtons;
    private ImageButton shovelButton;


    public GamePanel() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        setLayout(null);
        initializeComponents();
    }

    private void initializeComponents() {
        loadContainerImage();
        createButtonContainer();
        createPlantButtons();
        initializeShovelButton();
        initializeSunPointsDisplay();
        layoutComponents();
    }

    private void loadContainerImage() {
        try {
            seedSlotImage = ImageIO.read(new File(CONTAINER_IMAGE_PATH));
        } catch (IOException e) {
            System.err.println("Failed to load container background: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (seedSlotImage != null) {
            g2d.drawImage(seedSlotImage, 10, 0, CONTAINER_SIZE.width, CONTAINER_SIZE.height, null);
        }
    }

    private void createButtonContainer() {
        seedSlot = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        seedSlot.setOpaque(false);
        seedSlot.setBounds(77, 5, CONTAINER_SIZE.width, CONTAINER_SIZE.height);
    }

    private void createPlantButtons() {
        plantButtons = new ArrayList<>();
        String[] plantTypes = {"Sunflower", "Peashooter" };
                //"CherryBomb", "Wallnut", "PotatoMine", "SnowPea"};

        for (String plantType : plantTypes) {
            ImageButton plantButton = new ImageButton("assets/packets/" + plantType + ".png",
                plantType.toLowerCase(), 50, 60 );
            plantButtons.add(plantButton);
        }
    }

    private void initializeShovelButton() {
        shovelButton = new ImageButton("assets/button/Shovel.png", "shovel", 70, 72);
        shovelButton.setBounds(400, 0, shovelButton.getPreferredSize().width, shovelButton.getPreferredSize().height);
    }

    private void initializeSunPointsDisplay() {
        sunPointsLabel = new JLabel("0");
        sunPointsLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
        sunPointsLabel.setForeground(Color.BLACK);
        sunPointsLabel.setBounds(26, 50, 150, 30);
    }


    public void updateSunPoints(int points) {
        sunPointsLabel.setText("" + points);
    }

    private void layoutComponents() {
        for (ImageButton button : plantButtons) { seedSlot.add(button); }
        add(seedSlot);
        add(shovelButton);
        add(sunPointsLabel);
    }

    public void setActionListener(ActionListener listener) {
        for (ImageButton button : plantButtons) {
            button.addActionListener(listener);
        }
        shovelButton.addActionListener(listener);
    }
}