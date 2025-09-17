package dev.pakh.logic.signatures.chat;

import dev.pakh.models.signatures.ChatColors;
import dev.pakh.models.signatures.ChatSignature;
import dev.pakh.models.signatures.ChatSignatureEntry;

public class SkillFailedSignature extends ChatSignatureEntry {
    @Override
    public ChatSignature get() {
        return new ChatSignature(
                "SkillFailed",
                ChatColors.BEIGE,
                "000010000100010000000000000100010000000010001000000100010000001000000100000100000000000000001000000100000000000000001000000001000000000000"
        );
    }
}
