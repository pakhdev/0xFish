package dev.pakh.models.skills;

import java.awt.image.BufferedImage;

public class AlwaysReadyCondition implements SkillCondition {
    @Override
    public boolean canActivate(BufferedImage image) {
        return true;
    }
}
