import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener, DocumentListener {
    private final MainMenuView VIEW;
    private final PvZGUI GUI;

    private GameController gameController;
    private LevelManager levelManager;

    private boolean saving;

    public MainMenuController(MainMenuView view, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;

        levelManager = LevelManager.loadFile("assets/progress.txt");
        saving = false;

        VIEW.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());

        VIEW.setActionListener(this);
        VIEW.setDocumentListener(this);
    }

    public void playGame(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "adventure" -> {
                GUI.showScreen("game");
                gameController.startGameLoop();
            }
            case "save" -> {
                if (!saving) {
                    VIEW.showNameInput();
                    saving = true;
                } else {
                    String newName = VIEW.getName().trim();
                    if (!newName.isEmpty() && !newName.equals("New Player")) {
                        levelManager.setName(newName);
                        levelManager.saveFile("assets/progress.txt");
                        VIEW.hideNameInput();
                        VIEW.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());
                        System.out.println("Progress saved successfully for " + newName + " at level " + levelManager.getLevel());
                        saving = false;
                    } else {
                        System.out.println("Please enter a valid name (not 'New Player') to save progress.");
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
            String currentName = VIEW.getName().trim();
            if (!currentName.isEmpty() && !currentName.equals("New Player")) {
                levelManager.setName(currentName);
            }
        }
    }

    public void updateProgress() {
        VIEW.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}