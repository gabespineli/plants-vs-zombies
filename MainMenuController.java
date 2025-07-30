import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the main menu interface that handles user interactions
 * and manages the connection between the view and the game logic.
 */
 public class MainMenuController implements ActionListener, DocumentListener {
    private final MainMenuView VIEW;
    private final PvZGUI GUI;

    private GameController gameController;
    private LevelManager levelManager;

    private boolean saving;

    /**
     * Constructs a new MainMenuController with the specified view and GUI components.
     * Initializes the level manager by loading progress from file, sets up event listeners,
     * and updates the player display with current progress information.
     * @param view the MainMenuView component to control
     * @param gui the main PvZGUI component for screen navigation
     */
    public MainMenuController(MainMenuView view, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;

        levelManager = LevelManager.loadFile("assets/progress.txt");
        saving = false;

        VIEW.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());

        VIEW.setActionListener(this);
        VIEW.setDocumentListener(this);
    }

    /**
     * Sets the game controller for this main menu controller.
     * This method establishes the connection between the main menu and the game logic,
     * allowing the main menu to start the game when the adventure button is clicked.
     * @param gameController the GameController instance to use for game management
     */
    public void playGame(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Handles action events from the main menu buttons.
     * Processes adventure, save, and exit commands
     * @param e the ActionEvent containing the command information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "adventure" -> {
                GUI.showScreen("game");
                gameController.startGameLoop();
                gameController.updateLevelDisplay();
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

    /**
     * Handles text insertion events in the name input field.
     * Called when characters are added to the name input during save mode.
     * Updates the level manager with the current name input.
     * @param e the DocumentEvent containing information about the text insertion
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        updateNameFromInput();
    }

    /**
     * Handles text removal events in the name input field.
     * Called when characters are removed from the name input during save mode.
     * Updates the level manager with the current name input.
     * @param e the DocumentEvent containing information about the text removal
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        updateNameFromInput();
    }

    /**
     * Handles text change events in the name input field.
     * Called when the style or attributes of text change in the name input.
     * Updates the level manager with the current name input.
     * @param e the DocumentEvent containing information about the text changes
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        updateNameFromInput();
    }

    /**
     * Updates the level manager with the current name from the input field.
     * This method is called by the DocumentListener methods to keep the
     * level manager synchronized with user input during save mode.
     * Only updates if currently in saving mode and the name is valid
     * (not empty and not "New Player").
     */
    private void updateNameFromInput() {
        if (saving) {
            String currentName = VIEW.getName().trim();
            if (!currentName.isEmpty() && !currentName.equals("New Player")) {
                levelManager.setName(currentName);
            }
        }
    }

    /**
     * Updates the player progress display in the view with current level manager data.
     * This method synchronizes the view with the latest player name and level
     * information from the level manager.
     */
    public void updateProgress() {
        VIEW.updatePlayerDisplay(levelManager.getName(), levelManager.getLevel());
    }

    /**
     * Retrieves the level manager instance used by this controller.
     * This provides access to player progress data and level management functionality.
     * @return the LevelManager instance managing player progress
     */
    public LevelManager getLevelManager() {
        return levelManager;
    }
}