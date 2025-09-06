package dev.pakh.logic.signatures;

import dev.pakh.logic.signatures.entries.*;
import dev.pakh.logic.signatures.models.ElementVisualSignature;

import java.util.List;

public class SignaturesManager {
    private static final List<ElementVisualSignature> SIGNATURES = List.of(
            new CharacterInfoBoxLeftBorderSignature().get(),
            new CharacterInfoBoxRightBorderSignature().get(),
            new SkillsPanelLeftBorderBottomSignature().get(),
            new SkillsPanelLeftBorderSignature().get(),
            new FishingSignature().get(),
            new PumpingSignature().get(),
            new ReelingSignature().get(),
            new NextTargetSignature().get(),
            new PetAttackSignature().get(),
            new PetPickupSignature().get(),
            new DisabledFishingShotsSignature().get(),
            new EnabledFishingShotsV1Signature().get(),
            new EnabledFishingShotsV2Signature().get(),
            new EnabledFishingShotsV3Signature().get()
    );

    public static ElementVisualSignature find(String name) {
        return SIGNATURES
                .stream()
                .filter(signature -> signature.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
