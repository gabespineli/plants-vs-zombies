import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentListener;
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
    private JLabel welcomeDisplay;
    private JTextArea nameDisplay;
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
        add(welcomeDisplay);
        add(nameDisplay);
        add(levelDisplay);

        setComponentZOrder(welcomeDisplay, 0);
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
        nameLabel = new JLabel();
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
        String name = "New Player";
        int level = 1;
        welcomeDisplay = new JLabel("Welcome!");
        welcomeDisplay.setFont(new Font("DialogInput", Font.BOLD, 16));
        welcomeDisplay.setForeground(Color.WHITE);
        welcomeDisplay.setOpaque(false);
        welcomeDisplay.setBounds(45, 130, 210, 20);

        nameDisplay = new JTextArea(name);
        nameDisplay.setFont(new Font("DialogInput", Font.BOLD, 18));
        nameDisplay.setForeground(Color.GREEN);
        nameDisplay.setOpaque(false);
        nameDisplay.setEditable(false);
        nameDisplay.setBounds(45, 160, 210, 20);

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

    public void showNameInput() {
        welcomeDisplay.setText("Input your name here:");
        nameDisplay.setEditable(true);
        nameDisplay.setBackground(Color.WHITE);
        nameDisplay.setForeground(Color.GREEN);
        nameDisplay.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        nameDisplay.setOpaque(true);
        nameDisplay.setBounds(45, 160, 210, 20);
        nameDisplay.revalidate();
        nameDisplay.repaint();
    }

    public void setDocumentListener(DocumentListener listener) {
        nameDisplay.getDocument().addDocumentListener(listener);
    }

    public String getName() {
        return nameDisplay.getText();
    }

}