package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class EnabledFishingShotsV3Signature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("EnabledFishingShotsV3", List.of(
                ColorPoint.of(16, 15, "D1CE8A"),
                ColorPoint.of(16, 16, "452F2F"),
                ColorPoint.of(16, 17, "876B13")
        ));
    }
}
