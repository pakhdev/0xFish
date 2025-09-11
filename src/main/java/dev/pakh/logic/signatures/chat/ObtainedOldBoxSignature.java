package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class ObtainedOldBoxSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "ObtainedOldBox",
                ChatColors.YELLOW,
                "000010000000000000001000000100010000001000010000000000000000000000000000000000010010010001000000000000010000000000001001000000100000000000"
        );
    }
}
