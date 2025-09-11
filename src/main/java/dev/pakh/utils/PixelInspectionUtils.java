package dev.pakh.utils;

import dev.pakh.models.capture.ColorHSB;
import dev.pakh.models.capture.ColorRGB;
import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

public class PixelInspectionUtils {
    private static Boolean debugMode = false;

    public static Point findValidElementLeft(
            BufferedImage image,
            Point point,
            String[] validColors,
            int limitX,
            int requiredWidth,
            int requiredHeight
    ) {
        if (debugMode)
            System.out.println("Searching valid element left, X:" + point.x + ", Y:" + point.y + ", limitX:" + limitX);

        int y = point.y;
        for (int x = point.x; x >= limitX; x--) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
                if (!isValidHeight(image, new Point(x, y), validColors, requiredHeight))
                    continue;

                if (!isValidWidth(image, new Point(x, y), validColors, requiredWidth))
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
        if (debugMode)
            System.out.println("Searching valid element right, X:" + point.x + ", Y:" + point.y + ", limitX:" + limitX);

        int y = point.y;
        for (int x = point.x; x <= limitX; x++) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
                if (!isValidHeight(image, new Point(x, y), validColors, requiredHeight))
                    continue;

                if (!isValidWidth(image, new Point(x, y), validColors, requiredWidth))
                    continue;

                int topYCoord = countConsecutiveValidPixelsUp(image, new Point(x, y), requiredHeight, validColors);
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
        if (debugMode)
            System.out.println("Searching valid element up, X:" + point.x + ", Y:" + point.y + ", limitY:" + limitY);

        int x = point.x;
        for (int y = point.y; x <= limitY; y--) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
                if (!isValidHeight(image, new Point(x, y), validColors, requiredHeight))
                    continue;

                if (!isValidWidth(image, new Point(x, y), validColors, requiredWidth))
                    continue;

                int topYCoord = countConsecutiveValidPixelsUp(image, new Point(x, y), requiredHeight, validColors);
                return new Point(x, (y - topYCoord));
            }
        }
        return null;
    }

    public static boolean hasValidSignature(BufferedImage image, Point point, ElementVisualSignature visualSignature) {
        if (debugMode) System.out.printf("# Validating signature for %s%n", visualSignature.name());
        for (ColorPoint colorPoint : visualSignature.signature()) {
            int x = point.x + (int) colorPoint.offset().getX();
            int y = point.y + (int) colorPoint.offset().getY();
            String foundColor = getHex(image, new Point(x, y));
            if (debugMode)
                System.out.printf(
                        "Validating point color at X: %d, Y: %d. Target: %s, found: %s%n",
                        x, y, colorPoint.hexColor(), foundColor
                );
            if (!Objects.equals(colorPoint.hexColor(), foundColor)) return false;
        }
        return true;
    }

    // Проверить
    public static int countConsecutiveValidPixelsLeft(BufferedImage image, Point point, int limit,
                                                      String[] validColors) {
        int y = point.y;
        int xStart = point.x;
        int xLimit = Math.max(0, point.x - limit);
        int count = 0;

        for (int x = xStart - 1; x >= xLimit; x--) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
                count++;
            } else break;
        }
        return count;
    }

    // Проверить
    public static int countConsecutiveValidPixelsRight(BufferedImage image, Point point, int limit,
                                                       String[] validColors) {
        int y = point.y;
        int xStart = point.x;
        int xLimit = Math.min(image.getWidth() - 1, point.x + limit);
        int count = 0;

        for (int x = xStart + 1; x <= xLimit; x++) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
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

        if (debugMode) System.out.println("Counting valid pixels up from X:" + x
                + ", Y:" + (yStart - 1)
                + ", Y limit:" + yLimit);

        for (int y = yStart - 1; y >= yLimit; y--) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
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

        if (debugMode) System.out.println("Counting valid pixels down from X:" + x
                + ", Y:" + (yStart + 1)
                + ", Y limit:" + yLimit);

        for (int y = yStart + 1; y <= yLimit; y++) {
            if (hasValidColors(image, new Point(x, y), validColors)) {
                count++;
            } else break;
        }
        return count;
    }

    public static Boolean hasValidColors(BufferedImage image, Point point, String[] validColors) {
        String pixelColor = getHex(image, point);
        return Arrays.asList(validColors).contains(pixelColor);
    }

    public static String getHex(BufferedImage image, Point point) {
        int rgb = image.getRGB(point.x, point.y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        String hex = String.format("%02X%02X%02X", red, green, blue);
        return hex;
    }

    public static ColorRGB getRGBColor(BufferedImage image, Point point) {
        int rgb = image.getRGB(point.x, point.y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return new ColorRGB(red, green, blue);
    }

    public static ColorHSB getHSBColor(BufferedImage image, Point point) {
        int rgb = image.getRGB(point.x, point.y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        float[] hsb = Color.RGBtoHSB(red, green, blue, null);

        int hue = Math.round(hsb[0] * 360);
        int sat = Math.round(hsb[1] * 100);
        int bri = Math.round(hsb[2] * 100);

        return new ColorHSB(hue, sat, bri);
    }

    public static Boolean isValidHeight(BufferedImage image, Point point, String[] validColors, int targetHeight) {
        if (targetHeight == 0) return true;
        // Clamp the measurement within the image bounds, but extend by (+1/-1) pixel beyond the required height
        // to ensure that if the actual line is taller than required, it will still be detected correctly.
        int validPixelsUp = countConsecutiveValidPixelsUp(image, point, targetHeight - 1, validColors);
        int validPixelsDown = countConsecutiveValidPixelsDown(image, point, targetHeight + 1, validColors);
        int totalValid = validPixelsUp + validPixelsDown + 1;

        if (debugMode)
            System.out.println("Height validation, target:" + targetHeight
                    + ", valid up:" + validPixelsUp
                    + ", valid down:" + validPixelsDown
                    + ", total valid:" + totalValid);

        return totalValid == targetHeight;
    }

    public static Boolean isValidWidth(BufferedImage image, Point point, String[] validColors, int validWidth) {
        if (validWidth == 0) return true;

        // Clamp the measurement within the image bounds, but extend by (+1/-1) pixel beyond the required width
        // to ensure that if the actual line is smaller than required, it will still be detected correctly.
        int validPixelsLeft = countConsecutiveValidPixelsLeft(image, point, validWidth - 1, validColors);
        int validPixelsRight = countConsecutiveValidPixelsRight(image, point, validWidth + 1, validColors);

        if (debugMode)
            System.out.println("Width validation, target:" + validWidth
                    + "valid left:" + validPixelsLeft + ","
                    + " valid right:" + validPixelsRight);

        return (validPixelsLeft + validPixelsRight + 1) == validWidth;
    }

    // ==================================== DEV TOOLS ====================================
    public static void printHexVerticalColors(BufferedImage image, int x, int yStart, int yEnd) {
        System.out.println("# Printing pixels in column");
        int counter = 0;
        for (int y = yStart; y <= yEnd; y++) {
            System.out.println("Coord X:" + x + ", Y:" + y + ", Color:" + getHex(image, new Point(x, y)));
            counter++;
        }
        System.out.println("Printed " + counter + " pixels");
    }

    public static void printHexHorizontalColors(BufferedImage image, int y, int xStart, int xEnd) {
        System.out.println("# Printing pixels in row");
        int counter = 0;
        for (int x = xStart; x <= xEnd; x++) {
            System.out.println("Coord X:" + x + ", Y:" + y + ", Color:" + getHex(image, new Point(x, y)));
            counter++;
        }
        System.out.println("Printed " + counter + " pixels");
    }
}
