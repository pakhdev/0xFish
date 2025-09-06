package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

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
