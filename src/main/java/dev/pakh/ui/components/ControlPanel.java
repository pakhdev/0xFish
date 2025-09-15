package dev.pakh.ui.components;

import dev.pakh.logic.BotController;
import dev.pakh.ui.UI;

import javax.swing.*;

public class ControlPanel {
    private final int BUTTON_HEIGHT = 30;

    private final BotController botController;
    private final UI ui;
    private JButton actionButton;
    private JButton exitButton;
    private SwingWorker<Boolean, Void> fishingWorker;
    private State state = State.IDLE;

    private enum State {
        IDLE,      // Just launched, "Detect elements" is available
        DETECTED,  // Elements detected, "Start fishing" is available
        FISHING    // Fishing, "Stop fishing"  is available
    }

    public ControlPanel(JFrame frame, BotController botController, UI ui) {
        this.botController = botController;
        this.ui = ui;
        setup(frame);
    }

    public void onFishingStart() {
        state = State.FISHING;
        actionButton.setText("Stop fishing");
    }

    public void onFishingStop() {
        state = State.DETECTED;
        actionButton.setText("Start fishing");
    }

    private void setup(JFrame frame) {
        setupWindowListener(frame);
        this.exitButton = createButton(frame, "Exit", 10, 130, 105);
        this.actionButton = createButton(frame, "Detect elements", 120, 130, 130);
        setupActionButton();
        setupExitButton();
    }

    private void setupWindowListener(JFrame frame) {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (fishingWorker != null && !fishingWorker.isDone()) {
                    fishingWorker.cancel(true);
                }
                botController.stopBot();
            }
        });
    }

    private void setupActionButton() {
        actionButton.addActionListener(e -> {
            switch (state) {
                case IDLE -> detectElements();
                case DETECTED -> startFishing();
                case FISHING -> stopFishing();
            }
        });
    }

    private void detectElements() {
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return botController.detectElements();
            }

            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (ok) onFishingStop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void startFishing() {
        fishingWorker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return botController.startBot();
            }

            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (ok) ui.onFishingStarted();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        fishingWorker.execute();
    }

    private void stopFishing() {
        ui.onFishingStopped();
        botController.stopBot();
    }

    private void setupExitButton() {
        exitButton.addActionListener(e -> {
            exitButton.getTopLevelAncestor().dispatchEvent(
                    new java.awt.event.WindowEvent(
                            (java.awt.Window) exitButton.getTopLevelAncestor(),
                            java.awt.event.WindowEvent.WINDOW_CLOSING
                    )
            );
        });
    }

    private JButton createButton(JFrame frame, String text, int x, int y, int width) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, BUTTON_HEIGHT);
        frame.add(button);
        return button;
    }

}
