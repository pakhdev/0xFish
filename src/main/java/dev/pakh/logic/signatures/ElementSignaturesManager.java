package dev.pakh.logic.signatures;

import dev.pakh.logic.signatures.elements.*;
import dev.pakh.models.signatures.ElementVisualSignature;

import java.util.List;

public class ElementSignaturesManager {
    private static final List<ElementVisualSignature> SIGNATURES = List.of(
            new CharacterInfoBoxLeftBorderSignature().get(),
            new CharacterInfoBoxRightBorderSignature().get(),
            new ChatArrowUpBoxSignature().get(),
            new ChatBottomSignature().get(),
            new CountdownWatchSignature().get(),
            new DisabledFishingShotsSignature().get(),
            new EnabledFishingShotsV1Signature().get(),
            new EnabledFishingShotsV2Signature().get(),
            new EnabledFishingShotsV3Signature().get(),
            new FishingBoxBottomBorderSignature().get(),
            new FishingSignature().get(),
            new MonsterBoxLeftBorderSignature().get(),
            new MonsterFirstHpPixelSignature().get(),
            new NextTargetSignature().get(),
            new PetAttackSignature().get(),
            new PetPickupSignature().get(),
            new PumpingSignature().get(),
            new ReelingSignature().get(),
            new SkillsPanelLeftBorderBottomSignature().get(),
            new SkillsPanelLeftBorderSignature().get()
    );

    public static ElementVisualSignature find(String name) {
        return SIGNATURES
                .stream()
                .filter(signature -> signature.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
