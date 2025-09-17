package dev.pakh.logic.locators;

import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.geometry.VerticalRange;
import dev.pakh.ui.MessageBox;
import dev.pakh.utils.PixelFinderUtils;
import dev.pakh.utils.PixelValidationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Attention!
 * Character Info Box cannot be moved in the game.
 * It can be stretched, but NOT moved, because the colors will change.
 * This may be fixed in the future.
 */
public class CharacterInfoBoxLocator extends CaptureProcessor {

    private final Boolean debugMode = true;
    private final GameLayout gameLayout;
    private final int VALID_LEFT_BORDER_HEIGHT = 78;
    private final String[] VALID_LEFT_BORDER_COLORS = {
            "AB9C88", "B1A493", "B2A393", "B2A493", "B3A393", "C0B5A6", "C2B8A9"
    };
    private final int VALID_RIGHT_BORDER_HEIGHT = 62;
    private final String[] VALID_RIGHT_BORDER_COLORS = {
            "827963", "817963", "817964", "827A65", "847C67", "857D69", "877F6A", "88806C",
            "8A826D", "8B836F", "8D8570", "8E8672", "908873", "918975", "928A76", "938A76",
            "938B77", "948B77", "958C78", "978E7A", "99907C", "9A927F", "9C9481", "9E9683"
    };
    private final int MIN_DISTANCE_BETWEEN_BORDERS = 175;

    public CharacterInfoBoxLocator(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    @Override
    public void process(BufferedImage image) {
        VerticalRange leftBorderRange = findLeftBorder(image);
        if (leftBorderRange == null) return;

        VerticalRange rightBorderRange = findRightBorder(image, leftBorderRange);
        if (rightBorderRange == null) return;

        gameLayout.setCharacterInfoBoxArea(new RectangleArea(
                leftBorderRange.x(),
                rightBorderRange.x(),
                leftBorderRange.startY(),
                leftBorderRange.endY()
        ));
    }

    private VerticalRange findLeftBorder(BufferedImage image) {
        if (debugMode) System.out.println("#Character info box: Searching left border");
        int currentSearchingPositionX = 0;
        int imageWidth = image.getWidth();

        while (currentSearchingPositionX < imageWidth) {
            Point foundPoint = PixelFinderUtils.findValidElementRight(
                    image,
                    new Point(currentSearchingPositionX, Math.round((float) VALID_LEFT_BORDER_HEIGHT / 2)),
                    VALID_LEFT_BORDER_COLORS,
                    image.getWidth() - 1,
                    0,
                    VALID_LEFT_BORDER_HEIGHT
            );
            if (foundPoint == null) break;

            if (debugMode)
                System.out.println("Character info box: Left border found at X:" + foundPoint.x + ", Y:" + foundPoint.y);
            currentSearchingPositionX = foundPoint.x + 1;

            if (matchesLeftBorderPattern(image, foundPoint))
                return new VerticalRange(foundPoint.x, foundPoint.y, foundPoint.y + VALID_LEFT_BORDER_HEIGHT - 1);
        }
        return null;
    }

    private boolean matchesLeftBorderPattern(BufferedImage image, Point point) {
        return PixelValidationUtils.hasValidSignature(
                image,
                point,
                ElementSignaturesManager.find("CharacterInfoBoxLeftBorder")
        );
    }

    private VerticalRange findRightBorder(BufferedImage image, VerticalRange leftBorderRange) {
        int currentSearchingPositionX = leftBorderRange.x() + MIN_DISTANCE_BETWEEN_BORDERS + 1;
        int y = Math.round((float) VALID_LEFT_BORDER_HEIGHT / 2);
        int imageWidth = image.getWidth();

        if (debugMode) System.out.println("# Searching right border, X:" + currentSearchingPositionX + ", Y:" + y);

        while (currentSearchingPositionX < imageWidth) {
            Point foundPoint = PixelFinderUtils.findValidElementRight(
                    image,
                    new Point(currentSearchingPositionX, y),
                    VALID_RIGHT_BORDER_COLORS,
                    image.getWidth() - 1,
                    0,
                    VALID_RIGHT_BORDER_HEIGHT
            );
            if (foundPoint == null) break;

            if (debugMode) System.out.println("Right border found at X:" + foundPoint.x + ", Y:" + foundPoint.y);
            currentSearchingPositionX = foundPoint.x + 1;

            if (matchesRightBorderPattern(image, foundPoint))
                return new VerticalRange(
                        foundPoint.x,
                        foundPoint.y,
                        foundPoint.y + VALID_LEFT_BORDER_HEIGHT - 1);
        }
        return null;
    }

    private boolean matchesRightBorderPattern(BufferedImage image, Point point) {
        return PixelValidationUtils.hasValidSignature(
                image,
                point,
                ElementSignaturesManager.find("CharacterInfoBoxRightBorder")
        );
    }
}
