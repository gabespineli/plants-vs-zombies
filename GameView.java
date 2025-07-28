import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameView extends BackgroundPanel {
    private GameViewListener listener;

    private static final String BACKGROUND_PATH = "assets/background/GamePanel.png";
    private static final String CONTAINER_IMAGE_PATH = "assets/ui/SeedSlot.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    private static final Dimension CONTAINER_SIZE = new Dimension(390, 80);

    public static final int GRID_START_X = 70;
    public static final int GRID_START_Y = 70;
    public static final int CELL_WIDTH = 65;
    public static final int CELL_HEIGHT = 80;
    public static final int GRID_COLS = 9;
    public static final int GRID_ROWS = 5;

    private static final String[] PLANT_TYPES = {"sunflower", "peashooter", "cherrybomb"}; // add cherrybomb

    private JPanel seedSlot;
    private BufferedImage seedSlotImage;
    private JLabel sunPointsLabel;
    private ArrayList<JLabel> seedPackets;
    private JLabel shovelLabel;
    private ImageButton menuButton;
    private JLabel settingsLabel;
    private ImageButton restartLevelButton;
    private ImageButton mainMenuButton;
    private JButton backSettingsButton;
    private JLabel level;
    private JLabel levelNumber;

    private int readySetPlantPhase;
    private ArrayList<Zombie> zombies;
    private ArrayList<Pea> peas;
    private ArrayList<Sun> suns;
    private final String[][] plantGrid;

    public GameView() {
        super(BACKGROUND_PATH, PANEL_SIZE);

        readySetPlantPhase = 0;
        zombies = new ArrayList<>();
        peas = new ArrayList<>();
        suns = new ArrayList<>();
        plantGrid = new String[GRID_ROWS][GRID_COLS];

        initializePanel();
    }

    public void setGameViewListener(GameViewListener listener) {
        this.listener = listener;
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private void initializePanel() {
        setLayout(null);
        loadImages();
        createComponents();
        layoutComponents();
    }

    private void loadImages() {
        try {
            seedSlotImage = ImageIO.read(new File(CONTAINER_IMAGE_PATH));
        } catch (IOException e) {
            System.err.println("Failed to load container background: " + e.getMessage());
        }
    }

    private void createComponents() {
        createSeedSlotContainer();
        createSunPointsDisplay();
        createSeedPackets();
        createShovelLabel();
        createMenuButton();
        createSettingsLabel();
        createRestartLevelButton();
        createMainMenuButton();
        createBackSettingsButton();
        createLevelLabel();

        // DEBUGGING
        // backSettingsButton.setBorder(new LineBorder(Color.RED, 2));

    }

    private void createSeedSlotContainer() {
        seedSlot = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        seedSlot.setOpaque(false);
        seedSlot.setBounds(77, 5, CONTAINER_SIZE.width - 80, CONTAINER_SIZE.height - 15);
    }

    private void createSeedPackets() {
        seedPackets = new ArrayList<>();

        for (String plantType : PLANT_TYPES) {
            JLabel label = createSeedPacket(plantType);
            if (label != null) {
                label.setName(plantType.toLowerCase());
                seedPackets.add(label);
            }
        }
    }

    private JLabel createSeedPacket(String plantType) {
        JLabel label = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/packets/" + plantType + ".png")));
            Image scaled = icon.getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            label.setPreferredSize(new Dimension(50, 60));
            return label;
        } catch (IOException e) {
            System.err.println("Failed to load plant image for " + plantType + ": " + e.getMessage());
            return null;
        }
    }

    private void createShovelLabel() {
        shovelLabel = new JLabel();
        shovelLabel.setName("shovel");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/button/shovel.png")));
            Image scaled = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            shovelLabel.setIcon(new ImageIcon(scaled));
            shovelLabel.setBounds(400, 0, 70, 70);
        } catch (IOException e) {
            System.err.println("Failed to load shovel image: " + e.getMessage());
        }
    }

    private void createMenuButton() {
        menuButton = new ImageButton("assets/button/menu.png", "menu", 80, 25);
        menuButton.setBounds(580, 0, menuButton.getPreferredSize().width, menuButton.getPreferredSize().height);
    }

    private void createSettingsLabel() {
        settingsLabel = new JLabel();
        settingsLabel.setName("settings");
        int x = 670;
        int y = 503;
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/settings.png")));
            Image scaled = icon.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
            settingsLabel.setIcon(new ImageIcon(scaled));
            settingsLabel.setBounds(0, 0, x, y);
        } catch (IOException e) {
            System.err.println("Failed to load settings image: " + e.getMessage());
        }
        settingsLabel.setVisible(false);
    }

    private void createRestartLevelButton() {
        restartLevelButton = new ImageButton("assets/button/restartlevel.png", "restart", 230, 60);
        restartLevelButton.setBounds(220, 180, restartLevelButton.getPreferredSize().width, restartLevelButton.getPreferredSize().height);
    }

    private void createMainMenuButton() {
        mainMenuButton = new ImageButton("assets/button/mainmenu.png", "main", 180, 40);
        mainMenuButton.setBounds(250, 250, mainMenuButton.getPreferredSize().width, mainMenuButton.getPreferredSize().height);
    }

    private void createBackSettingsButton() {
        backSettingsButton = new JButton();
        backSettingsButton.setActionCommand("back");
        backSettingsButton.setBounds(235, 360, 260, 65);
        backSettingsButton.setOpaque(false);
        backSettingsButton.setBorderPainted(false);
        backSettingsButton.setContentAreaFilled(false);
        backSettingsButton.setVisible(false);
    }

    private void createLevelLabel() {
        level = new JLabel();
        level.setName("level");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/level.png")));
            Image scaled = icon.getImage().getScaledInstance(60, 20, Image.SCALE_SMOOTH);
            level.setIcon(new ImageIcon(scaled));
            level.setBounds(560, 480, 60, 20);
        } catch (IOException e) {
            System.err.println("Failed to load level image: " + e.getMessage());
        }

        levelNumber = new JLabel("HELLO");
        levelNumber.setName("number");

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/" + 1 + ".png")));
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            levelNumber.setIcon(new ImageIcon(scaled));
            levelNumber.setBounds(620, 480, 20, 20);
        } catch (IOException e) {
            System.err.println("Failed to load level number image: " + e.getMessage());
        }
    }

    private void createSunPointsDisplay() {
        sunPointsLabel = new JLabel("50");
        sunPointsLabel.setFont(new Font("DialogInput", Font.BOLD, 18));
        sunPointsLabel.setForeground(Color.BLACK);
        sunPointsLabel.setBounds(20, 52, 47, 30);
        sunPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void layoutComponents() {
        for (JLabel label : seedPackets) {
            seedSlot.add(label);
        }

        add(seedSlot);
        add(shovelLabel);
        add(sunPointsLabel);
        add(menuButton);
        add(settingsLabel);
        add(restartLevelButton);
        add(mainMenuButton);
        add(backSettingsButton);
        add(level);
        add(levelNumber);

        setComponentZOrder(restartLevelButton, 0);
        setComponentZOrder(mainMenuButton, 0);
        setComponentZOrder(backSettingsButton, 0);

        setSettingsVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawSeedSlotBackground(g2d);
        drawZombies(g2d);
        drawPeas(g2d);
        drawPlantedPlants(g2d);
        drawSuns(g2d);
        drawDraggedPlant(g2d);

        if (readySetPlantPhase > 0 && readySetPlantPhase <= 3) {
            drawReadySetPlant(g2d, readySetPlantPhase);
        }
    }

    private void drawSeedSlotBackground(Graphics2D g2d) {
        if (seedSlotImage != null) {
            g2d.drawImage(seedSlotImage, 10, 0, CONTAINER_SIZE.width, CONTAINER_SIZE.height, null);
        }
    }

    private void drawZombies(Graphics2D g2d) {
        for (Zombie zombie : zombies) {
            int x = (int)(GRID_START_X + zombie.getColumnPos() * CELL_WIDTH + 10);
            int y = GRID_START_Y + zombie.getRowPos() * CELL_HEIGHT - 10;

            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/zombies/zombie.png")));
                Image scaled = icon.getImage().getScaledInstance(65, 90, Image.SCALE_SMOOTH);
                g2d.drawImage(scaled, x, y, null);

                if (zombie.hasArmor()){
                    String armor = zombie.getArmor().getArmorType();
                    icon = new ImageIcon(ImageIO.read(new File("assets/zombies/" + armor + ".png")));
                    scaled = icon.getImage().getScaledInstance(20, 23, Image.SCALE_SMOOTH);
                    g2d.drawImage(scaled, x, y + 10, null);
                }

            } catch (Exception e) {
                System.err.println("Could not draw zombie: " + e.getMessage());
            }
        }
    }



    private void drawPeas(Graphics2D g2d) {
        for (Pea pea : peas) {
            int x = (int)(GRID_START_X + pea.getColumnPos() * CELL_WIDTH + 15);
            int y = GRID_START_Y + pea.getRowPos() * CELL_HEIGHT + 10;

            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/pea.png")));
                Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                g2d.drawImage(scaled, x, y, null);
            } catch (Exception e) {
                System.err.println("Could not draw pea: " + e.getMessage());
            }
        }
    }

    private void drawPlantedPlants(Graphics2D g2d) {
        for (int row = 0; row < plantGrid.length; row++) {
            for (int col = 0; col < plantGrid[0].length; col++) {
                String plantType = plantGrid[row][col];
                if (plantType != null) {
                    int x = GRID_START_X + col * CELL_WIDTH;
                    int y = GRID_START_Y + row * CELL_HEIGHT + 15;

                    try {
                        ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/plants/" + plantType + ".png")));
                        Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        g2d.drawImage(scaled, x, y, null);
                    } catch (Exception e) {
                        System.err.println("Could not draw sun: " + e.getMessage());
                    }
                }
            }
        }
    }

    private void drawDraggedPlant(Graphics g2d) {
        if (listener != null && listener.isDragging() && listener.getDraggedImage() != null) {
            Point draggedPos = listener.getDraggedPosition();
            Point dragOffset = listener.getDragOffset();
            Image original = listener.getDraggedImage();
            Image scaled = original.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

            g2d.drawImage(scaled,
                    draggedPos.x - dragOffset.x,
                    draggedPos.y - dragOffset.y,
                    null);
        }
    }

    private void drawSuns(Graphics2D g2d) {
        for (Sun sun : suns) {
            int x = (int)(GRID_START_X + sun.getColumnPos() * CELL_WIDTH);
            int y = (int)(GRID_START_Y + sun.getRowPos() * CELL_HEIGHT);

            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/sun.png")));
                Image scaled = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                g2d.drawImage(scaled, x, y, null);
            } catch (Exception e) {
                System.err.println("Could not draw sun: " + e.getMessage());
            }
        }
    }

    private void drawReadySetPlant(Graphics2D g2d, int phase) {
        try {
            BufferedImage img = ImageIO.read(new File("assets/ui/ready" + phase + ".png"));

            double scale = 0.4 + ((phase - 1) * 0.3);
            int width = (int)(img.getWidth() * scale);
            int height = (int)(img.getHeight() * scale);
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;

            g2d.drawImage(img, x, y, width, height, null);
        } catch (IOException e) {
            System.err.println("Error loading ready-set-plant image: " + e.getMessage());
        }
    }

    public void setReadySetPlantPhase(int phase) {
        this.readySetPlantPhase = phase;
        repaint();
    }

    public void clearReadySetPlant() {
        this.readySetPlantPhase = 0;
        repaint();
    }

    public void setSettingsVisible(boolean visible) {
        settingsLabel.setVisible(visible);
        restartLevelButton.setVisible(visible);
        mainMenuButton.setVisible(visible);
        backSettingsButton.setVisible(visible);
        repaint();
    }

    public void placePlant(String plantType, int row, int col) {
        plantGrid[row][col] = plantType;
        repaint();
    }

    public void removePlant(int row, int col) {
        plantGrid[row][col] = null;
        repaint();
    }

    public void clearBoard() {
        for (int row = 0; row < plantGrid.length; row++) {
            for (int col = 0; col < plantGrid[row].length; col++) {
                plantGrid[row][col] = null;
            }
        }
        repaint();
    }

    public ArrayList<JLabel> getSeedPackets() {
        return seedPackets;
    }

    public void updateSunPoints(int points) {
        sunPointsLabel.setText(String.valueOf(points));
    }

    public void updateZombies(ArrayList<Zombie> zombies) {
        this.zombies = zombies;
    }

    public void updatePeas(ArrayList<Pea> peas) {
        this.peas = peas;
    }

    public void updateSuns(ArrayList<Sun> suns) {
        this.suns = suns;
    }

    public void resetView() {
        clearBoard();

        updateSunPoints(50);

        zombies.clear();
        peas.clear();
        suns.clear();

        readySetPlantPhase = 0;
        repaint();
    }

    public void setActionListener(ActionListener listener) {
        menuButton.addActionListener(listener);
        restartLevelButton.addActionListener(listener);
        mainMenuButton.addActionListener(listener);
        backSettingsButton.addActionListener(listener);
    }
}