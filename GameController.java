import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class GameController implements ActionListener {
    private final PvZGUI GUI;
    // VIEW
    private final GameView VIEW;
    private GameViewListener gameViewListener;

    // MODEL
    private Gameboard gameboard;
    private Player player;

    private Timer gameLoop;
    private boolean preGame;
    private boolean isPaused;
    private int currentTick;

    public GameController(GameView view, LevelManager levelManager, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;

        this.gameboard = new Gameboard(levelManager.getLevel());
        this.player = new Player();

        gameLoop = new Timer(30, this);
        preGame = true;
        isPaused = false;
        currentTick = 0;

        updateLevelDisplay();
        initializeListener();
    }

    public void startGameLoop() {
        gameLoop.start();
    }

    public void resumeGameLoop() {
        if (!isPaused) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        incrementTick();

        if (preGame) {
            int phase = currentTick / 30 + 1;
            if (phase <= 3) {
                VIEW.displayReadySetPlantPhase(phase);
            } else {
                preGame = false;
                currentTick = 0;
                VIEW.displayReadySetPlantPhase(currentTick);
            }
        } else {
            if (currentTick % 30 == 0 && currentTick != 0) System.out.println( "Game loop tick: " + currentTick/30);
            gameboard.updateGame(currentTick);

            // UPDATE DISPLAYS
            updateZombiesDisplay();
            updatePeaDisplay();
            updatePlantBoardDisplay();
            updateSunPointsDisplay();
            updateSunDisplay();

            // CHECK WIN/LOSE CONDITION
            if (gameboard.checkWinLose(currentTick) != 0){
                if (gameboard.checkWinLose(currentTick) == 1){
                    stopGameLoop();
                    VIEW.setWinVisible(true);
                }
                else {
                    stopGameLoop();
                    VIEW.setLoseVisible(true);
                }

            }
        }

        // MENU INTERFACE
        String command = e.getActionCommand();
        if (command == null) return;
        switch (e.getActionCommand()) {
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
                GUI.getMainMenuController().getLevelManager().setLevel(gameboard.getCurrentLevel() + 1);
                this.gameboard = new Gameboard(gameboard.getCurrentLevel());
                System.out.println("Level completed! Advanced to level " + gameboard.getCurrentLevel());
                resetGame();
                gameLoop.restart();
                VIEW.setWinVisible(false);
            }
        }
    }

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

    // For Drag & Drop
    private void initializeListener() {
        gameViewListener = new GameViewListener(VIEW, this);
        VIEW.setGameViewListener(gameViewListener);
        VIEW.setActionListener(this);
    }

    // GameViewListener Methods
    public void placePlantOnBoard(String plantType, int row, int col) {
        if (gameboard.addPlant(plantType, row, col, player, currentTick)) {
            VIEW.displayPlant(plantType, row, col);
            updateSunPointsDisplay();
        }
    }

    public void removePlantFromBoard(int row, int col) {
        gameboard.removePlant(row, col);
        VIEW.removePlant(row, col);
    }

    // GVL METHODS
    public void updatePlantBoardDisplay() {
        Plant[][] plantBoard = gameboard.getPlantBoard();
        VIEW.clearBoard();

        for (int row = 0; row < plantBoard.length; row++) {
            for (int col = 0; col < plantBoard[row].length; col++) {
                Plant plant = plantBoard[row][col];
                if (plant != null) {
                    VIEW.displayPlant(plant.getPlantType(), row, col);
                }
            }
        }
    }

    public void handleSunClick(Sun sun) {
        gameboard.collectSun(sun, player);
    }

    // DISPLAYS
    private void updateLevelDisplay() {
        VIEW.displayLevel(gameboard.getCurrentLevel());
    }

    private void updateSunPointsDisplay() {
        VIEW.displaySunPoints(player.getSunPoints());
    }

    private void updateSunDisplay() {
        VIEW.displaySuns(gameboard.getActiveSuns());
    }

    private void updateZombiesDisplay() {
        VIEW.displayZombies(gameboard.getAliveZombies());
    }

    private void updatePeaDisplay() {
        VIEW.displayPeas(gameboard.getActivePeas());
    }

    // GETTERS
    public ArrayList<Sun> getActiveSuns() {
        return gameboard.getActiveSuns();
    }
}