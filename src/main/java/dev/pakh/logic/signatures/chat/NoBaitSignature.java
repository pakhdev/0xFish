package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class NoBaitSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "NoBait",
                ChatColors.BEIGE,
                "000010000000000000001000000100010001000000100000010000000000000000000100000000000000000000100100000000000000000100010000000010000000000000"
        );
    }
}
