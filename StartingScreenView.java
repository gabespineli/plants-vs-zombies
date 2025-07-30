import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The starting screen view for a game application that extends BackgroundPanel.
 * This class provides a loading screen interface with a progress bar and continue button.
 */
public class StartingScreenView extends BackgroundPanel {
    private static final int PROGRESS_BAR_WIDTH = 150;
    private static final int PROGRESS_BAR_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 40;
    private static final String BACKGROUND_PATH = "assets/background/StartingScreen.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    
    private JButton continueButton;
    private JProgressBar progressBar;

    /**
     * Constructs a new StartingScreenView with the default background and panel size.
     * Initializes all UI components including the progress bar and continue button,
     * then sets up the layout positioning.
     */
    public StartingScreenView() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        initializeComponents();
    }
    /**
     * Initializes and configures all UI components for the starting screen.
     * Sets up the progress bar, continue button, and applies the layout
     * in the correct order to ensure proper component positioning.
     */
    private void initializeComponents() {
        initializeProgressBar();
        initializeContinueButton();
        layoutComponents();
    }

    /**
     * Initializes the progress bar component with default styling and positioning
     */
    private void initializeProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setPreferredSize(new Dimension(PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT));

        int xProgressBar = (getPreferredSize().width - PROGRESS_BAR_WIDTH) / 2;
        progressBar.setBounds(xProgressBar, 350, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }

    /**
     * Initializes the continue button component with styling and positioning.
     */
    private void initializeContinueButton() {
        continueButton = new JButton();
        continueButton.setEnabled(false);
        continueButton.setOpaque(true);
        continueButton.setForeground(new Color(126, 69, 21));
        continueButton.setBackground(new Color(126, 69, 21));

        int xButton = (getPreferredSize().width - BUTTON_WIDTH) / 2;
        continueButton.setBounds(xButton, 425, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    /**
     * Sets up the layout manager and adds all components to the panel.
     * Uses absolute positioning (null layout) to precisely control
     * component placement on the background image.
     */
    private void layoutComponents() {
        setLayout(null);
        add(progressBar);
        add(continueButton);
    }

    /**
     * Configures the continue button with an action listener and command.
     * This method sets up the event handling for the continue button,
     * allowing it to respond to user clicks and identify the action source.
     * @param listener the ActionListener to handle button click events
     * @param actionCommand the action command string to identify this button's events
     */
    public void setupButton(ActionListener listener, String actionCommand) {
        continueButton.addActionListener(listener);
        continueButton.setActionCommand(actionCommand);
    }

    /**
     * Updates the visual state and behavior of the continue button.
     * This method allows dynamic control of the button's appearance and
     * interaction state during different phases of the loading process.
     * @param enabled whether the button should be clickable and responsive
     * @param transparent whether the button should have transparent styling
     */
    public void updateButton(boolean enabled, boolean transparent) {
        continueButton.setEnabled(enabled);
        continueButton.setOpaque(transparent);
        continueButton.setContentAreaFilled(transparent);
        continueButton.setBorderPainted(transparent);
    }

    /**
     * Updates the progress bar with the specified completion value.
     * The progress bar will display the new value as both a visual bar
     * and as percentage text if string painting is enabled.
     * @param value the progress value to set (typically 0-100 for percentage)
     */
    public void updateProgress(int value) {
        progressBar.setValue(value);
    }
}