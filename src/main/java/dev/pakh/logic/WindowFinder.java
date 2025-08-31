package dev.pakh.logic;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.POINT;
import com.sun.jna.win32.StdCallLibrary;
import dev.pakh.models.RectangleArea;
import dev.pakh.models.WindowState;
import dev.pakh.ui.MessageBox;

import java.util.ArrayList;
import java.util.List;


public class WindowFinder {

    public static WindowState find() {
        List<HWND> windows = findLineageWindows();
        validateSingleWindow(windows);

        HWND foundHWND = windows.getFirst();
        RECT rect = getWindowPosition(foundHWND);

        if (rect == null) {
            MessageBox.error("Error", "Can not get window position");
            return null;
        }

        return new WindowState(foundHWND, new RectangleArea(rect.left, rect.right, rect.top, rect.bottom));
    }

    public static RECT getWindowPosition(HWND hwnd) {
        RECT clientRect = getClientRect(hwnd);
        if (clientRect == null) return null;

        POINT topLeft = new POINT(clientRect.left, clientRect.top);
        boolean success = MyUser32.INSTANCE.ClientToScreen(hwnd, topLeft);
        if (!success) return null;

        clientRect.left = topLeft.x;
        clientRect.right = clientRect.right + clientRect.left;
        clientRect.top = topLeft.y;
        clientRect.bottom = clientRect.top + clientRect.bottom;
        return clientRect;
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
            MessageBox.error("Error", "Window not found");
            throw new RuntimeException("Window not found");
        }
        if (windows.size() > 1) {
            MessageBox.error("Error", "More than one window found");
            throw new RuntimeException("More than one window found");
        }
    }

    private interface MyUser32 extends StdCallLibrary {
        MyUser32 INSTANCE = Native.load("user32", MyUser32.class);

        boolean ClientToScreen(HWND hWnd, POINT point);
    }

}
