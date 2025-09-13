package dev.pakh.logic;

import dev.pakh.logic.handlers.chat.ChatHandler;
import dev.pakh.logic.handlers.countdownDetection.CountdownDetectionHandler;
import dev.pakh.logic.handlers.fishHp.FishHpHandler;
import dev.pakh.logic.handlers.monsterHp.MonsterHpHandler;
import dev.pakh.logic.locators.FishingBoxLocator;
import dev.pakh.logic.locators.MonsterBoxLocator;
import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.ui.FishingWorkflowListener;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.game.GameWindow;
import dev.pakh.utils.PixelValidationUtils;
import dev.pakh.utils.ScreenshotUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FishingWorkflow {
    private final CaptureDispatcher captureDispatcher;
    private final GameWindow gameWindow;
    private final GameLayout gameLayout;
    private final int DISPATCH_INTERVAL_MS = 200;
    private FishingWorkflowListener listener;

    private final List<CaptureProcessor> subscribedProcessors = new ArrayList<>();
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> captureTask;

    public FishingWorkflow(
            CaptureDispatcher captureDispatcher,
            GameWindow gameWindow,
            GameLayout gameLayout
    ) {
        this.captureDispatcher = captureDispatcher;
        this.gameWindow = gameWindow;
        this.gameLayout = gameLayout;
    }

    public void setListener(FishingWorkflowListener listener) {
        this.listener = listener;
    }

    public boolean start() {
        if (!gameLayout.areStartupAreasReady()) return false;

        gameWindow.activateWindow();
        if (areFishingShotsDisabled()) return false;

        //    CharacterHpHandler characterHpHandler = new CharacterHpHandler(gameLayout);
        //    subscribe(characterHpHandler);
        //
        //    CharacterCpHandler characterCpHandler = new CharacterCpHandler(gameLayout);
        //    subscribe(characterCpHandler);

        ChatHandler chatHandler = new ChatHandler(gameLayout, this);
        subscribe(chatHandler);

        startCaptureLoop();
        gameLayout.getFishingSkill().activate(null);

        if (listener != null)
            SwingUtilities.invokeLater(() -> listener.onFishingStarted());
        return true;
    }

    public void restart() {
        captureDispatcher.unsubscribeByClass(FishHpHandler.class);
        if (areFishingShotsDisabled()) {
            stop();
            return;
        }
        gameLayout.getFishingSkill().activate(null);
    }

    public void stop() {
        if (captureTask != null) {
            captureTask.cancel(true);
        }
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
        unsubscribeAll();

        if (listener != null)
            SwingUtilities.invokeLater(() -> listener.onFishingStopped());
    }

    public void waitFishingStart() {
        if (!gameLayout.isFishingBoxDetected()) {
            FishingBoxLocator fishingBoxLocator = new FishingBoxLocator(gameLayout, captureDispatcher);
            captureDispatcher.subscribeForSingleRun(fishingBoxLocator);
            return;
        }
        captureDispatcher.subscribe(new CountdownDetectionHandler(gameLayout, captureDispatcher));
    }

    public void selectMonster() throws InterruptedException {
        System.out.println("Select monster");

        gameLayout.getNextTargetSkill().activate(null);
        if (gameLayout.isMonsterHpBarDetected()) {
            Thread.sleep(30);
            killMonster();
        } else {
            MonsterBoxLocator monsterBoxLocator = new MonsterBoxLocator(
                    gameLayout,
                    captureDispatcher,
                    this
            );
            subscribe(monsterBoxLocator);
        }
    }

    public void killMonster() {
        System.out.println("Kill monster");
        gameLayout.getPetAttackSkill().activate(null);
        MonsterHpHandler monsterHpHandler = new MonsterHpHandler(gameLayout, captureDispatcher, this);
        subscribe(monsterHpHandler);
    }

    public void pickupMonsterDrop() throws InterruptedException {
        System.out.println("Pick up drop");

        gameLayout.getPetPickupSkill().activate(null);
        Thread.sleep(30);
        gameLayout.getPetPickupSkill().activate(null);
    }

    public void incrementFishCounter() {
        if (listener != null)
            SwingUtilities.invokeLater(() -> listener.onFishObtention());
    }

    public void incrementOldBoxCounter() {
        if (listener != null)
            SwingUtilities.invokeLater(() -> listener.onOldBoxObtention());
    }

    private void startCaptureLoop() {
        if (scheduler != null && !scheduler.isShutdown()) return;

        scheduler = Executors.newSingleThreadScheduledExecutor();
        captureTask = scheduler.scheduleAtFixedRate(
                captureDispatcher::dispatch,
                0,
                DISPATCH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    private void subscribe(CaptureProcessor processor) {
        captureDispatcher.subscribe(processor);
        subscribedProcessors.add(processor);
    }

    private void unsubscribeAll() {
        for (CaptureProcessor processor : subscribedProcessors) {
            captureDispatcher.unsubscribe(processor);
        }
        subscribedProcessors.clear();
    }

    private boolean areFishingShotsDisabled() {
        List<String> signatures = List.of(
                "EnabledFishingShotsV1",
                "EnabledFishingShotsV2",
                "EnabledFishingShotsV3"
        );

        BufferedImage image = ScreenshotUtils.capture(gameWindow.getArea(), "full-screen");
        RectangleArea fishingShotsArea = gameLayout.getFishingShotArea();
        Point fishingShotsPoint = new Point(fishingShotsArea.startX(), fishingShotsArea.startY());
        boolean enabled = false;

        for (String signatureName : signatures) {
            if (PixelValidationUtils.hasValidSignature(
                    image,
                    fishingShotsPoint,
                    ElementSignaturesManager.find(signatureName))) {
                enabled = true;
                break;
            }
        }

        if (!enabled) System.out.println("Enable fishing shots");
        return !enabled;
    }
}
