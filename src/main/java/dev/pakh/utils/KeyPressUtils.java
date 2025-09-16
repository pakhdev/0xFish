package dev.pakh.utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.*;

public class KeyPressUtils {
    private final Robot robot = new Robot();
    private final int MIN_KEYPRESS_DELAY_MS = 5;
    private final int MAX_KEYPRESS_DELAY_MS = 20;

    private final PriorityBlockingQueue<KeyEventTask> queue =
            new PriorityBlockingQueue<>(10, Comparator.comparingLong(t -> t.executeAt));

    private volatile boolean isProcessing = false;

    public KeyPressUtils() throws AWTException {
    }

    public void pressF(int number) {
        if (number < 1 || number > 12) {
            throw new IllegalArgumentException("Function key must be between 1 and 12");
        }
        int keyCode = KeyEvent.VK_F1 + (number - 1);
        enqueue(new KeyEventTask(keyCode, 0));
    }

    public void pressFWithInterval(int number, int count, int intervalMs) {
        if (number < 1 || number > 12) {
            throw new IllegalArgumentException("Function key must be between 1 и 12");
        }
        int keyCode = KeyEvent.VK_F1 + (number - 1);
        long now = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            long executeAt = now + (long) i * intervalMs;
            enqueue(new KeyEventTask(keyCode, executeAt));
        }
    }

    private synchronized void enqueue(KeyEventTask task) {
        queue.add(task);

        if (!isProcessing) {
            isProcessing = true;
            processNext();
        }
    }

    private void processNext() {
        new Thread(() -> {
            try {
                while (true) {
                    KeyEventTask task = queue.poll(100, TimeUnit.MILLISECONDS);
                    if (task == null) {
                        break;
                    }

                    long delay = task.executeAt - System.currentTimeMillis();
                    if (delay > 0) {
                        Thread.sleep(delay);
                    }

                    pressKeyImmediate(task.keyCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isProcessing = false;
                if (!queue.isEmpty()) {
                    enqueue(queue.poll());
                }
            }
        }).start();
    }

    private void pressKeyImmediate(int keyCode) {
        try {
            robot.keyPress(keyCode);
            int delay = ThreadLocalRandom.current().nextInt(MIN_KEYPRESS_DELAY_MS, MAX_KEYPRESS_DELAY_MS);
            Thread.sleep(delay);
            robot.keyRelease(keyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class KeyEventTask {
        final int keyCode;
        final long executeAt;

        KeyEventTask(int keyCode, long executeAt) {
            this.keyCode = keyCode;
            this.executeAt = executeAt;
        }
    }
}

