package dev.pakh.logic.locators;

import dev.pakh.models.RectangleArea;
import dev.pakh.models.VerticalRange;
import dev.pakh.ui.MessageBox;
import dev.pakh.utils.PixelInspectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Attention!
 * Character Info Box cannot be moved in the game.
 * It can be stretched, but NOT moved, because the colors will change.
 * This may be fixed in the future.
 */
public class CharacterInfoBoxLocator {

    private static Boolean debugMode = true;
    private static final int VALID_LEFT_BORDER_HEIGHT = 78;
    private static final String[] VALID_LEFT_BORDER_COLORS = {
            "AB9C88", "B1A493", "B2A393", "B2A493", "B3A393", "C0B5A6", "C2B8A9"
    };
    private static final int VALID_RIGHT_BORDER_HEIGHT = 62;
    private static final String[] VALID_RIGHT_BORDER_COLORS = {
            "827963", "817963", "817964", "827A65", "847C67", "857D69", "877F6A", "88806C",
            "8A826D", "8B836F", "8D8570", "8E8672", "908873", "918975", "928A76", "938A76",
            "938B77", "948B77", "958C78", "978E7A", "99907C", "9A927F", "9C9481", "9E9683"
    };
    private static final int MIN_DISTANCE_BETWEEN_BORDERS = 175;

    public static RectangleArea locate(BufferedImage image) {
        VerticalRange leftBorderRange = findLeftBorder(image);
        if (leftBorderRange == null) {
            MessageBox.error("Left border of Character Info Box not found");
            return null;
        }

        VerticalRange rightBorderRange = findRightBorder(image, leftBorderRange);
        if (rightBorderRange == null) {
            MessageBox.error("Right border of Character Info Box not found");
            return null;
        }

        return new RectangleArea(
                leftBorderRange.x(),
                rightBorderRange.x(),
                leftBorderRange.startY(),
                leftBorderRange.endY()
        );
    }

    private static VerticalRange findLeftBorder(BufferedImage image) {
        if (debugMode) System.out.println("# Searching left border");
        int currentSearchingPositionX = 0;
        int imageWidth = image.getWidth();

        while (currentSearchingPositionX < imageWidth) {
            Point foundPoint = PixelInspectionUtils.findValidElementRight(
                    image,
                    new Point(currentSearchingPositionX, Math.round((float) VALID_LEFT_BORDER_HEIGHT / 2)),
                    VALID_LEFT_BORDER_COLORS,
                    image.getWidth() - 1,
                    0,
                    VALID_LEFT_BORDER_HEIGHT
            );
            if (foundPoint == null) break;

            if (debugMode) System.out.println("Left border found at X:" + foundPoint.x + ", Y:" + foundPoint.y);
            currentSearchingPositionX = foundPoint.x + 1;

            if (matchesLeftBorderPattern(image, foundPoint))
                return new VerticalRange(foundPoint.x, foundPoint.y, foundPoint.y + VALID_LEFT_BORDER_HEIGHT - 1);
        }
        return null;
    }

    private static Boolean matchesLeftBorderPattern(BufferedImage image, Point point) {
        int x = point.x + 1;
        String firstColor = PixelInspectionUtils.getHex(image, new Point(x, point.y - 1));
        String secondColor = PixelInspectionUtils.getHex(image, new Point(x, point.y));

        if (debugMode)
            System.out.println("Validating left border pattern, first:" + firstColor + ", second:" + secondColor);

        return "C7BFB0".equals(firstColor) && "C2B8A8".equals(secondColor);
    }

    private static VerticalRange findRightBorder(BufferedImage image, VerticalRange leftBorderRange) {
        int currentSearchingPositionX = leftBorderRange.x() + MIN_DISTANCE_BETWEEN_BORDERS + 1;
        int y = Math.round((float) VALID_LEFT_BORDER_HEIGHT / 2);
        int imageWidth = image.getWidth();

        if (debugMode) System.out.println("# Searching right border, X:" + currentSearchingPositionX + ", Y:" + y);

        while (currentSearchingPositionX < imageWidth) {
            Point foundPoint = PixelInspectionUtils.findValidElementRight(
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
                return new VerticalRange(foundPoint.x, foundPoint.y, foundPoint.y + VALID_LEFT_BORDER_HEIGHT - 1);
        }
        return null;
    }

    private static Boolean matchesRightBorderPattern(BufferedImage image, Point point) {
        String firstColor = PixelInspectionUtils.getHex(image, new Point(point.x, 7));
        String secondColor = PixelInspectionUtils.getHex(image, new Point(point.x, 8));

        if (debugMode)
            System.out.println("Validating right border pattern, first:" + firstColor + ", second:" + secondColor);

        return "B7B5A6".equals(firstColor) && "938973".equals(secondColor);
    }
}
