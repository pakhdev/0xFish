package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class ReelingCooldownSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("ReelingCooldown", List.of(
                ColorPoint.of(15, 13, "090E14")
        ));
    }
}
