import javax.swing.*;
import java.awt.*;

public class PvZGUI extends JFrame{
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public PvZGUI() {
        super("Plants vs Zombies");
        setLayout(new BorderLayout());
        setSize(670, 503);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new StartingScreen(this), "start");
        mainPanel.add(new MainMenu(this), "menu");
        mainPanel.add(new GamePanel(this), "game");

        this.add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "start");
    }

    public void showScreen(String prompt) {
        cardLayout.show(mainPanel, prompt);
    }
}
