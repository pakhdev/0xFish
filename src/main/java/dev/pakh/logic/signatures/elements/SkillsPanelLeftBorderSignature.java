package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class SkillsPanelLeftBorderSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("SkillsPanelLeftBorder", List.of(
                ColorPoint.of(1, 0, "C2B8A8"),
                ColorPoint.of(1, -1, "C7BFB0")
        ));
    }
}
