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
    private final Gameboard gameboard;
    private final Player player;

    private Timer gameLoop;
    private boolean preGame;
    private int currentTick;

    public GameController(GameView view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        this.gameboard = new Gameboard();
        this.player = new Player();

        preGame = true;
        currentTick = 0;

        initializeListener();

    }

    public void startGameLoop() {
        gameLoop = new Timer(30, this);
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        incrementTick();

        if (preGame) {
            int phase = currentTick / 30 + 1;
            if (phase <= 3) {
                view.setReadySetPlantPhase(phase);
            } else {
                preGame = false;
                currentTick = 0;
                view.clearReadySetPlant();
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
        }

        // MENU INTERFACE
        String command = e.getActionCommand();
        if (command != null) {
            switch (e.getActionCommand()) {
                case "menu" -> {
                    gameLoop.stop();
                    view.setSettingsVisible(true);
                }
                case "restart" -> {
                    currentTick = 0;
                    // reset level
                    view.resetView();
                    gameLoop.restart();
                    preGame = true;
                    view.setSettingsVisible(false);
                }
                case "main" -> {
                    currentTick = 0;
                    //reset level
                    view.resetView();
                    preGame = true;
                    view.setSettingsVisible(false);
                    gui.showScreen("menu");
                }
                case "back" -> {
                    gameLoop.start();
                    view.setSettingsVisible(false);
                }
            }
        }

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
            view.placePlant(plantType, row, col);
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
                    view.placePlant(plant.getPlantType(), row, col);
                }
            }
        }
    }

    public void handleSunClick(Sun sun) {
        gameboard.collectSun(sun, player);
    }

    // DISPLAYS
    private void updateSunPointsDisplay() {
        view.updateSunPoints(player.getSunPoints());
    }

    private void updateSunDisplay() {
        view.updateSuns(gameboard.getActiveSuns());
    }

    private void updateZombiesDisplay() {
        view.updateZombies(gameboard.getAliveZombies());
    }

    private void updatePeaDisplay() {
        view.updatePeas(gameboard.getActivePeas());
    }

    // GETTERS
    public ArrayList<Sun> getActiveSuns() {
        return gameboard.getActiveSuns();
    }
}