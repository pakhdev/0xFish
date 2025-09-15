package dev.pakh.ui.components;

import dev.pakh.logic.ports.FishingStateListener;
import dev.pakh.logic.states.FishingState;

import javax.swing.*;

public class StatusPanel implements FishingStateListener {
    private JLabel statusLabel;
    private FishingState state = FishingState.IDLE;
    private String errorMessage;

    public StatusPanel(JFrame frame) {
        setup(frame);
    }

    private void setup(JFrame frame) {
        JLabel statusTitleLabel = new JLabel("Status:");
        statusTitleLabel.setBounds(15, 87, 100, 20);
        statusTitleLabel.setFont(statusTitleLabel.getFont().deriveFont(13f));
        frame.add(statusTitleLabel);

        statusLabel = new JLabel("Idle");
        statusLabel.setBounds(15, 103, 245, 20);
        statusLabel.setFont(statusLabel.getFont().deriveFont(13f));
        frame.add(statusLabel);
    }

    @Override
    public void onDetectUI() {
        setState(FishingState.DETECT_UI);
    }

    @Override
    public void onReady() {
        setState(FishingState.READY);
    }

    @Override
    public void onFishingWait() {
        setState(FishingState.FISHING_WAIT);
    }

    @Override
    public void onFishing() {
        setState(FishingState.FISHING);
    }

    @Override
    public void onError(String message) {
        errorMessage = message;
        setState(FishingState.ERROR);
    }

    private void setState(FishingState state) {
        switch (state) {
            case IDLE -> statusLabel.setText("Idle");
            case READY -> statusLabel.setText("Ready to fish");
            case FISHING_WAIT -> statusLabel.setText("Waiting for fish");
            case FISHING -> statusLabel.setText("Fishing");
            case ERROR -> statusLabel.setText("Error: " + errorMessage);
        }
        this.state = state;
    }
}
