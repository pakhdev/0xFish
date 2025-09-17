package dev.pakh.logic.signatures;

import dev.pakh.logic.signatures.chat.*;
import dev.pakh.models.signatures.ChatSignature;

import java.util.List;

public class ChatSignaturesManager {
    private static final List<ChatSignature> SIGNATURES = List.of(
            new CantFishHereSignature().get(),
            new CaughtSomethingSignature().get(),
            new FishingBiteSignature().get(),
            new FishingEndedSignature().get(),
            new IncorrectPumpingSignature().get(),
            new IncorrectReelingSignature().get(),
            new MonsterFirstLineSignature().get(),
            new MonsterSecondLineSignature().get(),
            new NoBaitSignature().get(),
            new NoRodSignature().get(),
            new ObtainedOldBoxSignature().get(),
            new ObtainedSomethingSignature().get(),
            new PumpingSignature().get(),
            new ReelingSignature().get(),
            new SkillFailedSignature().get()
    );

    public static ChatSignature find(String name) {
        return SIGNATURES.stream()
                .filter(pattern -> pattern.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
