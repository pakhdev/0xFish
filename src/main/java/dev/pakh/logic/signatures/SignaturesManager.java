package dev.pakh.logic.signatures;

import dev.pakh.logic.signatures.entries.CharacterInfoBoxLeftBorderSignature;
import dev.pakh.logic.signatures.entries.CharacterInfoBoxRightBorderSignature;
import dev.pakh.logic.signatures.entries.FishingSignature;
import dev.pakh.logic.signatures.models.ElementVisualSignature;

import java.util.List;

public class SignaturesManager {
    private static final List<ElementVisualSignature> SIGNATURES = List.of(
            new CharacterInfoBoxLeftBorderSignature().get(),
            new CharacterInfoBoxRightBorderSignature().get(),
            new FishingSignature().get()
    );

    public static ElementVisualSignature find(String name) {
        return SIGNATURES
                .stream()
                .filter(signature -> signature.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
