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

    public void pressF1() {
        pressKey(KeyEvent.VK_F1);
    }

    public void pressF2() {
        pressKey(KeyEvent.VK_F2);
    }

    public void pressF3() {
        pressKey(KeyEvent.VK_F3);
    }

    public void pressF4() {
        pressKey(KeyEvent.VK_F4);
    }

    public void pressF5() {
        pressKey(KeyEvent.VK_F5);
    }

    public void pressF6() {
        pressKey(KeyEvent.VK_F6);
    }

    public void pressF7() {
        pressKey(KeyEvent.VK_F7);
    }

    public void pressF8() {
        pressKey(KeyEvent.VK_F8);
    }

    public void pressF9() {
        pressKey(KeyEvent.VK_F9);
    }

    public void pressF10() {
        pressKey(KeyEvent.VK_F10);
    }

    public void pressF11() {
        pressKey(KeyEvent.VK_F11);
    }

    public void pressF12() {
        pressKey(KeyEvent.VK_F12);
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
