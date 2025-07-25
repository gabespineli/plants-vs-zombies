import javax.swing.*;
import java.awt.*;

public class PvZGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;

    private StartingScreen startingScreen;
    private StartingScreenController startingScreenController;

    private MainMenu mainMenu;
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

        startingScreen = new StartingScreen();
        startingScreenController = new StartingScreenController(startingScreen, this);

        mainMenu = new MainMenu();
        mainMenuController = new MainMenuController(mainMenu, this);

        gameView = new GameView();
        gameController = new GameController(gameView, this);
        gameViewListener = new GameViewListener(gameView, gameController);

        mainContainer.add(startingScreen, "start");
        mainContainer.add(mainMenu, "menu");
        mainContainer.add(gameView, "game");

        add(mainContainer);
    }

    private void setupLayout() {
        pack();
        cardLayout.show(mainContainer, "game");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
    }
}