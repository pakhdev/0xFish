package dev.pakh.models.capture;

public class ColorHSB {
    public int h; // Hue: 0–360 (degrees)
    public int s; // Saturation: 0–100 (%)
    public int b; // Brightness: 0–100 (%)

    public ColorHSB(int h, int s, int b) {
        this.h = h;
        this.s = s;
        this.b = b;
    }

    @Override
    public String toString() {
        return "H=" + h + "° S=" + s + "% B=" + b + "%";
    }
}

