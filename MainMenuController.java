import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener, DocumentListener {
    private final MainMenuView view;
    private final PvZGUI gui;

    private GameController gameController;
    private LevelManager levelManager;


    public MainMenuController(MainMenuView view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;

        levelManager = LevelManager.loadFile("assets/progress.txt");

        view.setActionListener(this);
        view.setDocumentListener(this);
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
            case "save" -> {
                view.showNameInput();
                //levelManager.saveFile("assets/progress.txt");
            }
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