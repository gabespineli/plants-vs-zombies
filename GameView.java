import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * GameView represents the main visual interface for a Plants vs Zombies game.
 * This class extends BackgroundPanel and manages the rendering of all game elements including
 * plants, zombies, projectiles, UI components, and game state displays.
 * The view handles the display of the game grid, seed selection interface, sun points,
 * game menus, and various visual effects.
 */
public class GameView extends BackgroundPanel {
    private GameViewListener listener;

    private static final String BACKGROUND_PATH = "assets/background/GamePanel.png";
    private static final String CONTAINER_IMAGE_PATH = "assets/ui/SeedSlot.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    private static final Dimension CONTAINER_SIZE = new Dimension(400, 80);

    public static final int GRID_START_X = 70;
    public static final int GRID_START_Y = 70;
    public static final int CELL_WIDTH = 65;
    public static final int CELL_HEIGHT = 80;

    public static final int GRID_COLS = 9;
    public static final int GRID_ROWS = 5;

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
    private JLabel winningNote;
    private JLabel losingNote;
    private ImageButton nextLevelButton;

    private int displayLevel;
    private int displayReadySetPlantPhase;
    private ArrayList<Plant> displayPlants;
    private ArrayList<Zombie> displayZombies;
    private ArrayList<Pea> displayPeas;
    private ArrayList<Sun> displaySuns;
    private String[][] displayPlantGrid;

    private ArrayList<String> plantTypes;
    private ArrayList<BufferedImage> plantImages;

    // EXPLOSIVE PLANT IMAGES
    private BufferedImage cherryBombImage;
    private BufferedImage potatoMineDetonatedImage;
    private BufferedImage potatoMineUndetonatedImage;
    private BufferedImage explosionImage;

    // ZOMBIE IMAGES
    private BufferedImage zombieImage;
    private BufferedImage frozenZombieImage;
    private BufferedImage flagImage;
    private BufferedImage frozenFlagImage;
    private BufferedImage bucketImage;
    private BufferedImage frozenBucketImage;
    private BufferedImage coneImage;
    private BufferedImage frozenConeImage;
    
    // PROJECTILE IMAGES
    private BufferedImage peaImage;
    private BufferedImage frostImage;
    private BufferedImage sunImage;


    /**
     * Constructs a new GameView with default settings.
     * Initializes the game panel with the background image, sets up the game grid,
     * and prepares all UI components and display lists.
     */
    public GameView() {
        super(BACKGROUND_PATH, PANEL_SIZE);

        displayLevel = 1;
        displayReadySetPlantPhase = 0;
        displayZombies = new ArrayList<>();
        displayPeas = new ArrayList<>();
        displaySuns = new ArrayList<>();
        displayPlantGrid = new String[GRID_ROWS][GRID_COLS];
        plantImages = new ArrayList<>();
        plantTypes = new ArrayList<>();

        initializePanel();
    }

