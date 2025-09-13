package dev.pakh.utils;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PixelValidationUtils {
    private static boolean debugMode = false;

    public static boolean hasValidSignature(BufferedImage image, Point point, ElementVisualSignature visualSignature) {
        if (debugMode) System.out.printf("# Validating signature for %s%n", visualSignature.name());
        for (ColorPoint colorPoint : visualSignature.signature()) {
            int x = point.x + (int) colorPoint.offset().getX();
            int y = point.y + (int) colorPoint.offset().getY();
            String foundColor = PixelColorUtils.getHex(image, new Point(x, y));
            if (debugMode)
                System.out.printf(
                        "Validating point color at X: %d, Y: %d. Target: %s, found: %s%n",
                        x, y, colorPoint.hexColor(), foundColor
                );
            if (!Objects.equals(colorPoint.hexColor(), foundColor)) return false;
        }
        return true;
    }

    public static Boolean isValidHeight(BufferedImage image, Point point, String[] validColors, int targetHeight) {
        if (targetHeight == 0) return true;
        // Clamp the measurement within the image bounds, but extend by (+1/-1) pixel beyond the required height
        // to ensure that if the actual line is taller than required, it will still be detected correctly.
        int validPixelsUp = PixelCounterUtils.countConsecutiveValidPixelsUp(image, point, targetHeight - 1,
                validColors);
        int validPixelsDown = PixelCounterUtils.countConsecutiveValidPixelsDown(image, point, targetHeight + 1,
                validColors);
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
        int validPixelsLeft = PixelCounterUtils.countConsecutiveValidPixelsLeft(image, point, validWidth - 1,
                validColors);
        int validPixelsRight = PixelCounterUtils.countConsecutiveValidPixelsRight(image, point, validWidth + 1,
                validColors);

        boolean isValid = (validPixelsLeft + validPixelsRight + 1) == validWidth;

        if (debugMode)
            System.out.printf("Width validation, target: %d, valid left: %d, valid right: %d, is valid: %b%n",
                    validWidth, validPixelsLeft, validPixelsRight, isValid);

        return isValid;
    }
}
