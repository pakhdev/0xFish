package dev.pakh.logic.signatures.entries;

import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.logic.signatures.models.SignatureEntry;

import java.util.List;

public class NextTargetSignature extends SignatureEntry {
    @Override
    public ElementVisualSignature get() {
        return new ElementVisualSignature("NextTarget", List.of(
                ColorPoint.of(12, 10, "ADD3DE"),
                ColorPoint.of(12, 16, "000C18"),
                ColorPoint.of(12, 22, "000008"),
                ColorPoint.of(19, 10, "9CC3CE"),
                ColorPoint.of(19, 16, "425160"),
                ColorPoint.of(19, 22, "7BA6BD")
        ));
    }
}
