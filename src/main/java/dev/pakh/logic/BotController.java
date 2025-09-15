package dev.pakh.logic;

import dev.pakh.logic.locators.CharacterInfoBoxLocator;
import dev.pakh.logic.locators.ChatBoxLocator;
import dev.pakh.logic.locators.SkillsAndFishingShotsLocator;
import dev.pakh.logic.ports.BotStateListener;
import dev.pakh.logic.ports.FishingStateListener;
import dev.pakh.logic.ports.FishingStatsListener;
import dev.pakh.models.game.GameLayout;

public class BotController {
    private final GameLayout gameLayout;
    private final CaptureDispatcher captureDispatcher;
    private final BotStateListener botStateListener;
    private final FishingStatsListener fishingStatsListener;
    private final FishingStateListener fishingStateListener;
    private FishingWorkflow fishingWorkflow = null;

    public BotController(
            CaptureDispatcher captureDispatcher,
            BotStateListener botStateListener,
            FishingStatsListener fishingStatsListener,
            FishingStateListener fishingStateListener
    ) {
        this.gameLayout = new GameLayout(fishingStateListener);
        this.captureDispatcher = captureDispatcher;
        this.botStateListener = botStateListener;
        this.fishingStatsListener = fishingStatsListener;
        this.fishingStateListener = fishingStateListener;
    }

    public boolean detectElements() {
        fishingStateListener.onDetectUI();
        captureDispatcher
                .init()
                .subscribeForSingleRun(new CharacterInfoBoxLocator(gameLayout))
                .subscribeForSingleRun(new ChatBoxLocator(gameLayout, fishingStateListener))
                .subscribeForSingleRun(new SkillsAndFishingShotsLocator(gameLayout))
                .dispatchAndWait();
        boolean ready = gameLayout.areStartupAreasReady();
        if (ready) fishingStateListener.onReady();
        return ready;
    }

    public boolean startBot() {
        fishingWorkflow = new FishingWorkflow(captureDispatcher, captureDispatcher.gameWindow, gameLayout);
        fishingWorkflow.setListeners(botStateListener, fishingStatsListener, fishingStateListener);
        return fishingWorkflow.start();
    }

    public void stopBot() {
        if (fishingWorkflow != null) fishingWorkflow.stop();
    }

}
