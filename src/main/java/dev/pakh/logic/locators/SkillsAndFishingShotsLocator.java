package dev.pakh.logic.locators;

import dev.pakh.logic.signatures.SignaturesManager;
import dev.pakh.logic.signatures.entries.FishingSignature;
import dev.pakh.logic.signatures.models.ColorPoint;
import dev.pakh.logic.signatures.models.ElementVisualSignature;
import dev.pakh.models.*;
import dev.pakh.ui.MessageBox;
import dev.pakh.utils.PixelInspectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SkillsAndFishingShotsLocator extends CaptureProcessor {
    private final Boolean debugMode = true;
    private final GameLayout gameLayout;

    // Skills panel
    private final int Y_DISTANCE_BETWEEN_PANELS = 4;
    private final int SKIP_LEFT_DISTANCE = 345;
    private final int SKIP_BOTTOM_DISTANCE = 15;
    private final int VALID_LEFT_BORDER_HEIGHT = 42;
    private final String[] VALID_LEFT_BORDER_COLORS = {
            "AB9C88", "B1A493", "B2A393", "B2A493", "B3A393", "C0B5A6", "C2B8A9"
    };
    private final int PANEL_WIDTH = 490;

    // Skills
    private final int FIRST_SKILL_ICON_PANEL_OFFSET_X = 37;
    private final int SKILL_ICON_PANEL_OFFSET_Y = 6;
    private final int DISTANCE_BETWEEN_SKILL_ICONS = 5;
    private final int DISTANCE_BETWEEN_SKILL_ICONS_PLUS = 7;
    private final int SKILL_ICON_SIZE = 32;

    public SkillsAndFishingShotsLocator(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    @Override
    public void process(BufferedImage image) {
        List<VerticalRange> skillsPanels = new ArrayList<>();

        VerticalRange leftBorderRange = findLeftBorder(image);
        if (leftBorderRange == null) {
            MessageBox.error("Left border of skills panel not found");
            return;
        }

        skillsPanels.add(leftBorderRange);
        this.findMorePanels(image, skillsPanels)
                .findSkills(image, skillsPanels.get(0))
                .findFishingShots(image, skillsPanels);
    }

    public VerticalRange findLeftBorder(BufferedImage image) {
        if (debugMode) System.out.println("#Skills panel: Searching left border");
        int x = SKIP_LEFT_DISTANCE;
        int xLimit = image.getWidth();
        int y = image.getHeight() - SKIP_BOTTOM_DISTANCE - Math.round((float) VALID_LEFT_BORDER_HEIGHT / 2);
        int yLimit = image.getHeight() / 2;

        while (y > yLimit) {
            while (x < xLimit) {
                Point foundPoint = PixelInspectionUtils.findValidElementRight(
                        image,
                        new Point(x, y),
                        VALID_LEFT_BORDER_COLORS,
                        xLimit - 1,
                        0,
                        VALID_LEFT_BORDER_HEIGHT
                );
                if (foundPoint != null) {
                    if (matchesBottomLeftBorderPattern(image, foundPoint)) {
                        if (debugMode)
                            System.out.println("Skills panel: Left border found at X:" + foundPoint.x + ",  Y:" + foundPoint.y);
                        return new VerticalRange(
                                foundPoint.x,
                                foundPoint.y,
                                foundPoint.y + VALID_LEFT_BORDER_HEIGHT - 1
                        );
                    } else {
                        x = foundPoint.x + 1;
                    }
                } else break;
            }
            y -= VALID_LEFT_BORDER_HEIGHT;
        }
        return null;
    }

    private boolean matchesLeftBorderPattern(BufferedImage image, Point point) {
        return PixelInspectionUtils.hasValidSignature(
                image,
                point,
                SignaturesManager.find("SkillsPanelLeftBorder")
        );
    }

    private boolean matchesBottomLeftBorderPattern(BufferedImage image, Point point) {
        return PixelInspectionUtils.hasValidSignature(
                image,
                point,
                SignaturesManager.find("SkillsPanelLeftBorderBottom")
        );
    }

    private SkillsAndFishingShotsLocator findMorePanels(BufferedImage image, List<VerticalRange> skillsPanels) {
        if (skillsPanels.isEmpty()) return this;

        while (true) {
            VerticalRange lastPanel = getLast(skillsPanels);
            Point targetPoint = new Point(lastPanel.x(), lastPanel.startY() - Y_DISTANCE_BETWEEN_PANELS);
            Point nextPanelTop = new Point(
                    lastPanel.x(),
                    lastPanel.startY() - Y_DISTANCE_BETWEEN_PANELS - VALID_LEFT_BORDER_HEIGHT
            );

            boolean hasValidBorder = PixelInspectionUtils.countConsecutiveValidPixelsUp(
                    image,
                    targetPoint,
                    VALID_LEFT_BORDER_HEIGHT + 1,
                    VALID_LEFT_BORDER_COLORS
            ) == VALID_LEFT_BORDER_HEIGHT;

            boolean matchesPattern = matchesLeftBorderPattern(image, nextPanelTop);

            if (debugMode) System.out.println("Searching more panels. Has valid border:" + hasValidBorder + ", " +
                    "matches pattern:" + matchesPattern);

            if (hasValidBorder && matchesPattern) {
                VerticalRange foundPanel = new VerticalRange(
                        lastPanel.x(),
                        (int) nextPanelTop.getY(),
                        (int) nextPanelTop.getY() + VALID_LEFT_BORDER_HEIGHT
                );
                skillsPanels.add(foundPanel);
            } else break;
        }
        return this;
    }

    private VerticalRange getLast(List<VerticalRange> panels) {
        return panels.get(panels.size() - 1);
    }

    private SkillsAndFishingShotsLocator findSkills(BufferedImage image, VerticalRange panelLeftBorder) {
        int fishingKeyCode = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find("Fishing"));
        if (fishingKeyCode == 0)
            System.out.println("Fishing skill not found");
        else
            gameLayout.setFishingKeyCode(fishingKeyCode);

        int pumpingKeyCode = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find("Pumping"));
        if (pumpingKeyCode == 0)
            System.out.println("Pumping skill not found");
        else
            gameLayout.setPumpingKeyCode(pumpingKeyCode);

        int reelingKeyCode = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find("Reeling"));
        if (reelingKeyCode == 0)
            System.out.println("Reeling skill not found");
        else
            gameLayout.setReelingKeyCode(reelingKeyCode);

        int nextTargetKeyCode = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find("NextTarget"));
        if (nextTargetKeyCode == 0)
            System.out.println("NextTarget skill not found");
        else
            gameLayout.setNextTargetKeyCode(nextTargetKeyCode);

        int petAttackKeyCode = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find("PetAttack"));
        if (petAttackKeyCode == 0)
            System.out.println("PetAttack skill not found");
        else
            gameLayout.setPetAttackKeyCode(petAttackKeyCode);

        int petPickupKeyCode = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find("PetPickup"));
        if (petPickupKeyCode == 0)
            System.out.println("PetPickup skill not found");
        else
            gameLayout.setPetPickupKeyCode(petPickupKeyCode);


        return this;
    }

    private SkillsAndFishingShotsLocator findFishingShots(BufferedImage image, List<VerticalRange> skillsPanels) {
        List<String> signatures = List.of(
                "DisabledFishingShots",
                "EnabledFishingShotsV1",
                "EnabledFishingShotsV2",
                "EnabledFishingShotsV3"
        );

        for (VerticalRange panelLeftBorder : skillsPanels) {
            int skillSlot = 0;

            for (String signatureName : signatures) {
                skillSlot = findSkillSlotOnPanel(image, panelLeftBorder, SignaturesManager.find(signatureName));
                if (skillSlot != 0) break;
            }

            if (skillSlot != 0) {
                int xStart = getPanelSlotXPosition(panelLeftBorder, skillSlot);
                int yStart = panelLeftBorder.startY() + SKILL_ICON_PANEL_OFFSET_Y;
                gameLayout.setFishingShotArea(new RectangleArea(
                        xStart,
                        xStart + SKILL_ICON_SIZE - 1,
                        yStart,
                        yStart + SKILL_ICON_SIZE - 1
                ));
            }
        }

        return this;
    }

    private int findSkillSlotOnPanel(BufferedImage image, VerticalRange panelLeftBorder,
                                     ElementVisualSignature visualSignature) {

        int totalSlots = PANEL_WIDTH / (SKILL_ICON_SIZE + DISTANCE_BETWEEN_SKILL_ICONS);
        int y = panelLeftBorder.startY() + SKILL_ICON_PANEL_OFFSET_Y;

        for (int slot = 1; slot <= totalSlots; slot++) {
            int x = getPanelSlotXPosition(panelLeftBorder, slot);

            if (debugMode) System.out.println("Panel slot " + slot + " at x=" + x);

            if (PixelInspectionUtils.hasValidSignature(image, new Point(x, y), visualSignature)) {
                return slot;
            }
        }

        return 0;
    }

    private int getPanelSlotXPosition(VerticalRange panelLeftBorder, int targetSlotNumber) {
        int baseX = panelLeftBorder.x() + FIRST_SKILL_ICON_PANEL_OFFSET_X;
        int stepX = SKILL_ICON_SIZE + DISTANCE_BETWEEN_SKILL_ICONS;

        int slotsBefore = targetSlotNumber - 1;
        int extraGaps = slotsBefore / 4;

        return baseX + slotsBefore * stepX + extraGaps * DISTANCE_BETWEEN_SKILL_ICONS_PLUS;
    }

}
