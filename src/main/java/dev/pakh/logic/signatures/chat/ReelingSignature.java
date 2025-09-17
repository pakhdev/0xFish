package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class ReelingSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "Reeling",
                ChatColors.BEIGE,
                "000010000000000000001000000000010000001000000000000100000000000000000000100100100010000001001000000000000000000000000000000000000000000000"
        );
    }
}
