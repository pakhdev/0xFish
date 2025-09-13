package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class MonsterBoxLeftBorderSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("MonsterBoxLeftBorder", List.of(
                ColorPoint.of(4, 4, "E4D2B1"),
                ColorPoint.of(5, 5, "E4D2B1")
        ));
    }
}