    /**
     * Sets the GameViewListener for handling user interactions.
     * Registers the listener for mouse events on this panel.
     * @param listener the GameViewListener to handle user interactions
     */
    public void setGameViewListener(GameViewListener listener) {
        this.listener = listener;
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    /**
     * Initializes the panel by setting up layout, loading images, and creating components.
     * This method orchestrates the setup of all visual elements.
     */
    private void initializePanel() {
        setLayout(null);
        loadImages();
        createComponents();
        layoutComponents();
    }

    /**
     * Loads all image assets required for the game including plants, zombies,
     * projectiles, UI elements, and special effects.
     * @throws IOException if any image file cannot be loaded
     */
    private void loadImages() {
        try {
            seedSlotImage = ImageIO.read(new File(CONTAINER_IMAGE_PATH));
            // EXPLOSIVE PLANTS
            cherryBombImage = loadAndScale("assets/plants/cherrybomb.png", 60, 60);
            potatoMineDetonatedImage = loadAndScale("assets/plants/potatomine.png", 50, 50);
            potatoMineUndetonatedImage = loadAndScale("assets/plants/potatomineunder.png", 30, 30);
            explosionImage = loadAndScale("assets/ui/explode.png", 130, 90);

            // ZOMBIES
            zombieImage = loadAndScale("assets/zombies/zombie.png", 65, 90);
            flagImage = loadAndScale("assets/zombies/flag.png", 80, 110);
            // ZOMBIES FROZEN STATE
            frozenZombieImage = loadAndScale("assets/zombies/frozenzombie.png", 65, 90);
            frozenFlagImage = loadAndScale("assets/zombies/frozenflag.png", 80, 110);

            // ARMORS
            bucketImage = loadAndScale("assets/zombies/bucket.png", 42, 44);
            coneImage = loadAndScale("assets/zombies/cone.png", 42, 44);
            // ARMORS FROZEN STATE
            frozenBucketImage = loadAndScale("assets/zombies/frozenbucket.png", 42, 44);
            frozenConeImage = loadAndScale("assets/zombies/frozencone.png", 42, 44);

            // PROJECTILES
            peaImage = loadAndScale("assets/ui/pea.png", 20, 20);
            frostImage = loadAndScale("assets/ui/frost.png", 20, 20);
            sunImage = loadAndScale("assets/ui/sun.png", 70, 70);
        } catch (IOException e) {
            System.err.println("Failed to load container background: " + e.getMessage());
        }
    }

    /**
     * Loads an image from the specified path and scales it to the given dimensions.
     * @param path the file path to the image
     * @param width the desired width of the scaled image
     * @param height the desired height of the scaled image
     * @return a BufferedImage scaled to the specified dimensions, or null if loading fails
     */
    public BufferedImage loadAndScale(String path, int width, int height) {
        try {
            Image img = ImageIO.read(new File(path))
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH);

            BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaled.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();

            return scaled;
        } catch (IOException e) {
            System.err.println("Failed to load or scale image: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates all UI components including seed slots, buttons, labels, and other interface elements.
     * This method initializes all visual components but does not add them to the panel.
     */
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
        createWinningNoteLabel();
        createLosingNoteLabel();
        createNextLevelButton();

        // DEBUGGING
        //seedSlot.setBorder(new LineBorder(Color.RED, 2));
    }

    /**
     * Creates the container panel that holds the seed packet selection interface.
     */
    private void createSeedSlotContainer() {
        seedSlot = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 5));
        seedSlot.setOpaque(false);
        seedSlot.setBounds(77, 5, CONTAINER_SIZE.width - 80, CONTAINER_SIZE.height - 15);
    }

    /**
     * Creates seed packet labels for all available plant types.
     * Each seed packet is created as a JLabel with the corresponding plant image.
     */
    private void createSeedPackets() {
        seedPackets = new ArrayList<>();

        for (String plantType : plantTypes) {
            JLabel label = createSeedPacket(plantType);
            if (label != null) {
                label.setName(plantType.toLowerCase());
                seedPackets.add(label);
            }
        }
    }

    /**
     * Creates a single seed packet label for the specified plant type.
     * @param plantType the type of plant to create a seed packet for
     * @return a JLabel representing the seed packet, or null if the image cannot be loaded
     */
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

    /**
     * Creates the shovel label used for removing plants from the game grid.
     */
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

    /**
     * Creates the menu button for accessing game settings.
     */
    private void createMenuButton() {
        menuButton = new ImageButton("assets/button/menu.png", "menu", 80, 25);
        menuButton.setBounds(580, 0, menuButton.getPreferredSize().width, menuButton.getPreferredSize().height);
    }

