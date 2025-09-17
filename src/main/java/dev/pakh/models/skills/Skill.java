package dev.pakh.models.skills;

import dev.pakh.utils.KeyPressUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Skill {
    private final boolean debugMode = true;
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
            System.out.printf("Key F%d activated%n", keyNumber);
            keyPressUtils.pressF(keyNumber);
            return true;
        } else {
            System.out.printf("Key F%d can not be activated%n", keyNumber);
        }
        return false;
    }

    public void activateWithInterval(int count, int intervalMs) {
        keyPressUtils.pressFWithInterval(keyNumber, count, intervalMs);
    }
}

