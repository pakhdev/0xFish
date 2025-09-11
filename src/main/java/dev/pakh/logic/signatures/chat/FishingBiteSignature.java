package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class FishingBiteSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "FishingBite",
                ChatColors.BLUE,
                "000010000000000000001000000010000000000000000001000000000000000000000100000000000010000000000000000000000000000000000000000000000000000000"
        );
    }
}
