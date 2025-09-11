package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

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
