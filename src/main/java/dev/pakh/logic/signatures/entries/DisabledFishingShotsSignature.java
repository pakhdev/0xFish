package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

import java.util.List;

public class DisabledFishingShotsSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("DisabledFishingShots", List.of(
                ColorPoint.of(3, 27, "C6965A"),
                ColorPoint.of(3, 28, "FFF3B5")
        ));
    }
}
