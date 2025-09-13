package dev.pakh.logic.locators;

import dev.pakh.logic.CaptureDispatcher;
import dev.pakh.logic.handlers.countdownDetection.CountdownDetectionHandler;
import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.geometry.VerticalRange;
import dev.pakh.utils.PixelFinderUtils;
import dev.pakh.utils.PixelValidationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FishingBoxLocator extends CaptureProcessor {
    private final boolean debugMode = false;
    private final int TOP_LOCATION_OFFSET = 300;
    private final int BOTTOM_LOCATION_OFFSET = 50;
    private final int VALID_BOTTOM_BORDER_WIDTH = 258;
    private final String[] VALID_BORDER_COLORS = {
            "B2A38D", "B3A48E", "B9A892", "C4B39A", "CCBAA0", "D0BCA6", "D7C7AB", "E9DFC0", "EADFBE"
    };
    private final GameLayout gameLayout;
    private final CaptureDispatcher captureDispatcher;

    public FishingBoxLocator(GameLayout gameLayout, CaptureDispatcher captureDispatcher) {
        this.gameLayout = gameLayout;
        this.captureDispatcher = captureDispatcher;
    }

    @Override
    public void process(BufferedImage image) {
        List<VerticalRange> searchingRanges = calculateSearchingRanges(image);
        for (VerticalRange range : searchingRanges) {
            if (debugMode) System.out.printf(
                    "Searching fishing box at X: %d, Y START: %d, Y END: %d%n",
                    range.x(), range.startY(), range.endY());
            Point bottomBorder = findBottomBorder(image, range);
            if (bottomBorder == null) continue;
            if (matchesBottomBorderPattern(image, bottomBorder)) {
                RectangleArea area = calculateFishingBoxArea(bottomBorder);
                gameLayout.setFishingBoxArea(area);
                break;
            }
        }
        if (!gameLayout.isFishingBoxDetected()) throw new RuntimeException("Fishing box not found");

        captureDispatcher.subscribe(new CountdownDetectionHandler(gameLayout, captureDispatcher));
    }

    private RectangleArea calculateFishingBoxArea(Point bottomBorder) {
        int startX = (int) bottomBorder.getX() + 3;
        int endX = (int) bottomBorder.getX() + 255;
        int startY = (int) bottomBorder.getY() - 261;
        int endY = (int) bottomBorder.getY() - 9;
        return new RectangleArea(startX, endX, startY, endY);
    }

    private Point findBottomBorder(BufferedImage image, VerticalRange range) {
        return PixelFinderUtils.findValidElementUp(
                image,
                new Point(range.x(), range.endY()),
                VALID_BORDER_COLORS,
                range.startY(),
                VALID_BOTTOM_BORDER_WIDTH,
                0
        );
    }

    private boolean matchesBottomBorderPattern(BufferedImage image, Point point) {
        return PixelValidationUtils.hasValidSignature(image, point, ElementSignaturesManager.find("FishingBoxBottomBorder"));
    }

    private List<VerticalRange> calculateSearchingRanges(BufferedImage image) {
        List<VerticalRange> searchingRanges = new ArrayList<>();
        RectangleArea searchingArea = calculateSearchingArea(image);
        int x = searchingArea.startX();
        while (x <= searchingArea.endX()) {
            searchingRanges.add(new VerticalRange(
                    x,
                    searchingArea.startY(),
                    searchingArea.endY()
            ));
            x += VALID_BOTTOM_BORDER_WIDTH;
        }
        return searchingRanges;
    }

    private RectangleArea calculateSearchingArea(BufferedImage image) {
        int horizontalOffset = VALID_BOTTOM_BORDER_WIDTH / 2;
        int startX = horizontalOffset;
        int endX = image.getWidth() - horizontalOffset;
        return new RectangleArea(startX, endX, TOP_LOCATION_OFFSET, image.getHeight() - BOTTOM_LOCATION_OFFSET);
    }
}
