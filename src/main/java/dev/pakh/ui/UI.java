package dev.pakh.ui;

import dev.pakh.logic.BotController;
import dev.pakh.models.ui.FishingWorkflowListener;

import javax.swing.*;

public class UI implements FishingWorkflowListener {
    private final String title = "0xFish";
    private final int FRAME_WIDTH = 275;
    private final int FRAME_HEIGHT = 165;
    private final int BUTTON_HEIGHT = 30;
    private final BotController bot;
    private SwingWorker<Boolean, Void> fishingWorker;
    private JButton startButton;
    private JButton stopButton;

    private JLabel fishCounterLabel;
    private JLabel boxCounterLabel;
    private int fishCount = 0;
    private int boxCount = 0;

    private JLabel timerLabel;
    private Timer timer;
    private long startTime;
    private long elapsedTime = 0;

    public UI(BotController botController) {
        bot = botController;
        createUI();
    }

    public void createUI() {
        setSystemLookAndFeel();
        JFrame frame = createFrame();
        setupLabels(frame);
        setupButtons(frame);
        centerFrame(frame);
    }

    private void resetCounters() {
        fishCount = 0;
        boxCount = 0;
        SwingUtilities.invokeLater(() -> {
            fishCounterLabel.setText("Fish: 0");
            boxCounterLabel.setText("Old Boxes: 0");
        });
    }

    private void incrementFishCount() {
        fishCount++;
        SwingUtilities.invokeLater(() -> fishCounterLabel.setText("Fish: " + fishCount));
    }

    private void incrementOldBoxCount() {
        boxCount++;
        SwingUtilities.invokeLater(() -> boxCounterLabel.setText("Old Boxes: " + boxCount));
    }

    private void setupLabels(JFrame frame) {
        JLabel activeTimeLabel = new JLabel("Active time:");
        activeTimeLabel.setBounds(15, 7, 100, 20);
        activeTimeLabel.setFont(activeTimeLabel.getFont().deriveFont(13f));
        frame.add(activeTimeLabel);

        timerLabel = new JLabel("00:00:00");
        timerLabel.setBounds(15, 23, 120, 20);
        timerLabel.setFont(timerLabel.getFont().deriveFont(13f));
        frame.add(timerLabel);

        fishCounterLabel = new JLabel("Fish: 0");
        fishCounterLabel.setBounds(150, 7, 100, 20);
        fishCounterLabel.setFont(fishCounterLabel.getFont().deriveFont(13f));
        frame.add(fishCounterLabel);

        boxCounterLabel = new JLabel("Old Boxes: 0");
        boxCounterLabel.setBounds(150, 23, 130, 20);
        boxCounterLabel.setFont(boxCounterLabel.getFont().deriveFont(13f));
        frame.add(boxCounterLabel);
    }

    private void setupButtons(JFrame frame) {
        JButton detectButton = createButton(frame, "Detect elements", 10, 50, 130);
        JButton exitButton = createButton(frame, "Exit", 10, 80, 130);

        this.startButton = createButton(frame, "Start fishing", 149, 50, 100);
        this.stopButton = createButton(frame, "Stop fishing", 149, 80, 100);

        detectButton.addActionListener(e -> {
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return bot.detectElements();
                }

                @Override
                protected void done() {
                    try {
                        boolean ok = get();
                        if (ok) {
                            deactivateButton(detectButton);
                            activateButton(startButton);
                        }
                    } catch (Exception ex) {
                        MessageBox.error("Error: " + ex.getMessage());
                    }
                }
            };
            worker.execute();
        });
        exitButton.addActionListener(e -> {
            frame.dispatchEvent(new java.awt.event.WindowEvent(frame, java.awt.event.WindowEvent.WINDOW_CLOSING));
        });
        startButton.addActionListener(e -> {
            fishingWorker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return bot.startFishing();
                }

                @Override
                protected void done() {
                    try {
                        boolean ok = get();
                        if (ok) onFishingStarted();
                    } catch (Exception ex) {
                        MessageBox.error("Error: " + ex.getMessage());
                    }
                }
            };
            fishingWorker.execute();
        });
        stopButton.addActionListener(e -> {
            onFishingStopped();
            bot.stopFishing();
        });

        deactivateButton(startButton);
        deactivateButton(stopButton);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame(title);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setAlwaysOnTop(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (fishingWorker != null && !fishingWorker.isDone()) {
                    fishingWorker.cancel(true);
                }
                bot.stopFishing();
            }
        });

        return frame;
    }

    private JButton createButton(JFrame frame, String text, int x, int y, int width) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, BUTTON_HEIGHT);
        frame.add(button);
        return button;
    }

    private void centerFrame(JFrame frame) {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activateButton(JButton button) {
        button.setEnabled(true);
    }

    private void deactivateButton(JButton button) {
        button.setEnabled(false);
    }

    private void startTimer() {
        startTime = System.currentTimeMillis() - elapsedTime;
        timer = new Timer(1000, e -> updateTimer());
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        elapsedTime = 0;
        timerLabel.setText("00:00:00");
    }

    private void updateTimer() {
        long now = System.currentTimeMillis();
        long totalMillis = now - startTime;

        long totalSeconds = totalMillis / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    @Override
    public void onFishingStarted() {
        deactivateButton(startButton);
        activateButton(stopButton);
        startTimer();
    }

    @Override
    public void onFishingStopped() {
        activateButton(startButton);
        deactivateButton(stopButton);
        stopTimer();
    }

    @Override
    public void onFishObtention() {
        incrementFishCount();
    }

    @Override
    public void onOldBoxObtention() {
        incrementOldBoxCount();
    }
}
