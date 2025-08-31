package dev.pakh;

import dev.pakh.logic.BotController;
import dev.pakh.ui.UI;
import dev.pakh.utils.SoundUtils;


public class Main {
    public static void main(String[] args) {
        BotController botController = new BotController();
        UI ui = new UI(botController);
    }
}
