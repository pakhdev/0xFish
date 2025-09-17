package dev.pakh.logic.locators;

import dev.pakh.logic.CaptureDispatcher;
import dev.pakh.logic.FishingWorkflow;
import dev.pakh.logic.handlers.countdownDetection.CountdownDetectionHandler;
import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.HorizontalRange;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.geometry.VerticalRange;
import dev.pakh.utils.PixelFinderUtils;
import dev.pakh.utils.PixelValidationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MonsterBoxLocator extends CaptureProcessor {
    private final boolean debugMode = true;
    private final int VALID_LEFT_BORDER_HEIGHT = 42;
    private final String[] VALID_BORDER_COLORS = {
            "AB9C88", "B1A493", "B2A493", "B2A393", "B3A393", "C0B5A6", "C2B8A9"
    };
    private final int SEARCH_HORIZONTAL_OFFSET = 200;
    private final int SEARCH_LINES_LIMIT = 10;
    private final GameLayout gameLayout;
    private final CaptureDispatcher captureDispatcher;
    private final FishingWorkflow fishingWorkflow;

    public MonsterBoxLocator(GameLayout gameLayout, CaptureDispatcher captureDispatcher, FishingWorkflow fishingWorkflow) {
        this.gameLayout = gameLayout;
        this.captureDispatcher = captureDispatcher;
        this.fishingWorkflow = fishingWorkflow;
    }

    @Override
    public void process(BufferedImage image) {
        List<HorizontalRange> searchingRanges = calculateSearchingRanges(image);
        for (HorizontalRange range : searchingRanges) {
            if (debugMode)
                System.out.printf("Searching monster box at X: %d-%d, Y: %d%n", range.startX(), range.endX(),
                        range.y());
            Point leftBorder = findLeftBorder(image, range);
            if (leftBorder == null) continue;
            if (matchesLeftBorderPattern(image, leftBorder)) {
                Point hpPoint = calculateMonsterHpPoint(leftBorder);
                gameLayout.setMonsterHpBar(hpPoint);
                break;
            }
        }
        if (!gameLayout.isMonsterHpBarDetected()) return;
        captureDispatcher.unsubscribeByClass(MonsterBoxLocator.class);
        fishingWorkflow.killMonster();
    }

    private Point calculateMonsterHpPoint(Point leftBorder) {
        int x = (int) leftBorder.getX() + 19;
        int y = (int) leftBorder.getY() + 26;
        return new Point(x, y);
    }

    private Point findLeftBorder(BufferedImage image, HorizontalRange range) {
        return PixelFinderUtils.findValidElementRight(
                image,
                new Point(range.startX(), range.y()),
                VALID_BORDER_COLORS,
                range.endX(),
                0,
                VALID_LEFT_BORDER_HEIGHT
        );
    }

    private boolean matchesLeftBorderPattern(BufferedImage image, Point point) {
        return PixelValidationUtils.hasValidSignature(image, point, ElementSignaturesManager.find("MonsterBoxLeftBorder"));
    }

    private List<HorizontalRange> calculateSearchingRanges(BufferedImage image) {
        List<HorizontalRange> searchingRanges = new ArrayList<>();
        RectangleArea searchingArea = calculateSearchingArea(image);
        int y = searchingArea.startY();
        while (y <= searchingArea.endY()) {
            searchingRanges.add(new HorizontalRange(
                    y,
                    searchingArea.startX(),
                    searchingArea.endX()
            ));
            y += VALID_LEFT_BORDER_HEIGHT;
        }
        return searchingRanges;
    }

    private RectangleArea calculateSearchingArea(BufferedImage image) {
        int startX = SEARCH_HORIZONTAL_OFFSET;
        int endX = image.getWidth() - SEARCH_HORIZONTAL_OFFSET;
        int startY = VALID_LEFT_BORDER_HEIGHT / 2;
        int endY = startY + VALID_LEFT_BORDER_HEIGHT * SEARCH_LINES_LIMIT;
        return new RectangleArea(startX, endX, startY, endY);
    }
}
