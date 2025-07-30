import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the starting screen that manages the loading sequence
 * and user interaction for transitioning to the main menu.
 */
public class StartingScreenController implements ActionListener {
    private static final int BUTTON_ENABLE_DELAY = 3000;
    private static final int PROGRESS_UPDATE_DELAY = 30;

    private final StartingScreenView VIEW;
    private final PvZGUI GUI;
    private final Timer ENABLE_BUTTON_TIMER;
    private final Thread PROGRESS_THREAD;

    /**
     * Constructs a new StartingScreenController with the specified view and GUI components.
     * Initializes the button timer and progress thread, then starts the loading sequence
     * automatically upon construction.
     * @param view the StartingScreenView component to control
     * @param gui the main PvZGUI component for screen navigation
     */
    public StartingScreenController(StartingScreenView view, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;
        this.ENABLE_BUTTON_TIMER = createButtonTimer();
        this.PROGRESS_THREAD = createProgressThread();
        initializeScreen();
    }

    /**
     * Initializes the starting screen by setting up the continue button
     * and starting both the button enable timer and progress animation thread.
     * This method begins the loading sequence immediately.
     */
    private void initializeScreen() {
        VIEW.setupButton(this, "continue");
        ENABLE_BUTTON_TIMER.start();
        PROGRESS_THREAD.start();
    }

    /**
     * Creates and configures the timer responsible for enabling the continue button.
     * The timer is set to fire once after the specified delay period and does not repeat.
     * @return a configured Timer that will enable the continue button after the delay
     */
    private Timer createButtonTimer() {
        Timer timer = new Timer(BUTTON_ENABLE_DELAY, this);
        timer.setRepeats(false);
        return timer;
    }

    /**
     * Creates a background thread that animates the progress bar.
     * The thread updates the progress bar from 10% to 100% with small increments
     * and delays between updates to create a smooth animation effect.
     * @return a Thread that will animate the progress bar when started
     */
    private Thread createProgressThread() {
        return new Thread(() -> {
            for (int progress = 10; progress <= 100; progress++) {
                VIEW.updateProgress(progress);
                try {
                    Thread.sleep(PROGRESS_UPDATE_DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
    }

    /**
     * Handles action events from both the timer and the continue button.
     * @param e the ActionEvent containing the action command and source information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        VIEW.updateButton(true, false);

        if ("continue".equals(e.getActionCommand())) {
            GUI.showScreen("menu");
        }
    }
}