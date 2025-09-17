package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class NoRodSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "NoRod",
                ChatColors.BEIGE,
                "001000001000000001000100100100010000001000000000000000010010001000000000000010001001000010000000000000000000000000000100000010000000000001"
        );
    }
}
