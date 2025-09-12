package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class FishingBoxBottomBorderSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("FishingBoxBottomBorder", List.of(
                ColorPoint.of(-1, 0, "A8937A"),
                ColorPoint.of(258, 0, "88765D")
        ));
    }
}
