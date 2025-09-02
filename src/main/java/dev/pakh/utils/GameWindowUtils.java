package dev.pakh.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.POINT;
import com.sun.jna.win32.StdCallLibrary;
import dev.pakh.models.RectangleArea;
import dev.pakh.models.GameWindow;
import dev.pakh.ui.MessageBox;

import java.util.ArrayList;
import java.util.List;


public class GameWindowUtils {

    private static final int SW_RESTORE = 9;

    public static GameWindow find() {
        List<HWND> windows = findLineageWindows();
        validateSingleWindow(windows);

        HWND foundHWND = windows.getFirst();
        RectangleArea area = getWindowArea(foundHWND);

        if (area == null) {
            MessageBox.error("Can not get window position");
            return null;
        }

        return new GameWindow(foundHWND, area);
    }

    public static boolean activateWindow(HWND hwnd) {
        if (hwnd == null || isWindowActive(hwnd))
            return false;
        
        User32Helper.INSTANCE.ShowWindow(hwnd, SW_RESTORE);
        return User32Helper.INSTANCE.SetForegroundWindow(hwnd);
    }


    public static boolean isWindowActive(HWND hwnd) {
        HWND foreground = User32Helper.INSTANCE.GetForegroundWindow();
        return hwnd.equals(foreground);
    }

    private static RectangleArea getWindowArea(HWND hwnd) {
        RECT clientRect = getClientRect(hwnd);
        if (clientRect == null) return null;

        POINT topLeft = new POINT(clientRect.left, clientRect.top);
        boolean success = User32Helper.INSTANCE.ClientToScreen(hwnd, topLeft);
        if (!success) return null;

        int width = clientRect.right;
        int height = clientRect.bottom;
        return new RectangleArea(topLeft.x, topLeft.x + width, topLeft.y, topLeft.y + height);
    }

    private static RECT getClientRect(HWND hwnd) {
        RECT rect = new RECT();
        boolean rectFound = User32.INSTANCE.GetClientRect(hwnd, rect);

        if (!rectFound)
            return null;

        return rect;
    }

    private static List<HWND> findLineageWindows() {
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

    private static void validateSingleWindow(List<HWND> windows) {
        if (windows.isEmpty()) {
            MessageBox.error("Window not found");
            throw new RuntimeException("Window not found");
        }
        if (windows.size() > 1) {
            MessageBox.error("More than one window found");
            throw new RuntimeException("More than one window found");
        }
    }

    private interface User32Helper extends StdCallLibrary {
        User32Helper INSTANCE = Native.load("user32", User32Helper.class);

        boolean ClientToScreen(HWND hWnd, POINT point);

        HWND GetForegroundWindow();

        boolean SetForegroundWindow(HWND hWnd);

        boolean ShowWindow(HWND hWnd, int nCmdShow);
    }

}
