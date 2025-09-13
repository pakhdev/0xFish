package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class MonsterFirstHpPixelSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("MonsterFirstHpPixel", List.of(
                ColorPoint.of(0, 0, "6F1713")
        ));
    }
}
