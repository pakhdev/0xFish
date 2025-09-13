package dev.pakh;

import dev.pakh.logic.BotController;
import dev.pakh.logic.CaptureDispatcher;
import dev.pakh.ui.UI;
import dev.pakh.utils.SoundUtils;


public class Main {
    public static void main(String[] args) {
        CaptureDispatcher captureDispatcher = new CaptureDispatcher();
        BotController botController = new BotController(captureDispatcher);
        UI ui = new UI(botController);
        botController.setWorkflowListener(ui);
    }
}
