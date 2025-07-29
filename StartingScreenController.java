import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingScreenController implements ActionListener {
    private static final int BUTTON_ENABLE_DELAY = 3000;
    private static final int PROGRESS_UPDATE_DELAY = 30;

    private final StartingScreenView VIEW;
    private final PvZGUI GUI;
    private final Timer ENABLE_BUTTON_TIMER;
    private final Thread PROGRESS_THREAD;

    public StartingScreenController(StartingScreenView view, PvZGUI gui) {
        this.VIEW = view;
        this.GUI = gui;
        this.ENABLE_BUTTON_TIMER = createButtonTimer();
        this.PROGRESS_THREAD = createProgressThread();
        initializeScreen();
    }

    private void initializeScreen() {
        VIEW.setupButton(this, "continue");
        ENABLE_BUTTON_TIMER.start();
        PROGRESS_THREAD.start();
    }

    private Timer createButtonTimer() {
        Timer timer = new Timer(BUTTON_ENABLE_DELAY, this);
        timer.setRepeats(false);
        return timer;
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        VIEW.updateButton(true, false);

        if ("continue".equals(e.getActionCommand())) {
            GUI.showScreen("menu");
        }
    }
}