package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class CountdownWatchSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("CountdownWatch", List.of(
                ColorPoint.of(112, 194, "EFDF84"),
                ColorPoint.of(113, 194, "EFDF84")
        ));
    }
}
