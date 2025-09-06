package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

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
