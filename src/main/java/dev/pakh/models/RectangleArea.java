package dev.pakh.models;

public record RectangleArea(int startX, int endX, int startY, int endY) {
    public int width() {
        return endX - startX;
    }

    public int height() {
        return endY - startY;
    }

    public Boolean isValid() {
        return width() >= 0 && height() >= 0;
    }
}
