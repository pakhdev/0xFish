package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class FishingSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("Fishing", List.of(
                ColorPoint.of(12, 10, "684737"),
                ColorPoint.of(12, 16, "165970"),
                ColorPoint.of(12, 22, "94B6BD"),
                ColorPoint.of(19, 10, "BB967E"),
                ColorPoint.of(19, 16, "845542"),
                ColorPoint.of(19, 22, "63BCD1")
        ));
    }
}
