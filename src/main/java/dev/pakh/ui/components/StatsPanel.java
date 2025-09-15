package dev.pakh.ui.components;

import dev.pakh.logic.ports.FishingStatsListener;

import javax.swing.*;

public class StatsPanel implements FishingStatsListener {
    private final JFrame frame;
    private final int leftX = 15;
    private final int rightX = 120;
    private final int rowHeight = 16;
    private int leftRow = 0;
    private int rightRow = 0;

    private JLabel fishCounterLabel;
    private JLabel oldBoxCounterLabel;
    private JLabel monsterCounterLabel;

    private JLabel pumpingAttemptedLabel;
    private JLabel pumpingFailedLabel;
    private JLabel incorrectPumpingLabel;

    private JLabel reelingAttemptedLabel;
    private JLabel reelingFailedLabel;
    private JLabel incorrectReelingLabel;

    private int fishCount = 0;
    private int boxCount = 0;
    private int monsterCount = 0;

    private int pumpingAttempted = 0;
    private int pumpingFailed = 0;
    private int incorrectPumping = 0;

    private int reelingAttempted = 0;
    private int reelingFailed = 0;
    private int incorrectReeling = 0;

    public StatsPanel(JFrame frame) {
        this.frame = frame;
        setup();
    }

    private void setup() {
        fishCounterLabel = createLabel("Fish: 0", leftX, leftRow++);
        oldBoxCounterLabel = createLabel("Old Boxes: 0", leftX, leftRow++);
        monsterCounterLabel = createLabel("Monsters: 0", leftX, leftRow++);

        pumpingAttemptedLabel = createLabel("Pumping Attempted: 0", rightX, rightRow++);
        pumpingFailedLabel = createLabel("Pumping Failed: 0", rightX, rightRow++);
        incorrectPumpingLabel = createLabel("Incorrect Pumping: 0", rightX, rightRow++);

        reelingAttemptedLabel = createLabel("Reeling Attempted: 0", rightX, rightRow++);
        reelingFailedLabel = createLabel("Reeling Failed: 0", rightX, rightRow++);
        incorrectReelingLabel = createLabel("Incorrect Reeling: 0", rightX, rightRow++);
    }

    private JLabel createLabel(String text, int x, int row) {
        JLabel label = new JLabel(text);
        label.setBounds(x, 7 + row * rowHeight, 200, 16);
        label.setFont(label.getFont().deriveFont(13f));
        frame.add(label);
        return label;
    }

    @Override
    public void onFishCaught() {
        fishCount++;
        updateLabel(fishCounterLabel, "Fish: " + fishCount);
    }

    @Override
    public void onOldBoxCaught() {
        boxCount++;
        updateLabel(oldBoxCounterLabel, "Old Boxes: " + boxCount);
    }

    @Override
    public void onMonsterCaught() {
        monsterCount++;
        updateLabel(monsterCounterLabel, "Monsters: " + monsterCount);
    }

    @Override
    public void onPumpingAttempted() {
        pumpingAttempted++;
        updateLabel(pumpingAttemptedLabel, "Pumping Attempted: " + pumpingAttempted);
    }

    @Override
    public void onPumpingFailed() {
        pumpingFailed++;
        updateLabel(pumpingFailedLabel, "Pumping Failed: " + pumpingFailed);
    }

    @Override
    public void onIncorrectPumping() {
        incorrectPumping++;
        updateLabel(incorrectPumpingLabel, "Incorrect Pumping: " + incorrectPumping);
    }

    @Override
    public void onReelingAttempted() {
        reelingAttempted++;
        updateLabel(reelingAttemptedLabel, "Reeling Attempted: " + reelingAttempted);
    }

    @Override
    public void onReelingFailed() {
        reelingFailed++;
        updateLabel(reelingFailedLabel, "Reeling Failed: " + reelingFailed);
    }

    @Override
    public void onIncorrectReeling() {
        incorrectReeling++;
        updateLabel(incorrectReelingLabel, "Incorrect Reeling: " + incorrectReeling);
    }

    private void updateLabel(JLabel label, String text) {
        SwingUtilities.invokeLater(() -> label.setText(text));
    }
}

