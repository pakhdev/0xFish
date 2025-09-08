package dev.pakh.utils;

import dev.pakh.models.geometry.RectangleArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
    private static Boolean debugMode = true;

    public static BufferedImage capture(RectangleArea area, String name) {
        Rectangle captureRect = new Rectangle(area.startX(), area.startY(), area.width(), area.height());
        BufferedImage captured = doCapture(captureRect);
        BufferedImage image = normalizeImage(area, captured);
        if (debugMode) saveImage(name, image);
        return image;
    }

    public static BufferedImage crop(RectangleArea area, BufferedImage image, String name) {
        if (!area.isValid())
            throw new IllegalArgumentException("Invalid crop coordinates");

        BufferedImage cropped = image.getSubimage(area.startX(), area.startY(), area.width(), area.height());
        BufferedImage copy = normalizeImage(area, cropped);
        if (debugMode) saveImage(name, copy);
        return copy;
    }

    private static void saveImage(String name, BufferedImage image) {
        try {
            File output = new File(name + ".png");
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage normalizeImage(RectangleArea area, Image source) {
        BufferedImage result = new BufferedImage(area.width(), area.height(), BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(source, 0, 0, null);
        return result;
    }

    private static BufferedImage doCapture(Rectangle rectangle) {
        try {
            Robot robot = new Robot();
            BufferedImage captured = robot.createScreenCapture(rectangle);
            return captured;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
