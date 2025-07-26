import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameController implements ActionListener {
    private final PvZGUI gui;
    // VIEW
    private final GameView view;
    private GameViewListener gameViewListener;

    // MODEL
    private final Gameboard gameboard;
    private Timer gameLoop;
    private final Player player;
    private int currentTick = 0;

    public GameController(GameView view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        this.gameboard = new Gameboard();
        this.player = new Player();

        initializeListener();

        startGameLoop();
        gameLoop.start();
    }

    private void startGameLoop() {
        gameLoop = new Timer(100, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentTick % 10 == 0 && currentTick != 0) {
            System.out.println( "Game loop tick: " + currentTick/10);
        }
        incrementTick();
        gameboard.updateGame(currentTick);
        updateZombiesDisplay();
        updatePlantBoardDisplay();
        updateSunPointsDisplay();
    }

    private void incrementTick() {
        currentTick+=1;
    }

    // For Drag & Drop
    private void initializeListener() {
        gameViewListener = new GameViewListener(view, this);
        view.setGameViewListener(gameViewListener);
    }


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

    //UPDATE DISPLAYS
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

    private void updateSunPointsDisplay() {
        view.updateSunPoints(player.getSunPoints());
    }

    private void updateZombiesDisplay() {
        view.updateZombies(gameboard.getZombies());
    }
}