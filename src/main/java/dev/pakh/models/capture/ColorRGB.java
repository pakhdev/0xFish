package dev.pakh.models.capture;

public class ColorRGB {
    public int r;
    public int g;
    public int b;

    public ColorRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String toString() {
        return "R=" + r + " G=" + g + " B=" + b;
    }
}
