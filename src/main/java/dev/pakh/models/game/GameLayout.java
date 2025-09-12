package dev.pakh.models.game;

import dev.pakh.models.geometry.HorizontalRange;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.skills.Skill;
import dev.pakh.utils.LoggerUtils;

public class GameLayout {
    boolean debugMode = true;

    // --- Detected at startup ---
    private RectangleArea characterInfoBoxArea;
    private RectangleArea chatArea;
    private RectangleArea fishingShotArea;

    private Skill FishingSkill;
    private Skill PumpingSkill;
    private Skill ReelingSkill;
    private Skill NextTargetSkill;
    private Skill PetAttackSkill;
    private Skill PetPickupSkill;

    // --- Detected during events ---
    private RectangleArea fishingBoxArea;
    private HorizontalRange monsterHpBar;

    // --- Validations ---
    public boolean areStartupAreasReady() {
        if (debugMode) {
            Object[][] info = {
                    {"characterInfoBoxArea", characterInfoBoxArea != null},
                    {"chatArea", chatArea != null},
                    {"fishingShotArea", fishingShotArea != null},
                    {"FishingSkill", FishingSkill != null},
                    {"PumpingSkill", PumpingSkill != null},
                    {"ReelingSkill", ReelingSkill != null},
                    {"NextTargetSkill", NextTargetSkill != null},
                    {"PetAttackSkill", PetAttackSkill != null},
                    {"PetPickupSkill", PetPickupSkill != null}
            };
            LoggerUtils.logBlock(info, 3); // 3 elementos por línea
        }

        return characterInfoBoxArea != null
                && chatArea != null
                && fishingShotArea != null
                && FishingSkill != null
                && PumpingSkill != null
                && ReelingSkill != null
                && NextTargetSkill != null
                && PetAttackSkill != null
                && PetPickupSkill != null;
    }

    public boolean isMonsterHpBarDetected() {
        return monsterHpBar != null;
    }

    public boolean isFishingBoxDetected() {
        return fishingBoxArea != null;
    }

    // --- Getters ---
    public RectangleArea getCharacterInfoBoxArea() {
        return characterInfoBoxArea;
    }

    public RectangleArea getChatArea() {
        return chatArea;
    }

    public Skill getFishingSkill() {
        return FishingSkill;
    }

    public Skill getPumpingSkill() {
        return PumpingSkill;
    }

    public Skill getReelingSkill() {
        return ReelingSkill;
    }

    public Skill getNextTargetSkill() {
        return NextTargetSkill;
    }

    public Skill getPetAttackSkill() {
        return PetAttackSkill;
    }

    public Skill getPetPickupSkill() {
        return PetPickupSkill;
    }

    public RectangleArea getFishingShotArea() {
        return fishingShotArea;
    }

    public RectangleArea getFishingBoxArea() {
        return fishingBoxArea;
    }

    public HorizontalRange getMonsterHpBar() {
        return monsterHpBar;
    }

    // --- Setters ---
    public void setCharacterInfoBoxArea(RectangleArea characterInfoBoxArea) {
        this.characterInfoBoxArea = characterInfoBoxArea;
    }

    public void setChatArea(RectangleArea chatArea) {
        this.chatArea = chatArea;
    }

    public void setFishingSkill(Skill fishingSkill) {
        FishingSkill = fishingSkill;
    }

    public void setPumpingSkill(Skill pumpingSkill) {
        PumpingSkill = pumpingSkill;
    }

    public void setReelingSkill(Skill reelingSkill) {
        ReelingSkill = reelingSkill;
    }

    public void setNextTargetSkill(Skill nextTargetSkill) {
        NextTargetSkill = nextTargetSkill;
    }

    public void setPetAttackSkill(Skill petAttackSkill) {
        PetAttackSkill = petAttackSkill;

    }

    public void setPetPickupSkill(Skill petAttackSkill) {
        PetPickupSkill = petAttackSkill;
    }

    public void setFishingShotArea(RectangleArea fishingShotArea) {
        this.fishingShotArea = fishingShotArea;
    }

    public void setFishingBoxArea(RectangleArea fishingBoxArea) {
        this.fishingBoxArea = fishingBoxArea;
    }

    public void setMonsterHpBar(HorizontalRange monsterHpBar) {
        this.monsterHpBar = monsterHpBar;
    }
}

