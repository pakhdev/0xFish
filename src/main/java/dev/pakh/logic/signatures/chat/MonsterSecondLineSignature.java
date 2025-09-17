package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class MonsterSecondLineSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "MonsterSecondLine",
                ChatColors.BEIGE,
                "000000000000100010000000000000100100000010000000000100010010000000000000000000000010000000000000000000010000000010000000000000000000000000"
        );
    }
}
