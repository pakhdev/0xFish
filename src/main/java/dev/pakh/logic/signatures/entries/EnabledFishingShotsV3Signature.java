package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

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
