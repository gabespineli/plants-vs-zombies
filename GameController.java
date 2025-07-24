import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private final GamePanel view;
    private final PvZGUI gui;
    private Player player;


    public GameController(GamePanel view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        player = new Player();
        view.setActionListener(this);
        updateSunPointsDisplay();
    }

    private void updateSunPointsDisplay() {
        view.updateSunPoints(player.getSunPoints());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "peashooter" -> {
                System.out.println(command);
            }
            case "sunflower" -> {
                System.out.println(command);
            }
            case "cherrybomb" -> {
                System.out.println(command);
            }
            case "wallnut" -> {
                System.out.println(command);
            }
            case "potatomine" -> {
                System.out.println(command);
            }
            case "snowpea" -> {
                System.out.println(command);
            }
            case "shovel" -> {
                System.out.println(command);
            }
            default -> System.err.println("Invalid command: " + command);
        }

    }
}