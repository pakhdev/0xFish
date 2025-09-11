package dev.pakh.models.chat;

import dev.pakh.models.signatures.ChatColor;

public record ChatMessageLine(ChatColor color, String encoding) {
    public static int TOLERANCE = 3;

    public boolean isEqual(ChatMessageLine messageLine) {
        boolean colorMatch = (this.color == null && messageLine.color() == null) ||
                (this.color != null && this.color.equals(messageLine.color()));
        boolean encodingMatch = equalsWithTolerance(messageLine.encoding(), encoding);
        return colorMatch && encodingMatch;
    }

    private boolean equalsWithTolerance(String a, String b) {
        if (a.length() != b.length())
            return false;

        int errors = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                errors++;
                if (errors > TOLERANCE) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return color + ": " + encoding;
    }
}
