import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener, DocumentListener {
    private final MainMenuView view;
    private final PvZGUI gui;

    private GameController gameController;
    private LevelManager levelManager;

    private boolean saving;

    public MainMenuController(MainMenuView view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;

        levelManager = LevelManager.loadFile("assets/progress.txt");
        saving = false;

        // Initialize the view with loaded player data
        view.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());

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
            case "adventure" -> {
                gui.showScreen("game");
                gameController.startGameLoop();
            }
            case "save" -> {
                if (!saving) {
                    // Enter save mode - show name input
                    view.showNameInput();
                    saving = true;
                } else {
                    // Complete save - save data and exit save mode
                    String newName = view.getName().trim();
                    if (!newName.isEmpty() && !newName.equals("New Player")) {
                        levelManager.setName(newName);
                        levelManager.saveFile("assets/progress.txt");
                        view.hideNameInput();
                        view.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());
                        System.out.println("Progress saved successfully for " + newName + " at level " + levelManager.getLevel());
                        saving = false;
                    } else {
                        System.out.println("Please enter a valid name (not 'New Player') to save progress.");
                        // Keep in saving mode - don't reset saving to false
                        return;
                    }
                }
            }
            case "exit" -> System.exit(0);
            default -> System.err.println("Invalid command: " + command);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updateNameFromInput();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updateNameFromInput();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updateNameFromInput();
    }

    private void updateNameFromInput() {
        if (saving) {
            String currentName = view.getName().trim();
            if (!currentName.isEmpty() && !currentName.equals("New Player")) {
                levelManager.setName(currentName);
            }
        }
    }

    public void updatePlayerLevel(int newLevel) {
        levelManager.setLevel(newLevel);
        if (!saving) {
            view.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());
        }
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}