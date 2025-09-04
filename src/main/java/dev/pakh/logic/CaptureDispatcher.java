package dev.pakh.logic;

import dev.pakh.models.GameWindow;
import dev.pakh.models.CaptureProcessor;
import dev.pakh.models.RectangleArea;
import dev.pakh.utils.ScreenshotUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class is responsible for capturing the game screen and distributing
 * the captured image to all subscribed processors.
 * <p>
 * The main goal is to optimize performance and avoid multiple screen captures
 * for each individual processor.
 * <p>
 * Usage:
 * <ul>
 *   <li>Before using this class, call {@link #init()} to locate the game window
 *       and determine its position and size.</li>
 *   <li>All subscribers must implement the {@code Processor} interface.</li>
 *   <li>If a subscriber requires an image with a smaller resolution than the
 *       captured one, the image will be automatically cropped to the coordinates
 *       provided by the subscriber.</li>
 *   <li>A subscriber may define a timeout, meaning it does not need to process
 *       every dispatch. In that case, the subscriber will only receive an image
 *       when its {@code shouldRunNow()} method returns {@code true}.</li>
 *   <li>For one-time processing, use {@link #subscribeForSingleRun(CaptureProcessor)}.
 *       Such a subscriber will be automatically unsubscribed after its first run.</li>
 *   <li>The {@link #dispatch()} method performs the screen capture and
 *       distributes it to all active subscribers.</li>
 * </ul>
 */
public class CaptureDispatcher {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<CaptureProcessor, Boolean> subscribers = new HashMap<>();
    private final GameWindow gameWindow = new GameWindow();

    /**
     * Initializes the dispatcher by locating the game window.
     *
     * @return {@code true} if the game window was successfully found,
     * {@code null} otherwise
     */
    public CaptureDispatcher init() {
        if (gameWindow.identify() != null)
            gameWindow.activateWindow();
        return this;
    }

    /**
     * Captures the screen and dispatches the image to all subscribers.
     * Each processor is executed asynchronously in the internal thread pool.
     * <p>
     * This method returns immediately after submitting the tasks, without waiting
     * for them to complete. If synchronous execution is required, use
     * {@link #dispatchAndWait()} instead.
     *
     * @return a list of {@link Future} objects representing the submitted tasks
     */
    public List<Future<?>> dispatch() {
        List<Map.Entry<CaptureProcessor, Boolean>> toRun =
                subscribers.entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().shouldRunNow())
                        .toList();

        if (!gameWindow.isIdentified() || toRun.isEmpty()) return List.of();
        List<Future<?>> futures = new ArrayList<>();

        BufferedImage fullScreenshot = ScreenshotUtils.capture(gameWindow.getArea(), "full-screen");

        for (Map.Entry<CaptureProcessor, Boolean> entry : subscribers.entrySet()) {
            CaptureProcessor captureProcessor = entry.getKey();
            Boolean runOnce = entry.getValue();
            RectangleArea captureArea = captureProcessor.captureArea();
            BufferedImage inputImage = (captureArea == null)
                    ? fullScreenshot
                    : ScreenshotUtils.crop(captureArea, fullScreenshot, "processor-crop");

            Future<?> future = executor.submit(() -> {
                try {
                    captureProcessor.process(inputImage);
                    captureProcessor.updateLastRunTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futures.add(future);

            if (runOnce)
                unsubscribe(captureProcessor);
        }
        return futures;
    }

    /**
     * Convenience method that performs a {@link #dispatch()} and then blocks
     * until all submitted tasks have completed.
     * <p>
     * Use this when you need to ensure that all subscribers have finished
     * processing before continuing.
     */
    public void dispatchAndWait() {
        List<Future<?>> futures = dispatch();
        waitForAll(futures);
    }

    /**
     * Subscribes a processor for repeated execution on every dispatch cycle.
     *
     * @param captureProcessor the processor to subscribe
     * @return the current instance of {@code CaptureDispatcher} for chaining
     */
    public CaptureDispatcher subscribe(CaptureProcessor captureProcessor) {
        subscribers.put(captureProcessor, false);
        return this;
    }

    /**
     * Subscribes a processor for a single execution only.
     * The processor will be automatically unsubscribed after its first run.
     *
     * @param captureProcessor the processor to subscribe for a single run
     * @return the current instance of {@code CaptureDispatcher} for chaining
     */
    public CaptureDispatcher subscribeForSingleRun(CaptureProcessor captureProcessor) {
        subscribers.put(captureProcessor, true);
        return this;
    }

    /**
     * Unsubscribes a previously registered processor.
     *
     * @param captureProcessor the processor to unsubscribe
     */
    public void unsubscribe(CaptureProcessor captureProcessor) {
        subscribers.remove(captureProcessor);
    }

    /**
     * Waits for all provided {@link Future} tasks to complete.
     * Any exceptions thrown by the tasks are caught and printed to {@code stderr}.
     *
     * @param futures the list of futures to wait for
     */
    private void waitForAll(List<Future<?>> futures) {
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
