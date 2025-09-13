package dev.pakh.logic.signatures;

import dev.pakh.logic.signatures.chat.*;
import dev.pakh.models.signatures.ChatSignature;

import java.util.List;

public class ChatSignaturesManager {
    private static final List<ChatSignature> SIGNATURES = List.of(
            new ObtainedOldBoxSignature().get(),
            new MonsterSignature().get(),
            new FishingEndedSignature().get(),
            new FishingBiteSignature().get(),
            new NoBaitSignature().get(),
            new CantFishHereSignature().get(),
            new ObtainedSomethingSignature().get()
    );

    public static ChatSignature find(String name) {
        return SIGNATURES.stream()
                .filter(pattern -> pattern.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