    /**
     * Creates the settings overlay label that covers the entire screen when settings are shown.
     */
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
    }

    /**
     * Creates the restart level button.
     */
    private void createRestartLevelButton() {
        restartLevelButton = new ImageButton("assets/button/restartlevel.png", "restart", 230, 60);
        restartLevelButton.setBounds(220, 180, restartLevelButton.getPreferredSize().width, restartLevelButton.getPreferredSize().height);
    }

    /**
     * Creates the main menu button.
     */
    private void createMainMenuButton() {
        mainMenuButton = new ImageButton("assets/button/mainmenu.png", "main", 180, 40);
        mainMenuButton.setBounds(250, 250, mainMenuButton.getPreferredSize().width, mainMenuButton.getPreferredSize().height);
    }

    /**
     * Creates the back button for returning from settings screen.
     */
    private void createBackSettingsButton() {
        backSettingsButton = new JButton();
        backSettingsButton.setActionCommand("back");
        backSettingsButton.setBounds(235, 360, 260, 65);
        backSettingsButton.setOpaque(false);
        backSettingsButton.setBorderPainted(false);
        backSettingsButton.setContentAreaFilled(false);
    }

    /**
     * Creates the level display labels showing the current level number.
     */
    public void createLevelLabel() {
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

        levelNumber = new JLabel();
        levelNumber.setName("number");

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/level" + displayLevel + ".png")));
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            levelNumber.setIcon(new ImageIcon(scaled));
            levelNumber.setBounds(620, 480, 20, 20);
        } catch (IOException e) {
            System.err.println("Failed to load level number image: " + e.getMessage());
        }
    }

    /**
     * Creates the sun points display label showing the current sun currency.
     */
    private void createSunPointsDisplay() {
        sunPointsLabel = new JLabel("50");
        sunPointsLabel.setFont(new Font("DialogInput", Font.BOLD, 18));
        sunPointsLabel.setForeground(Color.BLACK);
        sunPointsLabel.setBounds(20, 52, 47, 30);
        sunPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Creates the winning notification label displayed when the player wins a level.
     */
    private void createWinningNoteLabel() {
        winningNote = new JLabel();
        winningNote.setName("winning");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/winningnote.png")));
            Image scaled = icon.getImage().getScaledInstance(408, 296, Image.SCALE_SMOOTH);
            winningNote.setIcon(new ImageIcon(scaled));
            winningNote.setBounds(150, 90, 408, 296);
        } catch (IOException e) {
            System.err.println("Failed to load winning note image: " + e.getMessage());
        }
    }

    /**
     * Creates the losing notification label displayed when the player loses a level.
     */
    private void createLosingNoteLabel() {
        losingNote = new JLabel();
        losingNote.setName("losing");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/losingnote.png")));
            Image scaled = icon.getImage().getScaledInstance(380, 236, Image.SCALE_SMOOTH);
            losingNote.setIcon(new ImageIcon(scaled));
            losingNote.setBounds(150, 100, 380, 236);
        } catch (IOException e) {
            System.err.println("Failed to load winning note image: " + e.getMessage());
        }
    }

    /**
     * Creates the next level button displayed when the player wins a level.
     */
    private void createNextLevelButton() {
        nextLevelButton = new ImageButton("assets/button/nextlevel.png", "next", 180, 45);
        nextLevelButton.setBounds(270, 400, nextLevelButton.getPreferredSize().width, nextLevelButton.getPreferredSize().height);
    }

    /**
     * Adds all created components to the panel and sets their initial visibility and z-order.
     */
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
        add(winningNote);
        add(losingNote);
        add(nextLevelButton);

        setComponentZOrder(restartLevelButton, 0);
        setComponentZOrder(mainMenuButton, 0);
        setComponentZOrder(backSettingsButton, 0);

        setSettingsVisible(false);
        setLoseVisible(false);
        setWinVisible(false);
    }

    /**
     * Custom paint method that renders all game elements including plants, zombies,
     * projectiles, suns, and special effects onto the game panel.
     * @param g the Graphics context to paint on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawSeedSlotBackground(g2d);
        drawPeas(g2d);
        drawPlantedPlants(g2d);
        drawExplosivePlants(g2d);
        drawZombies(g2d);
        drawSuns(g2d);
        drawDraggedPlant(g2d);

        if (displayReadySetPlantPhase > 0 && displayReadySetPlantPhase <= 3) {
            drawReadySetPlant(g2d, displayReadySetPlantPhase);
        }
    }

    /**
     * Draws the seed slot container background image.
     * @param g2d the Graphics2D context to draw on
     */
    private void drawSeedSlotBackground(Graphics2D g2d) {
        if (seedSlotImage != null) {
            g2d.drawImage(seedSlotImage, 10, 0, CONTAINER_SIZE.width, CONTAINER_SIZE.height, null);
        }
    }

    /**
     * Draws all sun collectibles on the game field.
     * @param g2d the Graphics2D context to draw on
     */
    private void drawSuns(Graphics2D g2d) {
        for (Sun sun : displaySuns) {
            int x = (int)(GRID_START_X + sun.getColumnPos() * CELL_WIDTH);
            int y = (int)(GRID_START_Y + sun.getRowPos() * CELL_HEIGHT);
            g2d.drawImage(sunImage, x, y, null);
        }
    }

    /**
     * Draws all projectiles (peas and frost projectiles) on the game field.
     * @param g2d the Graphics2D context to draw on
     */
    private void drawPeas(Graphics2D g2d) {
        for (Pea pea : displayPeas) {
            int x = (int)(GRID_START_X + pea.getColumnPos() * CELL_WIDTH + 15);
            int y = GRID_START_Y + pea.getRowPos() * CELL_HEIGHT + 10;
            if (pea instanceof Frost) {
                g2d.drawImage(frostImage, x, y+5, null);
            } else {
                g2d.drawImage(peaImage, x, y, null);
            }
        }
    }

    /**
     * Draws all zombies on the game field, including their armor and frozen states.
     * @param g2d the Graphics2D context to draw on
     */
    private void drawZombies(Graphics2D g2d) {
        for (Zombie zombie : displayZombies) {
            int x = (int) (GRID_START_X + zombie.getColumnPos() * CELL_WIDTH - 30);
            int y = GRID_START_Y + zombie.getRowPos() * CELL_HEIGHT - 10;
            BufferedImage baseZombieImage = zombieImage;
            BufferedImage baseConeImage = coneImage;
            BufferedImage baseBucketImage = bucketImage;
            BufferedImage baseFlagImage = flagImage;

            if (zombie.isFrozen()) {
                baseZombieImage = frozenZombieImage;
                baseConeImage = frozenConeImage;
                baseBucketImage = frozenBucketImage;
                baseFlagImage = frozenFlagImage;
            }
            if (zombie.hasArmor()) {
                String armor = zombie.getArmor().getArmorType();
                switch (armor) {
                    case "Cone" -> {
                        g2d.drawImage(baseZombieImage, x, y, null);
                        g2d.drawImage(baseConeImage, x, y - 20, null);
                    }
                    case "Bucket" -> {
                        g2d.drawImage(baseZombieImage, x, y, null);
                        g2d.drawImage(baseBucketImage, x, y - 20, null);
                    }
                    case "Flag" -> {
                        g2d.drawImage(baseFlagImage, x, y - 25, null);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + armor);
                }
            } else {
                g2d.drawImage(baseZombieImage, x, y, null);
            }
        }
    }

    /**
     * Draws all non-explosive plants placed on the game grid.
     * @param g2d the Graphics2D context to draw on
     */
    private void drawPlantedPlants(Graphics2D g2d) {
        for (int row = 0; row < displayPlantGrid.length; row++) {
            for (int col = 0; col < displayPlantGrid[0].length; col++) {
                String plantType = displayPlantGrid[row][col];
                if (plantType != null && !plantType.equalsIgnoreCase("cherrybomb") && !plantType.equalsIgnoreCase("potatomine")) {
                    int index = plantTypes.indexOf(plantType.toLowerCase());
                    if (index != -1) {
                        BufferedImage image = plantImages.get(index);
                        int x = GRID_START_X + col * CELL_WIDTH;
                        int y = GRID_START_Y + row * CELL_HEIGHT + 15;
                        g2d.drawImage(image, x, y, null);
                    } else {
                        System.err.println("Unknown plant type: " + plantType);
                    }
                }
            }
        }
    }

    /**
     * Draws explosive plants (Cherry Bombs and Potato Mines) with their various states
     * including explosions and detonation status.
     * @param g2d the Graphics2D context to draw on
     */
    private void drawExplosivePlants(Graphics2D g2d) {
        if (displayPlants == null) return;
        for (Plant plant : displayPlants) {
            int x = GRID_START_X + (int)plant.getColumnPos() * CELL_WIDTH;
            int y = GRID_START_Y + plant.getRowPos() * CELL_HEIGHT + 15;
            if (plant instanceof CherryBomb cb) {
                if (!cb.isAlive()) {
                    g2d.drawImage(explosionImage, x-10, y-10, null);
                } else {
                    g2d.drawImage(cherryBombImage, x, y, null);
                }
            } else if (plant instanceof PotatoMine pm) {
                if (!pm.isAlive()) {
                    g2d.drawImage(explosionImage, x-50, y-50, null);
                } else if (pm.isDetonated()) {
                    g2d.drawImage(potatoMineDetonatedImage, x+10, y+10, null);
                } else {
                    g2d.drawImage(potatoMineUndetonatedImage, x+15, y+15, null);
                }
            }
        }
    }

    /**
     * Draws the plant that is currently being dragged by the player.
     * @param g2d the Graphics context to draw on
     */
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

    /**
     * Draws the "Ready, Set, Plant!" countdown animation at the start of each level.
     * @param g2d the Graphics2D context to draw on
     * @param phase the current phase of the countdown (1-3)
     */
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

    /**
     * Controls the visibility of the settings screen and related buttons.
     * @param visible true to show the settings screen, false to hide it
     */
    public void setSettingsVisible(boolean visible) {
        settingsLabel.setVisible(visible);
        mainMenuButton.setVisible(visible);
        backSettingsButton.setVisible(visible);

        restartLevelButton.setVisible(visible);
        restartLevelButton.setBounds(220, 180, restartLevelButton.getPreferredSize().width,
                restartLevelButton.getPreferredSize().height);
        repaint();
    }

    /**
     * Controls the visibility of the game over (lose) screen.
     * @param show true to show the lose screen, false to hide it
     */
    public void setLoseVisible(boolean show) {
        losingNote.setVisible(show);

        restartLevelButton.setVisible(show);
        restartLevelButton.setBounds(220, 350, restartLevelButton.getPreferredSize().width,
                restartLevelButton.getPreferredSize().height);
        repaint();
    }

    /**
     * Controls the visibility of the level complete (win) screen.
     * @param show true to show the win screen, false to hide it
     */
    public void setWinVisible(boolean show) {
        winningNote.setVisible(show);
        nextLevelButton.setVisible(show);
        repaint();
    }

    /**
     * Clears all plants from the game board grid.
     */
    public void clearBoard() {
        for (int row = 0; row < displayPlantGrid.length; row++) {
            for (int col = 0; col < displayPlantGrid[row].length; col++) {
                displayPlantGrid[row][col] = null;
            }
        }
        repaint();
    }

    /**
     * Gets the list of seed packet labels for plant selection.
     * @return ArrayList of JLabel objects representing seed packets
     */
    public ArrayList<JLabel> getSeedPackets() {
        return seedPackets;
    }

    // DISPLAY DATA METHODS
    /**
     * Updates the display to show the current phase of the "Ready, Set, Plant!" countdown.
     * @param phase the countdown phase to display (1-3)
     */
    public void displayReadySetPlantPhase(int phase) {
        this.displayReadySetPlantPhase = phase;
        repaint();
    }
    /**
     * Updates the level display to show the specified level number.
     * @param level the level number to display
     */
    public void displayLevel(int level) {
        this.displayLevel = level;

        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/ui/level" + level + ".png")));
            Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            levelNumber.setIcon(new ImageIcon(scaled));
        } catch (IOException e) {
            System.err.println("Failed to load level number image: " + e.getMessage());
        }

        repaint();
    }

    /**
     * Updates the sun points display with the current sun currency value.
     * @param points the number of sun points to display
     */
    public void displaySunPoints(int points) {
        sunPointsLabel.setText(String.valueOf(points));
    }

    /**
     * Removes a plant from the specified grid position.
     * @param row the row index of the plant to remove
     * @param col the column index of the plant to remove
     */
    public void removePlant(int row, int col) {
        displayPlantGrid[row][col] = null;
        repaint();
    }

    /**
     * Updates the available seed packets with the specified plant types.
     * Refreshes the seed selection interface with new plant options.
     * @param types ArrayList of plant type names to create seed packets for
     */
    public void displaySeedPackets(ArrayList<String> types) {
        this.plantTypes = types;
        plantImages.clear();
        for (String plantType : plantTypes) {
            BufferedImage img = loadAndScale("assets/plants/" + plantType + ".png", 60, 60);
            if (img != null) {
                plantImages.add(img);
            } else {
                System.err.println("Failed to load image for plant type: " + plantType);
            }
        }
        refreshSeedPackets();
    }


    /**
     * Refreshes the seed packet display by clearing and recreating all seed packet labels.
     * This method is called when the available plant types change or need to be updated.
     */
    private void refreshSeedPackets() {
        seedSlot.removeAll();
        seedPackets.clear();
        createSeedPackets();
        for (JLabel label : seedPackets) {
            seedSlot.add(label);
        }
        seedSlot.revalidate();
        seedSlot.repaint();
    }

    /**
     * Updates the display with the current list of plants in the game.
     * This includes both regular plants and explosive plants (Cherry Bombs, Potato Mines).
     *
     * @param plants The list of Plant objects to display
     */
    public void displayPlants(ArrayList<Plant> plants) {
        this.displayPlants = plants;
        repaint();
    }

    /**
     * Updates a specific grid cell with a plant of the given type.
     * @param plantType The type of plant to display (e.g., "peashooter")
     * @param row The row index of the grid cell (0-based)
     * @param col The column index of the grid cell (0-based)
     */
    public void displayPlantGrid(String plantType, int row, int col) {
        displayPlantGrid[row][col] = plantType;
        repaint();
    }

    /**
     * Updates the display with the current list of zombies in the game.
     * @param zombies The list of Zombie objects to display
     */
    public void displayZombies(ArrayList<Zombie> zombies) {
        this.displayZombies = zombies;
        repaint();
    }

    /**
     * Updates the display with the current list of peas/projectiles in the game.
     * @param peas The list of Pea objects to display
     */
    public void displayPeas(ArrayList<Pea> peas) {
        this.displayPeas = peas;
        repaint();
    }

    /**
     * Updates the display with the current list of sun collectibles in the game.
     * @param suns The list of Sun objects to display
     */
    public void displaySuns(ArrayList<Sun> suns) {
        this.displaySuns = suns;
        repaint();
    }

    /**
     * Resets the game view to its initial state.
     * Clears the game board, resets sun points to 50, and clears all entities.
     */
    public void resetView() {
        clearBoard();

        displaySunPoints(50);

        displayZombies.clear();
        displayPeas.clear();
        displaySuns.clear();
        repaint();
    }

    /**
     * Sets the ActionListener for all interactive components in the view.
     * This includes menu buttons, restart buttons, and other UI controls.
     * @param listener The ActionListener to handle button events
     */
    public void setActionListener(ActionListener listener) {
        menuButton.addActionListener(listener);
        restartLevelButton.addActionListener(listener);
        mainMenuButton.addActionListener(listener);
        backSettingsButton.addActionListener(listener);
        nextLevelButton.addActionListener(listener);
    }
}