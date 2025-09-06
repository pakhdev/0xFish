package dev.pakh.utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

public class KeyPressUtils {
    private final Robot robot = new Robot();
    private final int MIN_KEYPRESS_DELAY_MS = 5;
    private final int MAX_KEYPRESS_DELAY_MS = 20;

    public KeyPressUtils() throws AWTException {
    }

    public void pressF(int number) {
        if (number < 1 || number > 12) {
            throw new IllegalArgumentException("Function key must be between 1 and 12");
        }
        int keyCode = KeyEvent.VK_F1 + (number - 1);
        pressKey(keyCode);
    }

    public void pressKey(int keyCode) {
        try {
            robot.keyPress(keyCode);
            int delay = ThreadLocalRandom.current().nextInt(MIN_KEYPRESS_DELAY_MS, MAX_KEYPRESS_DELAY_MS);
            Thread.sleep(delay);
            robot.keyRelease(keyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
