import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
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
    public static final int CELL_HEIGHT = 79;
    public static final int GRID_COLS = 9;
    public static final int GRID_ROWS = 5;

    private static final String[] PLANT_TYPES = {"sunflower", "peashooter", "cherrybomb"}; // add cherrybomb

    private JPanel seedSlot;
    private BufferedImage seedSlotImage;
    private JLabel sunPointsLabel;
    private ArrayList<JLabel> seedPackets;
    private JLabel shovelLabel;
    private JLabel menuLabel;

    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Pea> peas = new ArrayList<>();
    private ArrayList<Sun> suns = new ArrayList<>();
    private final String[][] plantGrid = new String[5][9];

    public GameView() {
        super(BACKGROUND_PATH, PANEL_SIZE);
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
        createSeedPackets();
        createShovelLabel();
        createMenuLabel();
        createSunPointsDisplay();

        // DEBUGGING
        // ITEM.setBorder(new LineBorder(Color.RED, 2));

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

    private void createMenuLabel() {
        menuLabel = new JLabel();
        menuLabel.setName("menu");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/button/menu.png")));
            Image scaled = icon.getImage().getScaledInstance(80, 25, Image.SCALE_SMOOTH);
            menuLabel.setIcon(new ImageIcon(scaled));
            menuLabel.setBounds(580, 0, 80, 25);
        } catch (IOException e) {
            System.err.println("Failed to load menu image: " + e.getMessage());
        }
    }

    private void createSunPointsDisplay() {
        sunPointsLabel = new JLabel("0");
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
        add(menuLabel);
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
}