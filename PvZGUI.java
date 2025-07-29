import javax.swing.*;
import java.awt.*;

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

    public PvZGUI() {
        initializeFrame();
        initializeComponents();
        setupLayout();
    }

    private void initializeFrame() {
        setTitle("Plants vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(100,100);
    }

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

    private void setupLayout() {
        pack();
        showScreen("start");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
        mainMenuController.updateProgress();
    }

    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }
}