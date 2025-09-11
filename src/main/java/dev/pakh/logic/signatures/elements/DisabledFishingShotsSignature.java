package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

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
