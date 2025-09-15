package dev.pakh.ui.components;

import javax.swing.*;

public class TimerPanel {
    private final String TIMER_TITLE = "Active time:";

    private JLabel timerLabel;
    private Timer timer;
    private long startTime;
    private long elapsedTime;

    public TimerPanel(JFrame frame) {
        setup(frame);
    }

    public void startTimer() {
        startTime = System.currentTimeMillis() - elapsedTime;
        timer = new javax.swing.Timer(1000, e -> updateTimer());
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    private void setup(JFrame frame) {
        JLabel activeTimeLabel = new JLabel(TIMER_TITLE);
        activeTimeLabel.setBounds(15, 55, 100, 20);
        activeTimeLabel.setFont(activeTimeLabel.getFont().deriveFont(13f));
        frame.add(activeTimeLabel);

        timerLabel = new JLabel("00:00:00");
        timerLabel.setBounds(15, 71, 120, 20);
        timerLabel.setFont(timerLabel.getFont().deriveFont(13f));
        frame.add(timerLabel);
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
}
