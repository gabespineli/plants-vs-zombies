import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class StartingScreen extends JPanel {
    private static final int PROGRESS_BAR_WIDTH = 150;
    private static final int PROGRESS_BAR_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;
    private static final String BACKGROUND_PATH = "assets/background/StartingScreen.png";
    
    private JButton continueButton;
    private JProgressBar progressBar;
    private BufferedImage backgroundImage;

    public StartingScreen() {
        setLayout(null);
        setOpaque(false);
        loadBackgroundImage();
        initializeComponents();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File(BACKGROUND_PATH));
        } catch (IOException e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }
    }

    private void initializeComponents() {
        initializeProgressBar();
        initializeContinueButton();
        add(progressBar);
        add(continueButton);
    }

    private void initializeProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        int xPosition = (getPreferredSize().width - PROGRESS_BAR_WIDTH) / 2;
        progressBar.setBounds(xPosition, 350, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }

    private void initializeContinueButton() {
        continueButton = new JButton("Loading...");
        continueButton.setEnabled(false);
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.setFocusPainted(false);

        int xPosition = (getPreferredSize().width - BUTTON_WIDTH) / 2;
        continueButton.setBounds(xPosition, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(670, 503);
    }

    public void setActionListener(ActionListener listener) {
        continueButton.addActionListener(listener);
    }

    public void setButtonEnabled(boolean enabled) {
        continueButton.setEnabled(enabled);
    }

    public void setButtonText(String text) {
        continueButton.setText(text);
    }

    public void setProgressValue(int value) {
        progressBar.setValue(value);
    }
    public void setButtonActionCommand(String command) {
        continueButton.setActionCommand(command);
    }
}