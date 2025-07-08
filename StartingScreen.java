import javax.swing.*;
import java.awt.*;

public class StartingScreen extends JPanel {
    private final JButton continueButton;
    private final JLabel backgroundLabel;

    public StartingScreen(PvZGUI parent) {
        setLayout(null);

        backgroundLabel = new JLabel(new ImageIcon("assets/background/StartingScreen.png")) ;
        backgroundLabel.setBounds(0,0,670,503);
        this.add(backgroundLabel);

        continueButton = new JButton("Loading...");
        continueButton.setEnabled(false);
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.setFocusPainted(false);
        continueButton.setBounds(250, 425, 150, 40);

        add(continueButton);

        new Timer(10000, e -> {
            continueButton.setEnabled(true);
            continueButton.setText("Continue");
        }).start();

        continueButton.addActionListener(e -> parent.showScreen("menu")); // should have a controller class
    }
}
