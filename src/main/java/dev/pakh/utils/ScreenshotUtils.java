package dev.pakh.utils;

import dev.pakh.models.geometry.RectangleArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
    private static Boolean debugMode = false;

    public static BufferedImage capture(RectangleArea area, String name) {
        Rectangle captureRect = new Rectangle(area.startX(), area.startY(), area.width(), area.height());
        BufferedImage image = doCapture(captureRect);
        if (debugMode) saveImage(name, image);
        return image;
    }

    public static BufferedImage crop(RectangleArea area, BufferedImage image, String name) {
        if (!area.isValid())
            throw new IllegalArgumentException("Invalid crop coordinates");

        BufferedImage cropped = image.getSubimage(area.startX(), area.startY(), area.width(), area.height());
        if (debugMode) saveImage(name, cropped);
        return cropped;
    }

    private static void saveImage(String name, BufferedImage image) {
        try {
            File output = new File(name + ".png");
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
