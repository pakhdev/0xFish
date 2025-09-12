package dev.pakh.utils;

import dev.pakh.models.capture.ColorHSB;
import dev.pakh.models.capture.ColorRGB;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PixelColorUtils {
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
}
