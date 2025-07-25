import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private final GamePanel view;
    private final PvZGUI gui;
    private final Gameboard gameboard;
    private final Player player;
    private int currentTick = 0;

    public GameController(GamePanel view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        gameboard = new Gameboard();
        player = new Player();

        updateSunPointsDisplay();
    }

    public void placePlantOnBoard(String plantType, int row, int col) {
        // First check if placement is valid in the model
        if (gameboard.addPlant(plantType, row, col, player, currentTick)) {
            // If successful, update the view with the GIF
            view.placePlant(plantType, row, col);
            updateSunPointsDisplay();
        }
    }

    public void updateBoardDisplay() {
        Plant[][] plantBoard = gameboard.getPlantBoard();
        view.clearBoard();

        // Update with current state
        for (int row = 0; row < plantBoard.length; row++) {
            for (int col = 0; col < plantBoard[row].length; col++) {
                Plant plant = plantBoard[row][col];
                if (plant != null) {
                    String plantType = getPlantType(plant);
                    view.placePlant(plantType, row, col);
                }
            }
        }
    }

    private String getPlantType(Plant plant) {
        if (plant instanceof Sunflower) return "sunflower";
        if (plant instanceof Peashooter) return "peashooter";
        // Add other plant types as needed
        return "";
    }


    private int getCurrentTick() {
        return currentTick;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //System.out.println(command);
    }

    private void updateSunPointsDisplay() {
        view.updateSunPoints(player.getSunPoints());
    }
}