package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class SkillsPanelLeftBorderBottomSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("SkillsPanelLeftBorderBottom", List.of(
                ColorPoint.of(4, 8, "E4D2B1"),
                ColorPoint.of(4, 11, "E4D2B1")
        ));
    }
}
