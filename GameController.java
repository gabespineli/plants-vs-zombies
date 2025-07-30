import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;


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

    public GameController(GameView view, LevelManager levelManager, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;
        this.gameboard = new Gameboard(levelManager.getLevel());
        this.player = new Player();

        VIEW.displaySeedPackets(gameboard.getAvailablePlantTypes());
        this.gameLoop = new Timer(5, this);
        this.preGame = true;
        this.isPaused = false;
        this.currentTick = 0;
        initializeListener();
    }

    private void initializeListener() {
        gameViewListener = new GameViewListener(VIEW, this);
        VIEW.setGameViewListener(gameViewListener);
        VIEW.setActionListener(this);
    }

    // Game Loop
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

    private void handleGameTick() {
        if (currentTick % 30 == 0 && currentTick != 0) {
            System.out.println("Game loop tick: " + currentTick / 30);
        }
        gameboard.updateGame(currentTick);
        updateDisplays();
        checkWinLoseCondition();
    }

    private void updateDisplays() {
        updateZombiesDisplay();
        updatePeaDisplay();
        updatePlantBoardDisplay();
        updateSunPointsDisplay();
        updateSunDisplay();
        updatePlantsDisplay();
    }

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

    //
    private void resetGame() {
        currentTick = 0;
        preGame = true;
        VIEW.resetView();
        gameboard.resetBoard();
        player.resetSunPoints();
        VIEW.setSettingsVisible(false);
    }

    private void incrementTick() {
        currentTick++;
    }

    // GameViewListener Methods
    public void placePlantOnBoard(String plantType, int row, int col) {
        if (gameboard.addPlant(plantType, row, col, player, currentTick)) {
            VIEW.displayPlantGrid(plantType, row, col);
            updateSunPointsDisplay();
        }
    }

    public void removePlantFromBoard(int row, int col) {
        gameboard.removePlant(row, col);
        VIEW.removePlant(row, col);
    }

    // Display Updates
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

    public void updateSeedPackets() {
        VIEW.displaySeedPackets(gameboard.getAvailablePlantTypes());
    }

    public void updateLevelDisplay() {
        VIEW.displayLevel(gameboard.getCurrentLevel());
    }

    private void updateSunPointsDisplay() {
        VIEW.displaySunPoints(player.getSunPoints());
    }

    private void updateSunDisplay() {
        VIEW.displaySuns(gameboard.getActiveSuns());
    }

    private void updatePlantsDisplay() {
        VIEW.displayPlants(gameboard.getAlivePlants());
    }

    private void updateZombiesDisplay() {
        VIEW.displayZombies(gameboard.getAliveZombies());
    }

    private void updatePeaDisplay() {
        VIEW.displayPeas(gameboard.getActivePeas());
    }

    public void startGameLoop() {
        gameLoop.start();
    }

    public void resumeGameLoop() {
        if (isPaused) {
            gameLoop.start();
            isPaused = false;
        }
    }

    public void stopGameLoop() {
        if (gameLoop != null && gameLoop.isRunning()) {
            gameLoop.stop();
            isPaused = true;
        }
    }

    public void handleSunClick(Sun sun) {
        gameboard.collectSun(sun, player);
    }

    public ArrayList<Sun> getActiveSuns() {
        return gameboard.getActiveSuns();
    }
}