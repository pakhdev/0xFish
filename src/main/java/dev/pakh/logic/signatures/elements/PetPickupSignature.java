package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class PetPickupSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("PetPickup", List.of(
                ColorPoint.of(12, 10, "55603F"),
                ColorPoint.of(12, 16, "E7FB9C"),
                ColorPoint.of(12, 22, "081410"),
                ColorPoint.of(19, 10, "ADCB7B"),
                ColorPoint.of(19, 16, "080C08"),
                ColorPoint.of(19, 22, "000000")
        ));
    }
}
