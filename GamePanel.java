import java.awt.*;
import java.awt.event.ActionListener;

public class GamePanel extends BackgroundPanel {
    private static final String BACKGROUND_PATH = "assets/background/GamePanel.png";
    private static final Dimension PANEL_SIZE = new Dimension(680, 500);

    private ImageButton adventureButton;

    public GamePanel() {
        super(BACKGROUND_PATH, PANEL_SIZE);
        //initializeComponents();
    }
/*
    private void initializeComponents() {
        initializeAdventureButton();
        add(adventureButton);
    }

    private void initializeAdventureButton() {
        adventureButton = new ImageButton("assets/button/Adventure.png", 310, 220);
        adventureButton.setBounds(330, 7, adventureButton.getPreferredSize().width, adventureButton.getPreferredSize().height);
    }

    public void setActionListener(ActionListener listener) {
        adventureButton.addActionListener(listener);
    }
    public void setButtonActionCommand(String adventureCommand) {
        adventureButton.setActionCommand(adventureCommand);
    }

 */
}