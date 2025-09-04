package dev.pakh.models;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import dev.pakh.ui.MessageBox;

import java.util.ArrayList;
import java.util.List;

public class GameWindow {

    private final int SW_RESTORE = 9;
    private final int WINDOW_ACTIVATION_TIMEOUT_MS = 10000;
    private final int WINDOW_ACTIVATION_CHECK_INTERVAL_MS = 100;

    public HWND hwnd = null;
    public RectangleArea area = null;

    public GameWindow identify() {
        List<HWND> windows = findLineageWindows();
        validateSingleWindow(windows);

        HWND foundHWND = windows.getFirst();
        RectangleArea area = getWindowArea(foundHWND);

        if (area == null)
            throw new RuntimeException("Can not get window position");

        this.hwnd = foundHWND;
        this.area = area;
        return this;
    }

    public boolean isIdentified() {
        return hwnd != null & area != null;
    }

    public boolean activateWindow() {
        if (hwnd == null || isWindowActive())
            return false;

        User32Helper.INSTANCE.ShowWindow(hwnd, SW_RESTORE);
        User32Helper.INSTANCE.SetForegroundWindow(hwnd);
        return waitForWindowActive();
    }

    public boolean isWindowActive() {
        HWND foreground = User32Helper.INSTANCE.GetForegroundWindow();
        return hwnd.equals(foreground);
    }

    public HWND getHwnd() {
        return hwnd;
    }

    public RectangleArea getArea() {
        return area;
    }

    private boolean waitForWindowActive() {
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < WINDOW_ACTIVATION_TIMEOUT_MS) {
            if (isWindowActive()) return true;

            try {
                Thread.sleep(WINDOW_ACTIVATION_CHECK_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        MessageBox.error("The game window could not be activated in time");
        return false;
    }

    private RectangleArea getWindowArea(HWND hwnd) {
        WinDef.RECT clientRect = getClientRect(hwnd);
        if (clientRect == null) return null;

        WinDef.POINT topLeft = new WinDef.POINT(clientRect.left, clientRect.top);
        boolean success = User32Helper.INSTANCE.ClientToScreen(hwnd, topLeft);
        if (!success) return null;

        int width = clientRect.right;
        int height = clientRect.bottom;
        return new RectangleArea(topLeft.x, topLeft.x + width, topLeft.y, topLeft.y + height);
    }

    private WinDef.RECT getClientRect(HWND hwnd) {
        WinDef.RECT rect = new WinDef.RECT();
        boolean rectFound = User32.INSTANCE.GetClientRect(hwnd, rect);

        if (!rectFound)
            return null;

        return rect;
    }

    private List<HWND> findLineageWindows() {
        List<HWND> lineageWindows = new ArrayList<>();

        User32.INSTANCE.EnumWindows((hWnd, data) -> {
            char[] windowText = new char[512];
            User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
            String wText = Native.toString(windowText);

            if (!wText.isEmpty() && wText.toLowerCase().contains("asterios"))
                lineageWindows.add(hWnd);

            return true;
        }, null);

        return lineageWindows;
    }

    private void validateSingleWindow(List<HWND> windows) {
        if (windows.isEmpty())
            throw new RuntimeException("Window not found");

        if (windows.size() > 1)
            throw new RuntimeException("More than one window found");
    }

    private interface User32Helper extends StdCallLibrary {
        User32Helper INSTANCE = Native.load("user32", User32Helper.class);

        boolean ClientToScreen(HWND hWnd, WinDef.POINT point);

        HWND GetForegroundWindow();

        boolean SetForegroundWindow(HWND hWnd);

        boolean ShowWindow(HWND hWnd, int nCmdShow);
    }

}
