import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder; // DEBUGGER FOR SIZES
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameView extends BackgroundPanel {
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

    private static final String[] PLANT_TYPES = {"sunflower", "peashooter"};

    private JPanel seedSlot;
    private BufferedImage seedSlotImage;
    private JLabel sunPointsLabel;
    private ArrayList<JLabel> plantLabels;
    private JLabel shovelLabel;

    private GameViewListener listener;
    private final Map<Point, JLabel> plantsOnBoard = new HashMap<>();

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
        createPlantLabels();
        createShovelLabel();
        createSunPointsDisplay();

        // DEBUGGING
        // ITEM.setBorder(new LineBorder(Color.RED, 2));

    }

    private void createSeedSlotContainer() {
        seedSlot = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        seedSlot.setOpaque(false);
        seedSlot.setBounds(77, 5, CONTAINER_SIZE.width - 80, CONTAINER_SIZE.height - 15);
    }

    private void createPlantLabels() {
        plantLabels = new ArrayList<>();

        for (String plantType : PLANT_TYPES) {
            JLabel label = createPlantLabel(plantType);
            if (label != null) {
                label.setName(plantType.toLowerCase());
                plantLabels.add(label);
            }
        }
    }

    private JLabel createPlantLabel(String plantType) {
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
            shovelLabel.setOpaque(true);
            shovelLabel.setPreferredSize(new Dimension(70, 70));
        } catch (IOException e) {
            System.err.println("Failed to load shovel image: " + e.getMessage());
        }
    }

    private void createSunPointsDisplay() {
        sunPointsLabel = new JLabel("0");
        sunPointsLabel.setFont(new Font("DialogInput", Font.BOLD, 18));
        sunPointsLabel.setForeground(Color.BLACK);
        sunPointsLabel.setBounds(20, 52, 47, 30);
        sunPointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    private void layoutComponents() {
        for (JLabel label : plantLabels) {
            seedSlot.add(label);
        }

        add(seedSlot);
        add(shovelLabel);
        add(sunPointsLabel);
    }

    public void updateSunPoints(int points) {
        sunPointsLabel.setText(String.valueOf(points));
    }

    public Point snapToGrid(int x, int y) {
        int col = (x - GRID_START_X) / CELL_WIDTH;
        int row = (y - GRID_START_Y) / CELL_HEIGHT;

        if (isValidGridPosition(row, col)) {
            return new Point(col, row);
        }
        return null;
    }

    private boolean isValidGridPosition(int row, int col) {
        return row >= 0 && row < GRID_ROWS && col >= 0 && col < GRID_COLS;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawSeedSlotBackground(g2d);
        drawDraggedPlant(g2d);
    }

    private void drawSeedSlotBackground(Graphics2D g2d) {
        if (seedSlotImage != null) {
            g2d.drawImage(seedSlotImage, 10, 0, CONTAINER_SIZE.width, CONTAINER_SIZE.height, null);
        }
    }

    private void drawDraggedPlant(Graphics g2d) {
        if (listener != null && listener.isDragging() && listener.getDraggedImage() != null) {
            Point draggedPos = listener.getDraggedPosition();
            Point dragOffset = listener.getDragOffset();
            g2d.drawImage(listener.getDraggedImage(),
                    draggedPos.x - dragOffset.x,
                    draggedPos.y - dragOffset.y, null);
        }
    }

    public void placePlant(String plantType, int row, int col) {
        JLabel plantLabel = createBoardPlantLabel(plantType, row, col);
        if (plantLabel != null) {
            plantsOnBoard.put(new Point(col, row), plantLabel);
            add(plantLabel);
            revalidate();
            repaint();
        }
    }

    private JLabel createBoardPlantLabel(String plantType, int row, int col) {
        try {
            ImageIcon image = new ImageIcon("assets/plants/" + plantType + ".png");
            Image scaledImage = image.getImage().getScaledInstance(
                    CELL_WIDTH - 10, CELL_HEIGHT - 10, Image.SCALE_SMOOTH);

            JLabel plantLabel = new JLabel(new ImageIcon(scaledImage));
            int x = GRID_START_X + (col * CELL_WIDTH);
            int y = GRID_START_Y + (row * CELL_HEIGHT);
            plantLabel.setBounds(x, y, CELL_WIDTH, CELL_HEIGHT);

            return plantLabel;
        } catch (Exception e) {
            System.err.println("Failed to create board plant label for " + plantType + ": " + e.getMessage());
            return null;
        }
    }

    public void removePlant(int row, int col) {
        Point position = new Point(col, row);
        JLabel existingPlant = plantsOnBoard.remove(position);
        if (existingPlant != null) {
            remove(existingPlant);
            revalidate();
            repaint();
        }
    }

    public void clearBoard() {
        for (JLabel plant : plantsOnBoard.values()) {
            remove(plant);
        }
        plantsOnBoard.clear();
        revalidate();
        repaint();
    }

    public ArrayList<JLabel> getPlantLabels() {
        return plantLabels;
    }

    public JLabel getShovelLabel() {
        return shovelLabel;
    }
}