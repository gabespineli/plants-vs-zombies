import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends BackgroundPanel {
    private static final String BACKGROUND_PATH = "assets/background/MainMenu.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);

    private ImageButton adventureButton;
    private ImageButton saveButton;
    private ImageButton exitButton;

    public MainMenuView() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        initializeComponents();
    }

    private void initializeComponents() {
        initializeAdventureButton();
        initializeSaveButton();
        initializeExitButton();
        add(adventureButton);
        add(saveButton);
        add(exitButton);
    }

    private void initializeAdventureButton() {
        adventureButton = new ImageButton("assets/button/Adventure.png", "adventure", 280, 135);
        adventureButton.setBounds(350, 50, adventureButton.getPreferredSize().width, adventureButton.getPreferredSize().height);
    }

    private void initializeSaveButton() {
        saveButton = new ImageButton("assets/button/Save.png", "save", 250, 90);
        saveButton.setBounds(350, 165, adventureButton.getPreferredSize().width, adventureButton.getPreferredSize().height);
    }

    private void initializeExitButton() {
        exitButton = new ImageButton("assets/button/Exit.png", "exit", 240, 90);
        exitButton.setBounds(350, 235, adventureButton.getPreferredSize().width, adventureButton.getPreferredSize().height);
    }

    public void setActionListener(ActionListener listener) {
        adventureButton.addActionListener(listener);
        saveButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }
}