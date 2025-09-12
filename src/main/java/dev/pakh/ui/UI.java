package dev.pakh.ui;

import dev.pakh.logic.BotController;

import javax.swing.*;

public class UI {
    private final String title = "0xFish";
    private final int FRAME_WIDTH = 275;
    private final int FRAME_HEIGHT = 165;
    private final int BUTTON_HEIGHT = 30;
    private final BotController bot;
    private SwingWorker<Boolean, Void> fishingWorker;

    public UI(BotController botController) {
        bot = botController;
        createUI();
    }

    public void createUI() {
        setSystemLookAndFeel();
        JFrame frame = createFrame();
        setupButtons(frame);
        centerFrame(frame);
    }

    private void setupButtons(JFrame frame) {
        JButton detectButton = createButton(frame, "Detect elements", 10, 50, 130);
        JButton exitButton = createButton(frame, "Exit", 10, 80, 130);
        JButton startButton = createButton(frame, "Start fishing", 149, 50, 100);
        JButton stopButton = createButton(frame, "Stop fishing", 149, 80, 100);

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
                        if (ok) {
                            deactivateButton(startButton);
                            activateButton(stopButton);
                        }
                    } catch (Exception ex) {
                        MessageBox.error("Error: " + ex.getMessage());
                    }
                }
            };
            fishingWorker.execute();
        });
        stopButton.addActionListener(e -> {
            deactivateButton(stopButton);
            activateButton(startButton);
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
}
