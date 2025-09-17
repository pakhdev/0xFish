package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class PumpingSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "Pumping",
                ChatColors.BEIGE,
                "000010000000000000001000000000010000001000000000000100000000010010001000100000000100100010000001001000000000000000000000000000000000000000"
        );
    }
}
