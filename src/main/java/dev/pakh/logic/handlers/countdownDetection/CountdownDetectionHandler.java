package dev.pakh.logic.handlers.countdownDetection;

import dev.pakh.logic.CaptureDispatcher;
import dev.pakh.logic.handlers.fishHp.FishHpHandler;
import dev.pakh.logic.ports.FishingStateListener;
import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.utils.PixelValidationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CountdownDetectionHandler extends CaptureProcessor {
    private final GameLayout gameLayout;
    private final CaptureDispatcher captureDispatcher;
    private final FishingStateListener fishingStateListener;

    public CountdownDetectionHandler(GameLayout gameLayout, CaptureDispatcher captureDispatcher,
                                     FishingStateListener fishingStateListener) {
        this.gameLayout = gameLayout;
        this.captureDispatcher = captureDispatcher;
        this.fishingStateListener = fishingStateListener;
    }

    @Override
    public void process(BufferedImage image) throws Exception {
        if (!gameLayout.isFishingBoxDetected()) return;

        ElementVisualSignature countdownWatchSignature = ElementSignaturesManager.find("CountdownWatch");
        Point point = new Point(gameLayout.getFishingBoxArea().startX(), gameLayout.getFishingBoxArea().startY());
        if (PixelValidationUtils.hasValidSignature(image, point, countdownWatchSignature)) {
            captureDispatcher.unsubscribeByClass(CountdownDetectionHandler.class);
            captureDispatcher.subscribe(new FishHpHandler(gameLayout, fishingStateListener));
        }
    }
}
