package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class ChatArrowUpBoxSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("ChatArrowUpBox", List.of(
                ColorPoint.of(1, -8, "B8B5A3"),
                ColorPoint.of(2, -9, "BAB8A8")
        ));
    }
}
