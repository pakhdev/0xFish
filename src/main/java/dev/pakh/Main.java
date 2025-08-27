package dev.pakh;

import dev.pakh.logic.BotController;
import dev.pakh.logic.WindowFinder;
import dev.pakh.state.WindowState;
import dev.pakh.ui.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        BotController botController = new BotController();
        UI ui = new UI(botController);
    }
}