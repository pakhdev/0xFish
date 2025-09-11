package dev.pakh.models.skills;

import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.utils.PixelInspectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CooldownCondition implements SkillCondition {
    private ElementVisualSignature cooldownSignature;
    private Point iconPosition;

    public CooldownCondition(ElementVisualSignature cooldownSignature, Point iconPosition) {
        this.cooldownSignature = cooldownSignature;
        this.iconPosition = iconPosition;
    }

    @Override
    public boolean canActivate(BufferedImage image) {
        return PixelInspectionUtils.hasValidSignature(image, iconPosition, cooldownSignature);
    }
}
