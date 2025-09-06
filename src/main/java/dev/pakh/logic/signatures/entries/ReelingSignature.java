package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

import java.util.List;

public class ReelingSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("Reeling", List.of(
                ColorPoint.of(12, 10, "310C42"),
                ColorPoint.of(12, 16, "4F3970"),
                ColorPoint.of(12, 22, "42386B"),
                ColorPoint.of(19, 10, "6BACD3"),
                ColorPoint.of(19, 16, "9195B0"),
                ColorPoint.of(19, 22, "185994")
        ));
    }
}
