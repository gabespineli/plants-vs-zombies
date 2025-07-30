import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Controls the main game logic, user interactions, and game state transitions for Plants vs Zombies.
 * <p>
 * This class acts as the bridge between the view (UI) and the model (gameboard, player),
 * handling game ticks, user actions, win/lose conditions, and updating the display.
 * It manages the game loop, responds to menu commands, and coordinates the placement and removal of plants.
 * </p>
 */
public class GameController implements ActionListener {
    private final PvZGUI GUI;
    private final GameView VIEW;
    private GameViewListener gameViewListener;

    // Model
    private Gameboard gameboard;
    private Player player;

    // Game State
    private Timer gameLoop;
    private boolean preGame;
    private boolean isPaused;
    private int currentTick;

    /**
     * Constructs a GameController for the given view, level manager, and GUI.
     * Initializes the gameboard, player, and sets up listeners and game loop.
     *
     * @param view         the game view for UI updates
     * @param levelManager the level manager for current level data
     * @param gui          the main GUI driver
     */
    public GameController(GameView view, LevelManager levelManager, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;
        this.gameboard = new Gameboard(levelManager.getLevel());
        this.player = new Player();

        VIEW.displaySeedPackets(gameboard.getAvailablePlantTypes());
        this.gameLoop = new Timer(10, this);
        this.preGame = true;
        this.isPaused = false;
        this.currentTick = 0;
        initializeListener();
    }

    /**
     * Initializes listeners for game view and action events.
     */
    private void initializeListener() {
        gameViewListener = new GameViewListener(VIEW, this);
        VIEW.setGameViewListener(gameViewListener);
        VIEW.setActionListener(this);
    }

    /**
     * Main game loop handler, called by the Swing timer.
     * Handles pre-game phase, game ticks, and menu commands.
     *
     * @param e the action event triggering the loop
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        incrementTick();
        if (preGame) {
            handlePreGamePhase();
        } else {
            handleGameTick();
        }
        handleMenuCommands(e);
    }

    /**
     * Handles the pre-game phase, displaying "Ready, Set, Plant" animations.
     */
    private void handlePreGamePhase() {
        int phase = currentTick / 30 + 1;
        if (phase <= 3) {
            VIEW.displayReadySetPlantPhase(phase);
        } else {
            preGame = false;
            currentTick = 0;
            VIEW.displayReadySetPlantPhase(currentTick);
        }
    }

    /**
     * Handles a single game tick: updates gameboard, display, and checks win/lose conditions.
     */
    private void handleGameTick() {
        if (currentTick % 30 == 0 && currentTick != 0) {
            System.out.println("Game loop tick: " + currentTick / 30);
        }
        gameboard.updateGame(currentTick);
        updateDisplays();
        checkWinLoseCondition();
    }

    /**
     * Updates all game displays: zombies, peas, plants, sun points, and suns.
     */
    private void updateDisplays() {
        updateZombiesDisplay();
        updatePeaDisplay();
        updatePlantBoardDisplay();
        updateSunPointsDisplay();
        updateSunDisplay();
        updatePlantsDisplay();
    }

    /**
     * Checks for win or lose conditions and updates the view accordingly.
     */
    private void checkWinLoseCondition() {
        int result = gameboard.checkWinLose(currentTick);
        if (result != 0) {
            stopGameLoop();
            if (result == 1) {
                VIEW.setWinVisible(true);
            } else {
                VIEW.setLoseVisible(true);
            }
        }
    }

