package dev.pakh.models;

public class GameLayout {
    boolean debugMode = true;

    // --- Detected at startup ---
    private RectangleArea characterInfoBoxArea;
    private RectangleArea chatArea;
    private RectangleArea fishingShotArea;

    private int FishingKeyCode;
    private int PumpingKeyCode;
    private int ReelingKeyCode;
    private int NextTargetKeyCode;
    private int PetAttackKeyCode;
    private int PetPickupKeyCode;

    // --- Detected during events ---
    private RectangleArea fishingInfoBoxArea;
    private HorizontalRange monsterHpBar;

    // --- Validations ---
    public boolean areStartupAreasReady() {
        if (debugMode) {
            System.out.println("characterInfoBoxArea: " + (characterInfoBoxArea != null));
            System.out.println("chatArea: " + (chatArea != null));
            System.out.println("fishingShotArea: " + (fishingShotArea != null));
            System.out.println("FishingKeyCode: " + (FishingKeyCode != 0));
            System.out.println("PumpingKeyCode: " + (PumpingKeyCode != 0));
            System.out.println("ReelingKeyCode: " + (ReelingKeyCode != 0));
            System.out.println("NextTargetKeyCode: " + (NextTargetKeyCode != 0));
            System.out.println("PetAttackKeyCode: " + (PetAttackKeyCode != 0));
            System.out.println("PetPickupKeyCode: " + (PetPickupKeyCode != 0));
        }

        return characterInfoBoxArea != null
                && chatArea != null
                && fishingShotArea != null
                && FishingKeyCode != 0
                && PumpingKeyCode != 0
                && ReelingKeyCode != 0
                && NextTargetKeyCode != 0
                && PetAttackKeyCode != 0
                && PetPickupKeyCode != 0;
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

    public int getFishingKeyCode() {
        return FishingKeyCode;
    }

    public int getPumpingKeyCode() {
        return PumpingKeyCode;
    }

    public int getReelingKeyCode() {
        return ReelingKeyCode;
    }

    public int getNextTargetKeyCode() {
        return NextTargetKeyCode;
    }

    public int getPetAttackKeyCode() {
        return PetAttackKeyCode;
    }

    public int getPetPickupKeyCode() {
        return PetPickupKeyCode;
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

    public void setFishingKeyCode(int fishingKeyCode) {
        FishingKeyCode = fishingKeyCode;
    }

    public void setPumpingKeyCode(int pumpingKeyCode) {
        PumpingKeyCode = pumpingKeyCode;
    }

    public void setReelingKeyCode(int reelingKeyCode) {
        ReelingKeyCode = reelingKeyCode;
    }

    public void setNextTargetKeyCode(int nextTargetKeyCode) {
        NextTargetKeyCode = nextTargetKeyCode;
    }

    public void setPetAttackKeyCode(int petAttackKeyCode) {
        PetAttackKeyCode = petAttackKeyCode;
    }

    public void setPetPickupKeyCode(int petAttackKeyCode) {
        PetPickupKeyCode = petAttackKeyCode;
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

