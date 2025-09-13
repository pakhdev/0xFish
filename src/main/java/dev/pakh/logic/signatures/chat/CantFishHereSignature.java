package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class CantFishHereSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "CantFishHere",
                ChatColors.BEIGE,
                "000010000000000000001000000000000000010010001000000000000010001000000001000100000010001000000000100000000001000000000000000000000000000000"
        );
    }
}
