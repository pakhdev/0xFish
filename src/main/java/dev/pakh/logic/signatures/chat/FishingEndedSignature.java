package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class FishingEndedSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "FishingEnded",
                ChatColors.BEIGE,
                "000010000000000000001000000100000000000000000100000000100000000000000100100000001001001000100000000000001001000100000000001001000100000010"
        );
    }
}
