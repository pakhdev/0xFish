package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

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
