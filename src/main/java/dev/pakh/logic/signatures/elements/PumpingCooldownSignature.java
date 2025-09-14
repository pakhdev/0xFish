package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class PumpingCooldownSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("PumpingCooldown", List.of(
                ColorPoint.of(14, 0, "0C1D20")
        ));
    }
}
