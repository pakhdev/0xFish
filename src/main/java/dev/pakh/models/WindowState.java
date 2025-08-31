package dev.pakh.models;

import com.sun.jna.platform.win32.WinDef.HWND;

public record WindowState(HWND windowHWND, RectangleArea area) {
}
