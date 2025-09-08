package dev.pakh.models.game;

import dev.pakh.models.geometry.HorizontalRange;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.skills.Skill;

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
    private RectangleArea fishingInfoBoxArea;
    private HorizontalRange monsterHpBar;

    // --- Validations ---
    public boolean areStartupAreasReady() {
        if (debugMode) {
            System.out.println("characterInfoBoxArea: " + (characterInfoBoxArea != null));
            System.out.println("chatArea: " + (chatArea != null));
            System.out.println("fishingShotArea: " + (fishingShotArea != null));
            System.out.println("Fishing skill: " + (FishingSkill != null));
            System.out.println("Pumping skill: " + (PumpingSkill != null));
            System.out.println("Reeling skill: " + (ReelingSkill != null));
            System.out.println("NextTarget skill: " + (NextTargetSkill != null));
            System.out.println("PetAttack skill: " + (PetAttackSkill != null));
            System.out.println("PetPickup skill: " + (PetPickupSkill != null));
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

    public boolean isFishingInfoBoxDetected() {
        return fishingInfoBoxArea != null;
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

    public RectangleArea getFishingInfoBoxArea() {
        return fishingInfoBoxArea;
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

    public void setFishingInfoBoxArea(RectangleArea fishingInfoBoxArea) {
        this.fishingInfoBoxArea = fishingInfoBoxArea;
    }

    public void setMonsterHpBar(HorizontalRange monsterHpBar) {
        this.monsterHpBar = monsterHpBar;
    }
}

