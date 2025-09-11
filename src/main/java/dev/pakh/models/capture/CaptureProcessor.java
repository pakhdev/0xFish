package dev.pakh.models.capture;

import dev.pakh.models.geometry.RectangleArea;

import java.awt.image.BufferedImage;

/**
 * Abstract class representing a processor that handles a captured image.
 * <p>
 * Each subclass must implement the {@link #process(BufferedImage)} method
 * to define how the captured image should be analyzed or processed.
 * <p>
 * This class implements a tick-based timeout mechanism, allowing subscribers
 * to skip processing certain captures if they do not need to run every tick.
 * <p>
 * Usage:
 * <ul>
 *   <li>Subclass {@code CaptureProcessor} and implement {@link #process(BufferedImage)}.</li>
 *   <li>Optionally override {@link #getTimeoutTicks()} to define how many ticks
 *       must pass before the processor runs again.</li>
 *   <li>Call {@link #shouldRunNow()} each tick to check if the processor
 *       should execute. This method automatically increments the tick counter
 *       and resets it when the timeout is reached.</li>
 *   <li>If needed, {@link #resetLastRunTick()} can be called manually to
 *       reset the tick counter.</li>
 *   <li>Optionally override {@link #captureArea()} to define a custom screen
 *       area for this processor.</li>
 * </ul>
 * <p>
 * Note: The tick mechanism ensures that all processors receive the same
 * capture even if they run at different intervals, avoiding timing issues
 * that could occur with a millisecond-based timeout.
 */
public abstract class CaptureProcessor {

    /**
     * Number of ticks passed since the last execution of this processor
     */
    protected int ticksSinceLastRun = 0;

    /**
     * Processes the captured image. Must be implemented by subclasses.
     *
     * @param image the captured {@link BufferedImage}
     * @throws Exception if processing fails
     */
    public abstract void process(BufferedImage image) throws Exception;

    /**
     * Returns the number of ticks that must pass before this processor should run again.
     * Subclasses should override this method to define their own timeout.
     *
     * @return the timeout in ticks (default is 0, meaning run every tick)
     */
    protected int getTimeoutTicks() {
        return 0;
    }

    /**
     * Checks if the processor should run on this tick.
     * <p>
     * Each call increments the internal tick counter. If the number of
     * ticks since the last run is greater than or equal to {@link #getTimeoutTicks()},
     * the counter is reset and {@code true} is returned.
     *
     * @return {@code true} if the processor should run, {@code false} otherwise
     */
    public boolean shouldRunNow() {
        ticksSinceLastRun++;
        if (ticksSinceLastRun >= getTimeoutTicks()) {
            ticksSinceLastRun = 0;
            return true;
        }
        return false;
    }

    /**
     * Resets the internal tick counter to 0.
     * Can be used to manually force a reset of the timeout.
     */
    public void resetLastRunTick() {
        ticksSinceLastRun = 0;
    }

    /**
     * Returns the screen area to capture for this processor.
     * <p>
     * By default, returns {@code null}, which means the full screen will be captured.
     * Subclasses can override this method to define a custom capture rectangle.
     *
     * @return the {@link RectangleArea} to capture, or {@code null} for full screen
     */
    public RectangleArea captureArea() {
        return null;
    }
}
