import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartingScreen extends BackgroundPanel {
    private static final int PROGRESS_BAR_WIDTH = 150;
    private static final int PROGRESS_BAR_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;
    private static final String BACKGROUND_PATH = "assets/background/StartingScreen.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    
    private JButton continueButton;
    private JProgressBar progressBar;

    public StartingScreen() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        initializeComponents();
    }

    private void initializeComponents() {
        initializeProgressBar();
        initializeContinueButton();
        layoutComponents();
    }

    private void initializeProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
    }

    private void initializeContinueButton() {
        continueButton = new JButton("Loading...");
        continueButton.setEnabled(false);
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.setFocusPainted(false);
    }

    private void layoutComponents() {
        setLayout(null);
        int xProgressBar = (getPreferredSize().width - PROGRESS_BAR_WIDTH) / 2;
        int xButton = (getPreferredSize().width - BUTTON_WIDTH) / 2;

        progressBar.setBounds(xProgressBar, 350, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        continueButton.setBounds(xButton, 400, BUTTON_WIDTH, BUTTON_HEIGHT);

        add(progressBar);
        add(continueButton);
    }

    public void setupButton(ActionListener listener, String actionCommand) {
        continueButton.addActionListener(listener);
        continueButton.setActionCommand(actionCommand);
    }

    public void updateButton(boolean enabled, String text) {
        continueButton.setEnabled(enabled);
        continueButton.setText(text);
    }

    public void updateProgress(int value) {
        progressBar.setValue(value);
    }
}