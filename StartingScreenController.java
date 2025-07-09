import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingScreenController implements ActionListener {
    private final StartingScreen view;
    private final PvZGUI gui;
    private Timer enableButtonTimer;
    private Thread progressThread;
    private static final String CONTINUE_COMMAND = "continue";
    private static final String ENABLE_BUTTON_COMMAND = "enableButton";

    public StartingScreenController(StartingScreen view, PvZGUI gui) {
        this.view = view;
        this.gui = gui;
        initializeControllerEvents();
    }

    private void initializeControllerEvents() {
        setupButtonTimer();
        setupContinueButtonAction();
        startProgressBarUpdate();
    }

    private void setupButtonTimer() {
        enableButtonTimer = new Timer(5000, this);
        enableButtonTimer.setActionCommand(ENABLE_BUTTON_COMMAND);
        enableButtonTimer.setRepeats(false);
        enableButtonTimer.start();
    }

    private void setupContinueButtonAction() {
        view.setActionListener(this);
        view.setButtonActionCommand(CONTINUE_COMMAND);
    }

    private void startProgressBarUpdate() {
        progressThread = new Thread(new ProgressBarUpdater());
        progressThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (CONTINUE_COMMAND.equals(command)) {
            gui.showScreen("menu");
        } else if (ENABLE_BUTTON_COMMAND.equals(command)) {
            view.setButtonEnabled(true);
            view.setButtonText("Continue");
        }
    }

    private class ProgressBarUpdater implements Runnable {
        @Override
        public void run() {
            int counter = 0;
            while (counter <= 100) {
                final int progressValue = counter;
                view.setProgressValue(progressValue);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                    return;
                }
                counter++;
            }
        }
    }
}