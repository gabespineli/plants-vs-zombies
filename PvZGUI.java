import javax.swing.*;
import java.awt.*;

public class PvZGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private StartingScreen startingScreen;
    private MainMenu mainMenu;
    private StartingScreenController startingScreenController;

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

        mainContainer.add(startingScreen, "start");
        mainContainer.add(mainMenu, "menu");


        add(mainContainer);
    }

    private void setupLayout() {
        pack();
        cardLayout.show(mainContainer, "start");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
    }
}