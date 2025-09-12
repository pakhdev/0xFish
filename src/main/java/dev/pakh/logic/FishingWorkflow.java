package dev.pakh.logic;

import dev.pakh.logic.handlers.chat.ChatHandler;
import dev.pakh.logic.handlers.countdownDetection.CountdownDetectionHandler;
import dev.pakh.logic.handlers.fishHp.FishHpHandler;
import dev.pakh.logic.locators.FishingBoxLocator;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.game.GameWindow;

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

    public boolean start() {
        if (!gameLayout.areStartupAreasReady()) return false;

        gameWindow.activateWindow();
        // Check fishing shots

        //    CharacterHpHandler characterHpHandler = new CharacterHpHandler(gameLayout);
        //    subscribe(characterHpHandler);
        //
        //    CharacterCpHandler characterCpHandler = new CharacterCpHandler(gameLayout);
        //    subscribe(characterCpHandler);

        ChatHandler chatHandler = new ChatHandler(gameLayout, this::restart);
        subscribe(chatHandler);

        startCaptureLoop();
        // gameLayout.getFishingSkill().activate(null);
        return true;
    }

    public void restart() {
        captureDispatcher.unsubscribeByClass(FishHpHandler.class);
        // Check fishing shots - else use stop()
        // gameLayout.getFishingSkill().activate(null);
        startFishing();
        System.out.println("Restart!");
    }

    public void stop() {
        if (captureTask != null) {
            captureTask.cancel(true);
        }
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
        unsubscribeAll();
    }

    public void startFishing() {
        if (!gameLayout.isFishingBoxDetected()) {
            FishingBoxLocator fishingBoxLocator = new FishingBoxLocator(gameLayout, captureDispatcher);
            captureDispatcher.subscribeForSingleRun(fishingBoxLocator);
            return;
        }
        captureDispatcher.subscribe(new CountdownDetectionHandler(gameLayout, captureDispatcher));
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
}
