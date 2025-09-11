package dev.pakh.logic.signatures.elements;

import dev.pakh.models.signatures.ColorPoint;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.signatures.SignatureEntry;

import java.util.List;

public class ChatBottomSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("ChatBottom", List.of(
                ColorPoint.of(6, 1, "958F7E"),
                ColorPoint.of(6, 20, "958F7E"),
                ColorPoint.of(6, 44, "958F7E"),
                ColorPoint.of(6, 63, "958F7E")
        ));
    }
}
