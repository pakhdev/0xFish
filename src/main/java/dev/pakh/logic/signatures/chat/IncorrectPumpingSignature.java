package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class IncorrectPumpingSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "IncorrectPumping",
                ChatColors.OLIVE,
                "000010000000000000001000000100000001001001000000000000010000000000000000000000000100000000000000000100100010000100000001000100100100010000"
        );
    }
}
