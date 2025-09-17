package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class CaughtSomethingSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "CaughtSomething",
                ChatColors.ORANGE,
                "000010000000000000001000000000000000010000001000000100100010000000000000010000000010001000100000000000001000100100100010000001000000000000"
        );
    }
}
