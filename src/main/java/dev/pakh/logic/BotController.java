package dev.pakh.logic;

import dev.pakh.logic.locators.CharacterCpLocator;
import dev.pakh.logic.locators.CharacterHpLocator;
import dev.pakh.logic.locators.CharacterInfoBoxLocator;
import dev.pakh.models.ProgressBar;
import dev.pakh.models.RectangleArea;
import dev.pakh.models.WindowState;
import dev.pakh.utils.PixelInspectionUtils;
import dev.pakh.utils.ScreenshotUtils;

import java.awt.image.BufferedImage;

public class BotController {
    private WindowState windowState = null;

    public Boolean detectWindow() {
        WindowState foundWindow = WindowFinder.find();
        if (foundWindow == null) return false;
        windowState = foundWindow;

        BufferedImage image = ScreenshotUtils.capture(windowState.area(), "full-window");

//        PixelInspectionUtils.printHexVerticalColors(image, 177, 0, 80);

        RectangleArea characterInfoBoxArea = CharacterInfoBoxLocator.locate(image);
        if (characterInfoBoxArea == null) return false;

        System.out.println("Result " + characterInfoBoxArea);

//        ProgressBar characterHp = CharacterHpLocator.locate(characterInfoBoxArea);
//        if (characterHp == null) return false;
//
//        ProgressBar characterCp = CharacterCpLocator.locate(characterInfoBoxArea);
//        if (characterCp == null) return false;

        // Detect chat area
        // Detect skills position: Fishing, Pumping, Reeling
        // Detect fishing shots position

        return true;
    }

    public void startFishing() {
        System.out.println("Start fishing");
    }

    public void stopFishing() {
        System.out.println("Stop fishing");
    }
}
