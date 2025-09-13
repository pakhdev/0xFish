package dev.pakh.logic;

import dev.pakh.logic.locators.CharacterInfoBoxLocator;
import dev.pakh.logic.locators.ChatBoxLocator;
import dev.pakh.logic.locators.SkillsAndFishingShotsLocator;
import dev.pakh.models.ui.FishingWorkflowListener;
import dev.pakh.models.game.GameLayout;

public class BotController {
    private final CaptureDispatcher captureDispatcher;
    private final GameLayout gameLayout;
    private FishingWorkflow fishingWorkflow = null;
    private FishingWorkflowListener workflowListener;

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
        fishingWorkflow.setListener(workflowListener);
        return fishingWorkflow.start();
    }

    public void stopFishing() {
        System.out.println("Stop fishing");
        if (fishingWorkflow != null)
            fishingWorkflow.stop();
    }

    public void setWorkflowListener(FishingWorkflowListener listener) {
        this.workflowListener = listener;
    }
}
