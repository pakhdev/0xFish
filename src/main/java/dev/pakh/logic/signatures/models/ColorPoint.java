package dev.pakh.logic.signatures.models;

import java.awt.*;

public record ColorPoint(Point offset, String hexColor) {
    public static ColorPoint of(int x, int y, String hexColor) {
        return new ColorPoint(new Point(x, y), hexColor);
    }
}
