package dev.pakh.logic;

import dev.pakh.logic.handlers.CharacterCpHandler;
import dev.pakh.logic.locators.CharacterInfoBoxLocator;
import dev.pakh.logic.locators.ChatBoxLocator;
import dev.pakh.logic.locators.SkillsAndFishingShotsLocator;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.game.GameWindow;
import dev.pakh.utils.ScreenshotUtils;

import java.awt.image.BufferedImage;

public class BotController {
    private final CaptureDispatcher captureDispatcher;
    private final GameLayout gameLayout;
    private FishingWorkflow fishingWorkflow = null;

    public BotController(CaptureDispatcher captureDispatcher) {
        this.gameLayout = new GameLayout();
        this.captureDispatcher = captureDispatcher;
    }

    public boolean detectElements() {
        captureDispatcher
                .init()
                .subscribeForSingleRun(new CharacterInfoBoxLocator(gameLayout))
                .subscribeForSingleRun(new ChatBoxLocator(gameLayout))
                .subscribeForSingleRun(new SkillsAndFishingShotsLocator(gameLayout))
                .dispatchAndWait();
        return gameLayout.areStartupAreasReady();
    }

    public boolean startFishing() {
        System.out.println("Start fishing");
        fishingWorkflow = new FishingWorkflow(captureDispatcher, captureDispatcher.gameWindow, gameLayout);
        return fishingWorkflow.start();
    }

    public void stopFishing() {
        System.out.println("Stop fishing");
        fishingWorkflow.stop();
    }
}
