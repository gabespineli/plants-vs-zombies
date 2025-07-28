import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener, DocumentListener {
    private final MainMenuView view;
    private final PvZGUI gui;

    private GameController gameController;


    public MainMenuController(MainMenuView view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        view.setActionListener(this);
    }

    public void playGame(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "adventure" ->
                    {
                        gui.showScreen("game");
                        gameController.startGameLoop();
                    }
            //case "save" -> gui.showScreen("save"); BONUS FEATURE!
            case "exit" -> System.exit(0);
            default -> System.err.println("Invalid command: " + command);
        }

    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}