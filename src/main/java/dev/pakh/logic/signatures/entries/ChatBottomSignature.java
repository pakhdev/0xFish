package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

import java.util.List;

public class ChatBottomSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("ChatBottom", List.of(
                ColorPoint.of(6, 0, "958F7E"),
                ColorPoint.of(6, 19, "958F7E"),
                ColorPoint.of(6, 43, "958F7E"),
                ColorPoint.of(6, 62, "958F7E")
        ));
    }
}
