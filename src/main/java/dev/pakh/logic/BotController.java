package dev.pakh.logic;

import dev.pakh.state.WindowState;

public class BotController {
    private WindowState windowState = null;

    public void detectWindow() {
        WindowState foundWindow = WindowFinder.find();
        if (foundWindow != null)
            windowState = foundWindow;

        ScreenGrabber.captureScreen(
                windowState.getXStart(), windowState.getYStart(), windowState.getXEnd(), windowState.getYEnd()
        );
    }

    public void startFishing() {
        System.out.println("Start fishing");
    }

    public void stopFishing() {
        System.out.println("Stop fishing");
    }
}
