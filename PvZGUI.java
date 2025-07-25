import javax.swing.*;
import java.awt.*;

public class PvZGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private StartingScreen startingScreen;
    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private StartingScreenController startingScreenController;
    private MainMenuController mainMenuController;
    private GameController gameController;

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

        gamePanel = new GamePanel();
        gameController = new GameController(gamePanel, this);
        gamePanel.setController(gameController);

        mainContainer.add(startingScreen, "start");
        mainContainer.add(mainMenu, "menu");
        mainContainer.add(gamePanel, "game");

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