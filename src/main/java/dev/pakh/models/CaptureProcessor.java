package dev.pakh.models;

import java.awt.image.BufferedImage;

public abstract class CaptureProcessor {

    protected int getTimeoutMs = 0;
    private Long lastRunTime = 0L;

    public abstract void process(BufferedImage image) throws Exception;

    public boolean shouldRunNow() {
        long now = System.currentTimeMillis();
        return (now - lastRunTime) >= getTimeoutMs;
    }

    public RectangleArea captureArea() {
        return null;
    }

    public void updateLastRunTime() {
        lastRunTime = System.currentTimeMillis();
    }
}
