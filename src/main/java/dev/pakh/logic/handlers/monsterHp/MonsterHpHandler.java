package dev.pakh.logic.handlers.monsterHp;

import dev.pakh.logic.CaptureDispatcher;
import dev.pakh.logic.FishingWorkflow;
import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.utils.PixelValidationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MonsterHpHandler extends CaptureProcessor {
    private final GameLayout gameLayout;
    private final CaptureDispatcher captureDispatcher;
    private final FishingWorkflow fishingWorkflow;

    public MonsterHpHandler(GameLayout gameLayout, CaptureDispatcher captureDispatcher, FishingWorkflow fishingWorkflow) {
        this.gameLayout = gameLayout;
        this.captureDispatcher = captureDispatcher;
        this.fishingWorkflow = fishingWorkflow;
    }

    @Override
    public void process(BufferedImage image) throws Exception {
        if (!isMonsterAlive(image)) {
            captureDispatcher.unsubscribeByClass(MonsterHpHandler.class);
            this.fishingWorkflow.pickupMonsterDrop();
        }

    }

    private boolean isMonsterAlive(BufferedImage image) {
        Point monsterHpPoint = gameLayout.getMonsterHpBar();
        return PixelValidationUtils.hasValidSignature(image, monsterHpPoint, ElementSignaturesManager.find(
                "MonsterFirstHpPixel"));
    }

    @Override
    protected int getTimeoutTicks() {
        return 3;
    }
}
