package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class ObtainedSomethingSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "ObtainedSomething",
                ChatColors.YELLOW,
                "0000100000000000000010000001000100000010000100000000000000000000000000000000000100100100010000000000"
        );
    }
}
