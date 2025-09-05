package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

import java.util.List;

public class CharacterInfoBoxRightBorderSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("CharacterInfoBoxRightBorder", List.of(
                ColorPoint.of(0, -11, "B7B5A6"),
                ColorPoint.of(0, -10, "938973")
        ));
    }
}
