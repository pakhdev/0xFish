package dev.pakh.models.skills;

import java.awt.image.BufferedImage;

public interface SkillCondition {
    boolean canActivate(BufferedImage image);
}
