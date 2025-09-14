package dev.pakh.logic.handlers.fishHp;

import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.HorizontalRange;
import dev.pakh.utils.PixelColorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FishHpHandler extends CaptureProcessor {
    private final int BAR_TOP_BORDER_OFFSET = 215;
    private final int BAR_LEFT_BORDER_OFFSET = 112;
    private final int PUMPING_THRESHOLD_MS = 900;
    private final GameLayout gameLayout;
    private final HorizontalRange barRange;

    private int lastHpPosition;
    private long lastHpTimestamp;
    private boolean skipNextComparison = true;

    public FishHpHandler(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        this.barRange = getBarRange();
    }

    @Override
    public void process(BufferedImage image) throws Exception {
        int currentHpPosition = getCurrentHpPosition(image);
        if (currentHpPosition == -1) return;

        if (skipNextComparison) {
            skipNextComparison = false;
            updateHpPosition(currentHpPosition);
            return;
        }

        if (isGreater(currentHpPosition)) {
            handleHpIncrease(currentHpPosition, image);
            return;
        }

        if (isLess(currentHpPosition)) {
            handleHpDecrease(currentHpPosition);
            return;
        }

        if (isEqual(currentHpPosition)) {
            handleHpStable(image);
            return;
        }
    }

    private void handleHpIncrease(int currentPosition, BufferedImage image) {
        boolean success = gameLayout.getReelingSkill().activate(image);
        if (success) {
            skipNextComparison = true;
        } else {
            updateHpPosition(currentPosition);
        }
    }

    private void handleHpDecrease(int currentPosition) {
        updateHpPosition(currentPosition);
        return;
    }

    private void handleHpStable(BufferedImage image) {
        if (!shouldUsePumping()) return;

        boolean success = gameLayout.getPumpingSkill().activate(image);
        if (success) skipNextComparison = true;
    }

    private boolean isGreater(int currentPosition) {
        return currentPosition > lastHpPosition;
    }

    private boolean isLess(int currentPosition) {
        return currentPosition < lastHpPosition;
    }

    private boolean isEqual(int currentPosition) {
        return currentPosition == lastHpPosition;
    }

    private HorizontalRange getBarRange() {
        int startX = gameLayout.getFishingBoxArea().startX() + 11;
        int endX = gameLayout.getFishingBoxArea().startX() + 240;
        int y = gameLayout.getFishingBoxArea().startY() + 221;
        return new HorizontalRange(y, startX, endX);
    }

    private int getCurrentHpPosition(BufferedImage image) {
        if (!isHpVisible(image)) return -1;

        int left = 1;
        int right = 230;

        while (left < right) {
            int middle = (left + right + 1) / 2;
            if (hasHpReached(image, middle)) {
                left = middle;
            } else {
                right = middle - 1;
            }
        }
        return left == 0 && !hasHpReached(image, 0) ? 0 : left;
    }

    private void updateHpPosition(int hpPosition) {
        lastHpPosition = hpPosition;
        lastHpTimestamp = System.currentTimeMillis();
    }

    private boolean shouldUsePumping() {
        return (System.currentTimeMillis() - lastHpTimestamp) >= PUMPING_THRESHOLD_MS;
    }

    private boolean hasHpReached(BufferedImage image, int position) {
        Point point = new Point(barRange.startX() + position, barRange.y());
        return PixelColorUtils.getHSBColor(image, point).h > 75;
    }

    private boolean isHpVisible(BufferedImage image) {
        int borderX = gameLayout.getFishingBoxArea().startX() + BAR_LEFT_BORDER_OFFSET;
        int borderY = gameLayout.getFishingBoxArea().startY() + BAR_TOP_BORDER_OFFSET;
        int borderHue = PixelColorUtils.getHSBColor(image, new Point(borderX, borderY)).h;
        return borderHue < 53;
    }
}
