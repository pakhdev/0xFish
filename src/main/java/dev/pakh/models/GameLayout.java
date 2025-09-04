package dev.pakh.models;

public class GameLayout {
    // --- Detected at startup ---
    private RectangleArea characterInfoBoxArea;
    private RectangleArea chatArea;
    private RectangleArea skillFishingArea;
    private RectangleArea skillPumpingArea;
    private RectangleArea skillReelingArea;
    private RectangleArea skillNextTargetArea;
    private RectangleArea skillWolfAttackArea;
    private RectangleArea fishingShotArea;

    // --- Detected during events ---
    private RectangleArea fishingInfoBoxArea;
    private HorizontalRange monsterHpBar;

    // --- Validations ---
    public boolean areStartupAreasReady() {
        return characterInfoBoxArea != null;
//                && chatArea != null
//                && skillFishingArea != null
//                && skillPumpingArea != null
//                && skillReelingArea != null
//                && skillNextTargetArea != null
//                && skillWolfAttackArea != null
//                && fishingShotArea != null;
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

    public RectangleArea getSkillFishingArea() {
        return skillFishingArea;
    }

    public RectangleArea getSkillPumpingArea() {
        return skillPumpingArea;
    }

    public RectangleArea getSkillReelingArea() {
        return skillReelingArea;
    }

    public RectangleArea getSkillNextTargetArea() {
        return skillNextTargetArea;
    }

    public RectangleArea getSkillWolfAttackArea() {
        return skillWolfAttackArea;
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

    public void setSkillFishingArea(RectangleArea skillFishingArea) {
        this.skillFishingArea = skillFishingArea;
    }

    public void setSkillPumpingArea(RectangleArea skillPumpingArea) {
        this.skillPumpingArea = skillPumpingArea;
    }

    public void setSkillReelingArea(RectangleArea skillReelingArea) {
        this.skillReelingArea = skillReelingArea;
    }

    public void setSkillNextTargetArea(RectangleArea skillNextTargetArea) {
        this.skillNextTargetArea = skillNextTargetArea;
    }

    public void setSkillWolfAttackArea(RectangleArea skillWolfAttackArea) {
        this.skillWolfAttackArea = skillWolfAttackArea;
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

