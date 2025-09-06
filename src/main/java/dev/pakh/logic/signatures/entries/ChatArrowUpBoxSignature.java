package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

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
