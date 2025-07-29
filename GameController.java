import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class GameController implements ActionListener {
    private final PvZGUI gui;
    // VIEW
    private final GameView view;
    private GameViewListener gameViewListener;

    // MODEL
    private Gameboard gameboard;
    private Player player;

    private Timer gameLoop;
    private boolean preGame;
    private boolean isPaused;
    private int currentTick;

    public GameController(GameView view, LevelManager levelManager, PvZGUI gui) {
        this.view = view;
        this.gui = gui;

        this.gameboard = new Gameboard(levelManager.getLevel());
        this.player = new Player();

        gameLoop = new Timer(10, this);
        preGame = true;
        isPaused = false;
        currentTick = 0;

        initializeListener();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        incrementTick();

        if (preGame) {
            int phase = currentTick / 30 + 1;
            if (phase <= 3) {
                view.displayReadySetPlantPhase(phase);
            } else {
                preGame = false;
                currentTick = 0;
                view.displayReadySetPlantPhase(currentTick);
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
                    view.setWinVisible(true);
                }
                else {
                    stopGameLoop();
                    view.setLoseVisible(true);
                }

            }
        }

        // MENU INTERFACE
        String command = e.getActionCommand();
        if (command == null) return;
        switch (e.getActionCommand()) {
            case "menu" -> {
                stopGameLoop();
                view.setSettingsVisible(true);
            }
            case "back" -> {
                resumeGameLoop();
                view.setSettingsVisible(false);
            }

            case "restart" -> {
                this.gameboard = new Gameboard(gameboard.getCurrentLevel());
                resetGame();
                gameLoop.restart();
                view.setLoseVisible(false);
            }
            case "main" -> {
                gui.showScreen("menu");
                view.setSettingsVisible(false);
            }
            case "next" -> {
                int nextLevel = gameboard.getCurrentLevel() + 1;
                gui.getMainMenuController().getLevelManager().setLevel(nextLevel);
                this.gameboard = new Gameboard(nextLevel);
                System.out.println("Level completed! Advanced to level " + nextLevel);
                updateLevelDisplay();
                resetGame();
                gameLoop.restart();
                view.setWinVisible(false);
            }
        }
    }

    private void resetGame() {
        currentTick = 0;
        preGame = true;
        view.resetView();
        gameboard.resetBoard();
        player.resetSunPoints();
        view.setSettingsVisible(false);
    }


    private void incrementTick() {
        currentTick++;
    }

    // For Drag & Drop
    private void initializeListener() {
        gameViewListener = new GameViewListener(view, this);
        view.setGameViewListener(gameViewListener);
        view.setActionListener(this);
    }

    // GameViewListener Methods
    public void placePlantOnBoard(String plantType, int row, int col) {
        if (gameboard.addPlant(plantType, row, col, player, currentTick)) {
            view.displayPlant(plantType, row, col);
            updateSunPointsDisplay();
        }
    }

    public void removePlantFromBoard(int row, int col) {
        gameboard.removePlant(row, col);
        view.removePlant(row, col);
    }

    // GVL METHODS
    public void updatePlantBoardDisplay() {
        Plant[][] plantBoard = gameboard.getPlantBoard();
        view.clearBoard();

        for (int row = 0; row < plantBoard.length; row++) {
            for (int col = 0; col < plantBoard[row].length; col++) {
                Plant plant = plantBoard[row][col];
                if (plant != null) {
                    view.displayPlant(plant.getPlantType(), row, col);
                }
            }
        }
    }

    public void handleSunClick(Sun sun) {
        gameboard.collectSun(sun, player);
    }

    // DISPLAYS
    private void updateLevelDisplay() {
        view.displayLevel(gameboard.getCurrentLevel());
    }

    private void updateSunPointsDisplay() {
        view.displaySunPoints(player.getSunPoints());
    }

    private void updateSunDisplay() {
        view.displaySuns(gameboard.getActiveSuns());
    }

    private void updateZombiesDisplay() {
        view.displayZombies(gameboard.getAliveZombies());
    }

    private void updatePeaDisplay() {
        view.displayPeas(gameboard.getActivePeas());
    }

    // GETTERS
    public ArrayList<Sun> getActiveSuns() {
        return gameboard.getActiveSuns();
    }
}