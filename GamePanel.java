import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends BackgroundPanel implements MouseListener, MouseMotionListener {
    private static final String BACKGROUND_PATH = "assets/background/GamePanel.png";
    private static final String CONTAINER_IMAGE_PATH = "assets/ui/SeedSlot.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    private static final Dimension CONTAINER_SIZE = new Dimension(390, 80);

    private JPanel seedSlot;
    private BufferedImage seedSlotImage;
    private JLabel sunPointsLabel;
    private ArrayList<JLabel> plantLabels;
    private JLabel shovelLabel;
    private JLabel selectedLabel;
    private BufferedImage draggedImage;
    private Map<JLabel, String> plantTypes;

    private boolean dragging = false;
    private Point draggedPosition = new Point();
    private Point dragOffset = new Point();
    private final int gridStartX = 70, gridStartY = 70, cellWidth = 65, cellHeight = 79;
    private final int gridCols = 9, gridRows = 5;
    private GameController controller;

    // In your GamePanel or wherever you manage the game board
    private Map<Point, JLabel> plantsOnBoard = new HashMap<>();

    public GamePanel() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        setLayout(null);
        initializeComponents();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void initializeComponents() {
        loadContainerImage();
        createSeedSlotContainer();
        createPlantLabels();
        initializeShovelLabel();
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

    private void createSeedSlotContainer() {
        seedSlot = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        seedSlot.setOpaque(false);
        seedSlot.setBounds(77, 5, CONTAINER_SIZE.width, CONTAINER_SIZE.height);
    }

    private void createPlantLabels() {
        plantLabels = new ArrayList<>();
        plantTypes = new HashMap<>();
        String[] plants = {"sunflower", "peashooter"};

        for (String plantType : plants) {
            JLabel label = new JLabel();
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/packets/" + plantType + ".png")));
                Image scaled = icon.getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
            } catch (IOException e) {
                System.err.println("Failed to load plant image: " + e.getMessage());
            }
            label.setPreferredSize(new Dimension(50, 60));
            plantLabels.add(label);
            plantTypes.put(label, plantType.toLowerCase());
        }
    }

    private void initializeShovelLabel() {
        shovelLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/button/Shovel.png")));
            Image scaled = icon.getImage().getScaledInstance(70, 72, Image.SCALE_SMOOTH);
            shovelLabel.setIcon(new ImageIcon(scaled));
        } catch (IOException e) {
            System.err.println("Failed to load shovel image: " + e.getMessage());
        }
        shovelLabel.setBounds(400, 0, 70, 72);
    }

    private void initializeSunPointsDisplay() {
        sunPointsLabel = new JLabel("0");
        sunPointsLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
        sunPointsLabel.setForeground(Color.BLACK);
        sunPointsLabel.setBounds(26, 50, 150, 30);
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

    private Point snapToGrid(int x, int y) {
        int col = (x - gridStartX) / cellWidth;
        int row = (y - gridStartY) / cellHeight;
        if (row >= 0 && row < gridRows && col >= 0 && col < gridCols) {
            return new Point(col, row);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (seedSlotImage != null) {
            g2d.drawImage(seedSlotImage, 10, 0, CONTAINER_SIZE.width, CONTAINER_SIZE.height, null);
        }

        g.setColor(new Color(0, 0, 0, 50));
        for (int i = 0; i <= gridCols; i++) {
            g.drawLine(gridStartX + i * cellWidth, gridStartY,
                      gridStartX + i * cellWidth, gridStartY + gridRows * cellHeight);
        }
        for (int j = 0; j <= gridRows; j++) {
            g.drawLine(gridStartX, gridStartY + j * cellHeight,
                      gridStartX + gridCols * cellWidth, gridStartY + j * cellHeight);
        }

        // Draw dragged plant
        if (dragging && draggedImage != null) {
            g.drawImage(draggedImage, draggedPosition.x - dragOffset.x,
                       draggedPosition.y - dragOffset.y, 60, 60, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Component comp = SwingUtilities.getDeepestComponentAt(this, p.x, p.y);

        if (comp instanceof JLabel && plantLabels.contains(comp)) {
            selectedLabel = (JLabel) comp;
            dragging = true;

            Point compPos = SwingUtilities.convertPoint(selectedLabel, 0, 0, this);
            dragOffset = new Point(p.x - compPos.x, p.y - compPos.y);
            draggedPosition = e.getPoint();

            String plantType = plantTypes.get(selectedLabel);
            if (plantType != null) {
                ImageIcon originalImage = new ImageIcon("assets/plants/" + plantType + ".png");
                Image scaledImage = originalImage.getImage().getScaledInstance(
                    cellWidth - 10,
                    cellHeight - 10,
                    Image.SCALE_SMOOTH
                );

                // Create semi-transparent dragged image
                draggedImage = new BufferedImage(cellWidth, cellHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = draggedImage.createGraphics();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
                g2d.drawImage(scaledImage, 5, 5, null);  // 5 pixel padding
                g2d.dispose();
            }
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            draggedPosition.setLocation(e.getPoint());
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging || selectedLabel == null) return;

        Point gridPos = snapToGrid(e.getX(), e.getY());
        if (gridPos != null && controller != null) {
            String plantType = plantTypes.get(selectedLabel);
            if (plantType != null) {
                controller.placePlantOnBoard(plantType, gridPos.y, gridPos.x);
            }
        }

        dragging = false;
        draggedImage = null;
        selectedLabel = null;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}

    public void placePlant(String plantType, int row, int col) {
        ImageIcon image = new ImageIcon("assets/plants/" + plantType + ".png");
        Image scaledImage = image.getImage().getScaledInstance(
            cellWidth - 10,
            cellHeight - 10,
            Image.SCALE_SMOOTH
        );

        JLabel plantLabel = new JLabel(new ImageIcon(scaledImage));
        int x = gridStartX + (col * cellWidth);
        int y = gridStartY + (row * cellHeight);
        plantLabel.setBounds(x, y, cellWidth, cellHeight);

        plantsOnBoard.put(new Point(col, row), plantLabel);
        add(plantLabel);
        revalidate();
        repaint();
    }

    public void removePlant(int row, int col) {
        Point pos = new Point(col, row);
        JLabel existingPlant = plantsOnBoard.remove(pos);
        if (existingPlant != null) {
            remove(existingPlant);
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

}