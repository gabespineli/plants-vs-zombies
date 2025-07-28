import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainMenuView extends BackgroundPanel {
    private static final String BACKGROUND_PATH = "assets/background/MainMenu.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);

    private ImageButton adventureButton;
    private ImageButton saveButton;
    private ImageButton exitButton;
    private JLabel nameLabel;
    private JLabel nameDisplay;
    private JLabel levelDisplay;

    public MainMenuView() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        initializeComponents();
    }

    private void initializeComponents() {
        initializeAdventureButton();
        initializeSaveButton();
        initializeExitButton();
        initializeNameLabel();
        createTextDisplays();

        add(adventureButton);
        add(saveButton);
        add(exitButton);
        add(nameLabel);
        add(nameDisplay);
        add(levelDisplay);

        setComponentZOrder(nameDisplay, 0);
        setComponentZOrder(levelDisplay, 0);
    }

    private void initializeAdventureButton() {
        adventureButton = new ImageButton("assets/button/Adventure.png", "adventure", 280, 135);
        adventureButton.setBounds(350, 50, adventureButton.getPreferredSize().width, adventureButton.getPreferredSize().height);
    }

    private void initializeSaveButton() {
        saveButton = new ImageButton("assets/button/Save.png", "save", 250, 90);
        saveButton.setBounds(350, 165, saveButton.getPreferredSize().width, saveButton.getPreferredSize().height);
    }

    private void initializeExitButton() {
        exitButton = new ImageButton("assets/button/Exit.png", "exit", 240, 90);
        exitButton.setBounds(350, 235, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
    }

    private void initializeNameLabel() {
        nameLabel = new JLabel("Welcome,");
        nameLabel.setFont(new Font("DialogInput", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setName("name");
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/button/Name.png")));
            Image scaled = icon.getImage().getScaledInstance(265, 130, Image.SCALE_SMOOTH);
            nameLabel.setIcon(new ImageIcon(scaled));
            nameLabel.setBounds(10, 70, 265, 130);
        } catch (IOException e) {
            System.err.println("Failed to load name image: " + e.getMessage());
        }
    }

    private void createTextDisplays() {
        String name = "PLAYER";
        int level = 1;
        nameDisplay = new JLabel(name);
        nameDisplay.setFont(new Font("DialogInput", Font.BOLD, 18));
        nameDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        nameDisplay.setForeground(Color.GREEN);
        nameDisplay.setOpaque(false);
        nameDisplay.setBounds(35, 160, 220, 20);

        levelDisplay = new JLabel("LEVEL " + level);
        levelDisplay.setFont(new Font("DialogInput", Font.PLAIN, 15));
        levelDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        levelDisplay.setForeground(Color.WHITE);
        levelDisplay.setOpaque(false);
        levelDisplay.setBounds(445, 70, 70, 20);
    }

    public void setActionListener(ActionListener listener) {
        adventureButton.addActionListener(listener);
        saveButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public void setName(String name) {
        nameDisplay.setText(name);
    }
}