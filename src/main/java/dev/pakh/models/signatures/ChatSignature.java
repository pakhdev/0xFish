package dev.pakh.models.signatures;

import dev.pakh.models.chat.ChatMessageLine;

public record ChatSignature(String name, ChatColor color, String encoding) {
    public boolean isEqual(ChatMessageLine messageLine) {
        return messageLine.color() != null && messageLine.color().equals(color) && messageLine.encoding().equals(encoding);
    }

    public boolean startsWith(ChatMessageLine messageLine) {
        return messageLine.color() != null && messageLine.color().equals(color) && messageLine.encoding().startsWith(encoding);
    }
}
