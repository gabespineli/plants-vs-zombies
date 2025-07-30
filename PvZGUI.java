import javax.swing.*;
import java.awt.*;

/**
 * Main GUI class for the Plants vs Zombies game that extends JFrame.
 * This class serves as the primary window and manages navigation between different
 * game screens using a CardLayout system.
 */
public class PvZGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;

    private StartingScreenView startingScreenView;
    private StartingScreenController startingScreenController;

    private MainMenuView mainMenuView;
    private MainMenuController mainMenuController;

    private GameView gameView;
    private GameController gameController;
    private GameViewListener gameViewListener;

    /**
     * Constructs a new PvZGUI window and initializes all components.
     * Sets up the main frame properties, creates all view-controller pairs,
     * configures the layout system, and displays the starting screen.
     */
    public PvZGUI() {
        initializeFrame();
        initializeComponents();
        setupLayout();
    }

    /**
     * Constructs a new PvZGUI window and initializes all components.
     * Sets up the main frame properties, creates all view-controller pairs,
     * configures the layout system, and displays the starting screen.
     */
    private void initializeFrame() {
        setTitle("Plants vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(100,100);
    }

    /**
     * Initializes all view and controller components for the application.
     * Creates the CardLayout system and sets up all three main screens:
     * starting screen, main menu, and game screen.
     */
    private void initializeComponents() {
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        startingScreenView = new StartingScreenView();
        startingScreenController = new StartingScreenController(startingScreenView, this);

        mainMenuView = new MainMenuView();
        mainMenuController = new MainMenuController(mainMenuView,this);

        gameView = new GameView();
        gameController = new GameController(gameView, mainMenuController.getLevelManager(), this);
        gameViewListener = new GameViewListener(gameView, gameController);

        mainMenuController.playGame(gameController);

        mainContainer.add(startingScreenView, "start");
        mainContainer.add(mainMenuView, "menu");
        mainContainer.add(gameView, "game");

        add(mainContainer);
    }

    /**
     * Sets up the final layout configuration and displays the initial screen.
     * Packs the frame to fit the preferred sizes of its components and
     * shows the starting screen as the initial view.
     */
    private void setupLayout() {
        pack();
        showScreen("start");
    }

    /**
     * Switches the display to the specified screen using CardLayout.
     * This method provides the primary navigation mechanism for the application.
     * After switching screens, it updates the main menu progress display
     * to ensure player information remains current.
     * @param screenName the identifier of the screen to display
     */
    public void showScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
        mainMenuController.updateProgress();
    }

    /**
     * Retrieves the main menu controller instance.
     * This method provides access to the main menu controller for other
     * components that need to interact with menu functionality or access
     * player progress information.
     * @return the MainMenuController instance managing the main menu
     */
    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }
}