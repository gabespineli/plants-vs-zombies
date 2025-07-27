import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartingScreenView extends BackgroundPanel {
    private static final int PROGRESS_BAR_WIDTH = 150;
    private static final int PROGRESS_BAR_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 40;
    private static final String BACKGROUND_PATH = "assets/background/StartingScreen.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);
    
    private JButton continueButton;
    private JProgressBar progressBar;

    public StartingScreenView() {
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
        progressBar.setBackground(Color.WHITE);
        progressBar.setPreferredSize(new Dimension(PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT));

        int xProgressBar = (getPreferredSize().width - PROGRESS_BAR_WIDTH) / 2;
        progressBar.setBounds(xProgressBar, 350, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }

    private void initializeContinueButton() {
        continueButton = new JButton();
        continueButton.setEnabled(false);
        continueButton.setOpaque(true);
        continueButton.setForeground(new Color(126, 69, 21));
        continueButton.setBackground(new Color(126, 69, 21));

        int xButton = (getPreferredSize().width - BUTTON_WIDTH) / 2;
        continueButton.setBounds(xButton, 425, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void layoutComponents() {
        setLayout(null);
        add(progressBar);
        add(continueButton);
    }

    public void setupButton(ActionListener listener, String actionCommand) {
        continueButton.addActionListener(listener);
        continueButton.setActionCommand(actionCommand);
    }

    public void updateButton(boolean enabled, boolean transparent) {
        continueButton.setEnabled(enabled);
        continueButton.setOpaque(transparent);
        continueButton.setContentAreaFilled(transparent);
        continueButton.setBorderPainted(transparent);
    }

    public void updateProgress(int value) {
        progressBar.setValue(value);
    }
}