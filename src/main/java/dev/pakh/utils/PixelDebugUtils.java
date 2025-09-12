package dev.pakh.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelDebugUtils {
    // ==================================== DEV TOOLS ====================================
    public static void printHexVerticalColors(BufferedImage image, int x, int yStart, int yEnd) {
        System.out.println("# Printing pixels in column");
        int counter = 0;
        for (int y = yStart; y <= yEnd; y++) {
            System.out.println("Coord X:" + x + ", Y:" + y + ", Color:" + PixelColorUtils.getHex(image, new Point(x,
                    y)));
            counter++;
        }
        System.out.println("Printed " + counter + " pixels");
    }

    public static void printHexHorizontalColors(BufferedImage image, int y, int xStart, int xEnd) {
        System.out.println("# Printing pixels in row");
        int counter = 0;
        for (int x = xStart; x <= xEnd; x++) {
            System.out.println("Coord X:" + x + ", Y:" + y + ", Color:" + PixelColorUtils.getHex(image, new Point(x,
                    y)));
            counter++;
        }
        System.out.println("Printed " + counter + " pixels");
    }
}
