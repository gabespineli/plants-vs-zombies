import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The main menu view for PVZ that extends BackgroundPanel.
 * This class provides a graphical user interface with adventure, save, and exit buttons,
 * along with player name input and level display functionality.
 */
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

    /**
     * Constructs a new MainMenuView with the default background and panel size.
     * Initializes all UI components and sets up the layout.
     */
    public MainMenuView() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        initializeComponents();
    }

    /**
     * Initializes and configures all UI components for the main menu.
     * Sets up buttons, labels, text displays, and adds them to the panel
     * with appropriate z-ordering for proper layering.
     */
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

    /**
     * Initializes the adventure button with its image, action command, and positioning.
     * The button is configured with dimensions of 280x135 pixels and positioned
     * at coordinates (350, 50).
     */
    private void initializeAdventureButton() {
        adventureButton = new ImageButton("assets/button/Adventure.png", "adventure", 280, 135);
        adventureButton.setBounds(350, 50, adventureButton.getPreferredSize().width, adventureButton.getPreferredSize().height);
    }

    /**
     * Initializes the save button with its image, action command, and positioning.
     * The button is configured with dimensions of 250x90 pixels and positioned
     * at coordinates (350, 165).
     */
    private void initializeSaveButton() {
        saveButton = new ImageButton("assets/button/Save.png", "save", 250, 90);
        saveButton.setBounds(350, 165, saveButton.getPreferredSize().width, saveButton.getPreferredSize().height);
    }

    /**
     * Initializes the exit button with its image, action command, and positioning.
     * The button is configured with dimensions of 240x90 pixels and positioned
     * at coordinates (350, 235).
     */
    private void initializeExitButton() {
        exitButton = new ImageButton("assets/button/Exit.png", "exit", 240, 90);
        exitButton.setBounds(350, 235, exitButton.getPreferredSize().width, exitButton.getPreferredSize().height);
    }

    /**
     * Initializes the name label with a background image for the name input area.
     * Loads and scales the name background image to 265x130 pixels.
     * If the image cannot be loaded, an error message is printed to stderr.
     */
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

    /**
     * Creates and configures the text display components including welcome message,
     * player name display, and level display. Sets up fonts, colors, positioning,
     * and initial text content for each component.
     */
    private void createTextDisplays() {
        welcomeDisplay = new JLabel("Welcome!");
        welcomeDisplay.setFont(new Font("DialogInput", Font.BOLD, 16));
        welcomeDisplay.setForeground(Color.WHITE);
        welcomeDisplay.setOpaque(false);
        welcomeDisplay.setBounds(45, 130, 210, 20);

        nameDisplay = new JTextArea("New Player");
        nameDisplay.setFont(new Font("DialogInput", Font.BOLD, 15));
        nameDisplay.setForeground(Color.GREEN);
        nameDisplay.setOpaque(false);
        nameDisplay.setEditable(false);
        nameDisplay.setBounds(40, 160, 210, 23);
        nameDisplay.setLineWrap(false);
        nameDisplay.setWrapStyleWord(false);

        levelDisplay = new JLabel("LEVEL 1");
        levelDisplay.setFont(new Font("DialogInput", Font.PLAIN, 15));
        levelDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        levelDisplay.setForeground(Color.WHITE);
        levelDisplay.setOpaque(false);
        levelDisplay.setBounds(445, 70, 70, 20);
    }

    /**
     * Sets the action listener for all interactive buttons in the main menu.
     * The same listener will receive action events from adventure, save, and exit buttons.
     * @param listener the ActionListener to be notified when buttons are clicked
     */
    public void setActionListener(ActionListener listener) {
        adventureButton.addActionListener(listener);
        saveButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    /**
     * Sets a document listener for the name display text area to monitor text changes.
     * This allows the application to respond to changes in the player name input.
     * @param listener the DocumentListener to be notified when the name text changes
     */
    public void setDocumentListener(DocumentListener listener) {
        nameDisplay.getDocument().addDocumentListener(listener);
    }

    /**
     * Updates the player display with the specified name and level information.
     * Configures the name display as non-editable with green text and updates
     * the level display with the formatted level text.
     * @param name the player's name to display
     * @param level the player's current level to display
     */
    public void updatePlayerDisplay(String name, int level) {
        nameDisplay.setText(name);
        nameDisplay.setEditable(false);
        nameDisplay.setOpaque(false);
        nameDisplay.setBorder(null);
        nameDisplay.setBackground(null);
        nameDisplay.setForeground(Color.GREEN);

        levelDisplay.setText("LEVEL " + level);
        welcomeDisplay.setText("Welcome!");

        nameDisplay.revalidate();
        nameDisplay.repaint();
    }

    /**
     * Switches the interface to name input mode, allowing the user to enter their name.
     * Changes the welcome message, makes the name field editable, applies input styling
     * with a white background and green border, and sets focus to the name field.
     */
    public void showNameInput() {
        welcomeDisplay.setText("Enter your name:");
        nameDisplay.setEditable(true);
        nameDisplay.setBackground(Color.WHITE);
        nameDisplay.setForeground(Color.BLACK);
        nameDisplay.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        nameDisplay.setOpaque(true);

        nameDisplay.selectAll();
        nameDisplay.requestFocus();

        nameDisplay.revalidate();
        nameDisplay.repaint();
    }

    /**
     * Switches the interface back to display mode, hiding the name input functionality.
     * Makes the name field non-editable, removes the input styling, restores the
     * green text color, and resets the welcome message.
     */
    public void hideNameInput() {
        nameDisplay.setEditable(false);
        nameDisplay.setOpaque(false);
        nameDisplay.setBorder(null);
        nameDisplay.setBackground(null);
        nameDisplay.setForeground(Color.GREEN);
        welcomeDisplay.setText("Welcome!");

        nameDisplay.revalidate();
        nameDisplay.repaint();
    }

    /**
     * Retrieves the current text content from the name display field.
     * @return the current player name as entered or displayed in the name field
     */
    public String getName() {
        return nameDisplay.getText();
    }

    /**
     * Sets the text content of the name display field to the specified name.
     * @param name the player name to set in the name display field
     */
    public void setName(String name) {
        nameDisplay.setText(name);
    }
}