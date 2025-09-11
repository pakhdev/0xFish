package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class PumpingSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("Pumping", List.of(
                ColorPoint.of(12, 10, "8CE7EF"),
                ColorPoint.of(12, 16, "313842"),
                ColorPoint.of(12, 22, "9C5D4F"),
                ColorPoint.of(19, 10, "18496B"),
                ColorPoint.of(19, 16, "BDAAAD"),
                ColorPoint.of(19, 22, "DEC3BD")
        ));
    }
}
