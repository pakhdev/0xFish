package dev.pakh.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelFinderUtils {
    private static Boolean debugMode = true;

    public static Point findValidElementLeft(
            BufferedImage image,
            Point point,
            String[] validColors,
            int limitX,
            int requiredWidth,
            int requiredHeight
    ) {
        if (debugMode) LoggerUtils.logCall("(X:%d-%d, Y:%d)", point.y, point.x, limitX);

        int y = point.y;
        for (int x = point.x; x >= limitX; x--) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                if (!PixelValidationUtils.isValidHeight(image, new Point(x, y), validColors, requiredHeight))
                    continue;

                if (!PixelValidationUtils.isValidWidth(image, new Point(x, y), validColors, requiredWidth))
                    continue;

                return new Point(x, y);
            }
        }
        return null;
    }

    public static Point findValidElementRight(
            BufferedImage image,
            Point point,
            String[] validColors,
            int limitX,
            int requiredWidth,
            int requiredHeight
    ) {
        if (debugMode) LoggerUtils.logCall("(X:%d-%d, Y:%d)", point.y, point.x, limitX);

        int y = point.y;
        for (int x = point.x; x <= limitX; x++) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                if (!PixelValidationUtils.isValidHeight(image, new Point(x, y), validColors, requiredHeight))
                    continue;

                if (!PixelValidationUtils.isValidWidth(image, new Point(x, y), validColors, requiredWidth))
                    continue;

                int topYCoord = PixelCounterUtils.countConsecutiveValidPixelsUp(image, new Point(x, y), requiredHeight,
                        validColors);
                return new Point(x, (y - topYCoord));
            }
        }
        return null;
    }

    public static Point findValidElementUp(
            BufferedImage image,
            Point point,
            String[] validColors,
            int limitY,
            int requiredWidth,
            int requiredHeight
    ) {
        if (debugMode) LoggerUtils.logCall("(X:%d, Y:%d-%d)", point.x, point.y, limitY);

        int x = point.x;
        for (int y = point.y; y >= limitY; y--) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                if (!PixelValidationUtils.isValidHeight(image, new Point(x, y), validColors, requiredHeight))
                    continue;

                if (!PixelValidationUtils.isValidWidth(image, new Point(x, y), validColors, requiredWidth))
                    continue;

                int topYCoord = PixelCounterUtils.countConsecutiveValidPixelsUp(image, new Point(x, y), requiredHeight,
                        validColors);
                int leftXCoord = PixelCounterUtils.countConsecutiveValidPixelsLeft(image, new Point(x, y),
                        requiredWidth,
                        validColors);
                return new Point((x - leftXCoord), (y - topYCoord));
            }
        }
        return null;
    }
}
