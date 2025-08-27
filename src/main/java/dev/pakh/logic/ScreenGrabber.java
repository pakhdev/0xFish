package dev.pakh.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenGrabber {
    public static BufferedImage captureScreen(int xStart, int yStart, int xEnd, int yEnd) {
        System.out.println("xstart " + xStart);
        System.out.println("xend " + xEnd);

        try {
            int width = xEnd - xStart;
            int height = yEnd - yStart;

            Rectangle captureRect = new Rectangle(xStart, yStart, width, height);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(captureRect);

            File output = new File("screenshot_test.png");
            ImageIO.write(image, "png", output);
            System.out.println("Screenshot saved: " + output.getAbsolutePath());

            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
