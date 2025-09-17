package dev.pakh.models.game;

import dev.pakh.logic.ports.FishingStateListener;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.skills.Skill;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameLayout {
    private final FishingStateListener fishingStateListener;

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
    private Point monsterHpPoint;

    public GameLayout(FishingStateListener fishingStateListener) {
        this.fishingStateListener = fishingStateListener;
    }

    // --- Validations ---
    public boolean areStartupAreasReady() {
        Map<Object, String> checks = new HashMap<>();
        checks.put(characterInfoBoxArea, "Character info box not detected");
        checks.put(chatArea, "Chat area not detected");
        checks.put(fishingShotArea, "Fishing shot area not detected");
        checks.put(FishingSkill, "Fishing skill not detected");
        checks.put(PumpingSkill, "Pumping skill not detected");
        checks.put(ReelingSkill, "Reeling skill not detected");
        checks.put(NextTargetSkill, "Next target skill not detected");
        checks.put(PetAttackSkill, "Pet attack skill not detected");
        checks.put(PetPickupSkill, "Pet pickup skill not detected");

        for (Map.Entry<Object, String> entry : checks.entrySet()) {
            if (entry.getKey() == null) {
                fishingStateListener.onError(entry.getValue());
                return false;
            }
        }

        return true;
    }


    public boolean isMonsterHpBarDetected() {
        boolean isDetected = monsterHpPoint != null;
        if (!isDetected) fishingStateListener.onError("Monster HP bar not found");
        return isDetected;
    }

    public boolean isFishingBoxDetected() {
        boolean isDetected = fishingBoxArea != null;
        if (!isDetected) fishingStateListener.onError("Fishing box not found not found");
        return isDetected;
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

    public Point getMonsterHpBar() {
        return monsterHpPoint;
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

    public void setMonsterHpBar(Point monsterHpBar) {
        this.monsterHpPoint = monsterHpBar;
    }
}

