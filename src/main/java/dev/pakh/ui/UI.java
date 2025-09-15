package dev.pakh.ui;

import dev.pakh.logic.BotController;
import dev.pakh.logic.CaptureDispatcher;
import dev.pakh.logic.ports.BotStateListener;
import dev.pakh.ui.components.ControlPanel;
import dev.pakh.ui.components.StatsPanel;
import dev.pakh.ui.components.StatusPanel;
import dev.pakh.ui.components.TimerPanel;

import javax.swing.*;

public class UI implements BotStateListener {
    private final String TITLE = "0xFish";
    private final int FRAME_WIDTH = 275;
    private final int FRAME_HEIGHT = 215;

    private ControlPanel controlPanel;
    private TimerPanel timerPanel;

    public UI() {
        setup();
    }

    private void setup() {
        setSystemLookAndFeel();
        JFrame frame = createFrame();
        StatusPanel statusPanel = new StatusPanel(frame);
        BotController botController = new BotController(
                new CaptureDispatcher(statusPanel),
                this,
                new StatsPanel(frame),
                statusPanel
        );
        controlPanel = new ControlPanel(frame, botController, this);
        timerPanel = new TimerPanel(frame);
        centerFrame(frame);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame(TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setAlwaysOnTop(true);
        return frame;
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

    @Override
    public void onFishingStarted() {
        controlPanel.onFishingStart();
        timerPanel.startTimer();
    }

    @Override
    public void onFishingStopped() {
        controlPanel.onFishingStop();
        timerPanel.stopTimer();
    }
}