    /**
     * Handles menu-related commands (pause, resume, restart, next level, etc.).
     *
     * @param e the action event containing the command
     */
    private void handleMenuCommands(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == null) return;
        switch (command) {
            case "menu" -> {
                stopGameLoop();
                VIEW.setSettingsVisible(true);
            }
            case "back" -> {
                resumeGameLoop();
                VIEW.setSettingsVisible(false);
            }
            case "restart" -> {
                this.gameboard = new Gameboard(gameboard.getCurrentLevel());
                resetGame();
                gameLoop.restart();
                VIEW.setLoseVisible(false);
            }
            case "main" -> {
                GUI.showScreen("menu");
                VIEW.setSettingsVisible(false);
            }
            case "next" -> {
                int nextLevel = gameboard.getCurrentLevel() + 1;
                GUI.getMainMenuController().getLevelManager().setLevel(nextLevel);
                this.gameboard = new Gameboard(nextLevel);
                System.out.println("Level completed! Advanced to level " + nextLevel);
                resetGame();
                gameLoop.restart();
                VIEW.setWinVisible(false);
                updateLevelDisplay();
                updateSeedPackets();
            }
        }
    }

    /**
     * Resets the game state, view, and player sun points for a new game or level.
     */
    private void resetGame() {
        currentTick = 0;
        preGame = true;
        VIEW.resetView();
        gameboard.resetBoard();
        player.resetSunPoints();
        VIEW.setSettingsVisible(false);
    }

    /**
     * Increments the game tick counter.
     */
    private void incrementTick() {
        currentTick++;
    }

    /**
     * Places a plant on the board if possible, updates the view and sun points.
     *
     * @param plantType the type of plant to place
     * @param row       the row to place the plant
     * @param col       the column to place the plant
     */
    public void placePlantOnBoard(String plantType, int row, int col) {
        if (gameboard.addPlant(plantType, row, col, player, currentTick)) {
            VIEW.displayPlantGrid(plantType, row, col);
            updateSunPointsDisplay();
        }
    }

    /**
     * Removes a plant from the board and updates the view.
     *
     * @param row the row of the plant to remove
     * @param col the column of the plant to remove
     */
    public void removePlantFromBoard(int row, int col) {
        gameboard.removePlant(row, col);
        VIEW.removePlant(row, col);
    }

    /**
     * Updates the plant grid display to reflect the current plant board state.
     */
    public void updatePlantBoardDisplay() {
        Plant[][] plantBoard = gameboard.getPlantBoard();
        VIEW.clearBoard();
        for (int row = 0; row < plantBoard.length; row++) {
            for (int col = 0; col < plantBoard[row].length; col++) {
                Plant plant = plantBoard[row][col];
                if (plant != null) {
                    VIEW.displayPlantGrid(plant.getPlantType(), row, col);
                }
            }
        }
    }

    /**
     * Updates the display of available seed packets based on the current gameboard.
     */
    public void updateSeedPackets() {
        VIEW.displaySeedPackets(gameboard.getAvailablePlantTypes());
    }

    /**
     * Updates the display to show the current level.
     */
    public void updateLevelDisplay() {
        VIEW.displayLevel(gameboard.getCurrentLevel());
    }

    /**
     * Updates the display of the player's sun points.
     */
    private void updateSunPointsDisplay() {
        VIEW.displaySunPoints(player.getSunPoints());
    }

    /**
     * Updates the display of active suns on the gameboard.
     */
    private void updateSunDisplay() {
        VIEW.displaySuns(gameboard.getActiveSuns());
    }

    /**
     * Updates the display of all alive plants on the gameboard.
     */
    private void updatePlantsDisplay() {
        VIEW.displayPlants(gameboard.getAlivePlants());
    }

    /**
     * Updates the display of all alive zombies on the gameboard.
     */
    private void updateZombiesDisplay() {
        VIEW.displayZombies(gameboard.getAliveZombies());
    }

    /**
     * Updates the display of all active peas on the gameboard.
     */
    private void updatePeaDisplay() {
        VIEW.displayPeas(gameboard.getActivePeas());
    }

    /**
     * Starts the main game loop timer.
     */
    public void startGameLoop() {
        gameLoop.start();
    }

    /**
     * Resumes the game loop if it is currently paused.
     */
    public void resumeGameLoop() {
        if (isPaused) {
            gameLoop.start();
            isPaused = false;
        }
    }

    /**
     * Stops the game loop timer and sets the paused flag.
     */
    public void stopGameLoop() {
        if (gameLoop != null && gameLoop.isRunning()) {
            gameLoop.stop();
            isPaused = true;
        }
    }

    /**
     * Handles the event when a sun is clicked, collecting it for the player.
     *
     * @param sun the sun object to collect
     */
    public void handleSunClick(Sun sun) {
        gameboard.collectSun(sun, player);
    }

    /**
     * Returns the list of currently active suns on the gameboard.
     *
     * @return list of active Sun objects
     */
    public ArrayList<Sun> getActiveSuns() {
        return gameboard.getActiveSuns();
    }
}