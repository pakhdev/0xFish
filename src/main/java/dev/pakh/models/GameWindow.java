package dev.pakh.models;

import com.sun.jna.platform.win32.WinDef.HWND;

public record GameWindow(HWND windowHWND, RectangleArea area) {
}
