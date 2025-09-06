package dev.pakh.logic;

import dev.pakh.logic.locators.CharacterInfoBoxLocator;
import dev.pakh.logic.locators.ChatBoxLocator;
import dev.pakh.logic.locators.SkillsAndFishingShotsLocator;
import dev.pakh.models.GameLayout;
import dev.pakh.models.GameWindow;

public class BotController {
    private final CaptureDispatcher captureDispatcher;
    private final GameLayout gameLayout;

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
        System.out.println("Result " + gameLayout.getChatArea());
        return gameLayout.areStartupAreasReady();
    }

    public void startFishing() {
        System.out.println("Start fishing");
//        if (gameLayout.areStartupAreasReady()) {
//            captureDispatcher
//                    .subscribe(new CharacterHpObserver())
//                    .subscribe(new CharacterCpObserver())
//                    .subscribe(new ChatObserver());
//        } else return false;
//        captureDispatcher
//                .subscribe(new SkillsCooldownObserver())
//                .subscribe(new FishingProcessObserver())
//                .subscribe(new FishingShotsObserver());

    }

    public void stopFishing() {
        System.out.println("Stop fishing");
    }

    public GameWindow devWindow() {
        GameWindow gameWindow = new GameWindow();
        if (gameWindow.identify() != null) {
            gameWindow.activateWindow();
            return gameWindow;
        }
        return null;
    }
}
