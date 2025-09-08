package dev.pakh.models.capture;

import dev.pakh.models.geometry.RectangleArea;

import java.awt.image.BufferedImage;

public abstract class CaptureProcessor {

    private Long lastRunTime = 0L;

    public abstract void process(BufferedImage image) throws Exception;

    protected int getTimeoutMs() {
        return 0;
    }

    public boolean shouldRunNow() {
        long now = System.currentTimeMillis();
        return (now - lastRunTime) >= getTimeoutMs();
    }

    public RectangleArea captureArea() {
        return null;
    }

    public void updateLastRunTime() {
        lastRunTime = System.currentTimeMillis();
    }
}
