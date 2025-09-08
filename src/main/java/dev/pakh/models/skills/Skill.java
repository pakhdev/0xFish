package dev.pakh.models.skills;

import dev.pakh.utils.KeyPressUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Skill {
    private int keyNumber;
    private SkillCondition condition;
    private KeyPressUtils keyPressUtils;

    public Skill(int keyNumber, SkillCondition condition) throws AWTException {
        this.keyNumber = keyNumber;
        this.condition = condition;
        this.keyPressUtils = new KeyPressUtils();
    }

    public boolean activate(BufferedImage image) {
        if (condition.canActivate(image)) {
            keyPressUtils.pressF(keyNumber);
            return true;
        }
        return false;
    }
}

