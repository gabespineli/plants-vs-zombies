import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    private final MainMenuView view;
    private final PvZGUI gui;


    public MainMenuController(MainMenuView view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        view.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "adventure" ->
                    {
                        gui.showScreen("game");
                        //start game
                    }
            //case "save" -> gui.showScreen("save"); BONUS FEATURE!
            case "exit" -> System.exit(0);
            default -> System.err.println("Invalid command: " + command);
        }

    }
}