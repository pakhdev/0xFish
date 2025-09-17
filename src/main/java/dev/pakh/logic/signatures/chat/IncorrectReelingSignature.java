package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class IncorrectReelingSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "IncorrectReeling",
                ChatColors.OLIVE,
                "000010000000000000001000000100000001001001000000000000010000000000000000000001000000000000000001000000000010001000000100000000001000100000"
        );
    }
}
