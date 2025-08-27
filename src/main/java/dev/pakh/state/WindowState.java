package dev.pakh.state;

import com.sun.jna.platform.win32.WinDef.HWND;

public class WindowState {
    private HWND windowHWND;
    private int xStart;
    private int xEnd;
    private int yStart;
    private int yEnd;

    public WindowState(
            HWND windowHWND,
            int xStart,
            int xEnd,
            int yStart,
            int yEnd
    ) {
        this.windowHWND = windowHWND;
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.yStart = yStart;
        this.yEnd = yEnd;
    }


    public int getXStart() {
        return xStart;
    }

    public void setXStart(int xStart) {
        this.xStart = xStart;
    }

    public int getXEnd() {
        return xEnd;
    }

    public void setXEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getYStart() {
        return yStart;
    }

    public void setYStart(int yStart) {
        this.yStart = yStart;
    }

    public int getYEnd() {
        return yEnd;
    }

    public void setYEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public HWND getWindowHWND() {
        return windowHWND;
    }

    public void setWindowHWND(HWND windowHWND) {
        this.windowHWND = windowHWND;
    }
}
