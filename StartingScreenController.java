import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingScreenController implements ActionListener {
    private static final int BUTTON_ENABLE_DELAY = 5000; // 5 seconds
    private static final int PROGRESS_UPDATE_DELAY = 50; // 50 milliseconds

    private final StartingScreen view;
    private final PvZGUI gui;
    private final Timer enableButtonTimer;
    private final Thread progressThread;

    public StartingScreenController(StartingScreen view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        this.enableButtonTimer = createButtonTimer();
        this.progressThread = createProgressThread();
        initializeScreen();
    }

    private void initializeScreen() {
        view.setupButton(this, "continue");
        enableButtonTimer.start();
        progressThread.start();
    }

    private Timer createButtonTimer() {
        Timer timer = new Timer(BUTTON_ENABLE_DELAY, e -> {
            view.updateButton(true, "Continue");
        });
        timer.setRepeats(false);
        return timer;
    }

    private Thread createProgressThread() {
        return new Thread(() -> {
            for (int progress = 0; progress <= 100; progress++) {
                view.updateProgress(progress);
                try {
                    Thread.sleep(PROGRESS_UPDATE_DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("continue".equals(e.getActionCommand())) {
            gui.showScreen("menu");
        }
    }
}