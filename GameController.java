import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

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
        updateSunPointsDisplay();
    }

    private void startGameLoop() {
        //gameLoop = new Timer(100, e -> updateGame());
    }

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
        view.removePlant(row, col);
    }

    public void updateBoardDisplay() {
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

    public int getCurrentTick() {
        return currentTick;
    }

    public void incrementTick() {
        currentTick++;
    }

    public Player getPlayer() {
        return player;
    }

    public Gameboard getGameboard() {
        return gameboard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        // Handle other UI actions here if needed
        System.out.println("Action performed: " + command);
    }

    private void updateSunPointsDisplay() {
        view.updateSunPoints(player.getSunPoints());
    }
}