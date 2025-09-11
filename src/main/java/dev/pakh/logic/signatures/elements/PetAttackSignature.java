package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class PetAttackSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("PetAttack", List.of(
                ColorPoint.of(12, 10, "E7F7A5"),
                ColorPoint.of(12, 16, "86A063"),
                ColorPoint.of(12, 22, "181C10"),
                ColorPoint.of(19, 10, "101008"),
                ColorPoint.of(19, 16, "D6F394"),
                ColorPoint.of(19, 22, "C6E784")
        ));
    }
}
