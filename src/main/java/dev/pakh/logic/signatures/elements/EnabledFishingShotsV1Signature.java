package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class EnabledFishingShotsV1Signature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("EnabledFishingShotsV1", List.of(
                ColorPoint.of(3, 27, "B08753"),
                ColorPoint.of(3, 28, "E1D7A1")
        ));
    }
}
