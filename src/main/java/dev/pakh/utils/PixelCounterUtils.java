package dev.pakh.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelCounterUtils {
    private static boolean debugMode = false;

    public static int countConsecutiveValidPixelsLeft(BufferedImage image, Point point, int limit,
                                                      String[] validColors) {
        int y = point.y;
        int xStart = point.x;
        int xLimit = Math.max(0, point.x - limit);
        int count = 0;

        for (int x = xStart - 1; x >= xLimit; x--) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                count++;
            } else break;
        }
        return count;
    }

    public static int countConsecutiveValidPixelsRight(BufferedImage image, Point point, int limit,
                                                       String[] validColors) {
        int y = point.y;
        int xStart = point.x;
        int xLimit = Math.min(image.getWidth() - 1, point.x + limit);
        int count = 0;

        for (int x = xStart + 1; x <= xLimit; x++) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                count++;
            } else break;
        }
        return count;
    }

    public static int countConsecutiveValidPixelsUp(BufferedImage image, Point point, int limit, String[] validColors) {
        int x = point.x;
        int yStart = point.y;
        int yLimit = Math.max(0, yStart - limit);
        int count = 0;

        if (debugMode) LoggerUtils.logCall("(X:%d, Y:%d-%d)", x, (yStart - 1), yLimit);

        for (int y = yStart - 1; y >= yLimit; y--) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                count++;
            } else break;
        }
        return count;
    }

    public static int countConsecutiveValidPixelsDown(BufferedImage image, Point point, int limit,
                                                      String[] validColors) {
        int x = point.x;
        int yStart = point.y;
        int yLimit = Math.min(image.getHeight() - 1, point.y + limit);
        int count = 0;

        if (debugMode) LoggerUtils.logCall("(X:%d, Y:%d-%d)", x, (yStart + 1), yLimit);

        for (int y = yStart + 1; y <= yLimit; y++) {
            if (PixelColorUtils.hasValidColors(image, new Point(x, y), validColors)) {
                count++;
            } else break;
        }
        return count;
    }
}
