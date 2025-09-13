package dev.pakh.logic.locators;

import dev.pakh.logic.signatures.ElementSignaturesManager;
import dev.pakh.models.signatures.ElementVisualSignature;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.geometry.VerticalRange;
import dev.pakh.models.skills.AlwaysReadyCondition;
import dev.pakh.models.skills.CooldownCondition;
import dev.pakh.models.skills.Skill;
import dev.pakh.models.skills.SkillCondition;
import dev.pakh.ui.MessageBox;
import dev.pakh.utils.PixelCounterUtils;
import dev.pakh.utils.PixelFinderUtils;
import dev.pakh.utils.PixelValidationUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// TODO: SPLIT IN 3
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
    public void process(BufferedImage image) throws AWTException {
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
                Point foundPoint = PixelFinderUtils.findValidElementRight(
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
        return PixelValidationUtils.hasValidSignature(
                image,
                point,
                ElementSignaturesManager.find("SkillsPanelLeftBorder")
        );
    }

    private boolean matchesBottomLeftBorderPattern(BufferedImage image, Point point) {
        return PixelValidationUtils.hasValidSignature(
                image,
                point,
                ElementSignaturesManager.find("SkillsPanelLeftBorderBottom")
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

            boolean hasValidBorder = PixelCounterUtils.countConsecutiveValidPixelsUp(
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

    private SkillsAndFishingShotsLocator findSkills(BufferedImage image, VerticalRange panelLeftBorder) throws AWTException {
        initSkill(image, panelLeftBorder, "Fishing", false, gameLayout::setFishingSkill);
        initSkill(image, panelLeftBorder, "Pumping", true, gameLayout::setPumpingSkill);
        initSkill(image, panelLeftBorder, "Reeling", true, gameLayout::setReelingSkill);
        initSkill(image, panelLeftBorder, "NextTarget", false, gameLayout::setNextTargetSkill);
        initSkill(image, panelLeftBorder, "PetAttack", false, gameLayout::setPetAttackSkill);
        initSkill(image, panelLeftBorder, "PetPickup", false, gameLayout::setPetPickupSkill);
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
            for (String signatureName : signatures) {
                int slotNumber = findSkillSlotOnPanel(image, panelLeftBorder, ElementSignaturesManager.find(signatureName));
                if (slotNumber != 0) {
                    RectangleArea slotArea = getPanelSlotArea(panelLeftBorder, slotNumber);
                    gameLayout.setFishingShotArea(slotArea);
                    return this;
                }
            }
        }

        return this;
    }

    private int findSkillSlotOnPanel(BufferedImage image, VerticalRange panelLeftBorder,
                                     ElementVisualSignature visualSignature) {
        int totalSlots = PANEL_WIDTH / (SKILL_ICON_SIZE + DISTANCE_BETWEEN_SKILL_ICONS);

        for (int slot = 1; slot <= totalSlots; slot++) {
            Point slotPosition = getPanelSlotPosition(panelLeftBorder, slot);
            if (PixelValidationUtils.hasValidSignature(image, slotPosition, visualSignature))
                return slot;
        }

        return 0;
    }

    private Point getPanelSlotPosition(VerticalRange panelLeftBorder, int targetSlotNumber) {
        // X
        int baseX = panelLeftBorder.x() + FIRST_SKILL_ICON_PANEL_OFFSET_X;
        int stepX = SKILL_ICON_SIZE + DISTANCE_BETWEEN_SKILL_ICONS;
        int slotsBefore = targetSlotNumber - 1;
        int extraGaps = slotsBefore / 4;
        int x = baseX + slotsBefore * stepX + extraGaps * DISTANCE_BETWEEN_SKILL_ICONS_PLUS;

        // Y
        int y = panelLeftBorder.startY() + SKILL_ICON_PANEL_OFFSET_Y;
        return new Point(x, y);
    }

    private RectangleArea getPanelSlotArea(VerticalRange panelLeftBorder, int targetSlotNumber) {
        Point slotPosition = getPanelSlotPosition(panelLeftBorder, targetSlotNumber);
        int xStart = (int) slotPosition.getX();
        int yStart = (int) slotPosition.getY();
        return new RectangleArea(
                xStart,
                xStart + SKILL_ICON_SIZE - 1,
                yStart,
                yStart + SKILL_ICON_SIZE - 1
        );
    }

    private void initSkill(BufferedImage image,
                           VerticalRange panel,
                           String skillName,
                           boolean hasCooldown,
                           Consumer<Skill> setter) throws AWTException {

        int keyCode = findSkillSlotOnPanel(image, panel, ElementSignaturesManager.find(skillName));

        if (keyCode == 0) {
            System.out.println(skillName + " skill not found");
            return;
        }

        SkillCondition condition = hasCooldown
                ? new CooldownCondition(ElementSignaturesManager.find(skillName + "Cooldown"),
                getPanelSlotPosition(panel, keyCode))
                : new AlwaysReadyCondition();

        Skill skill = new Skill(keyCode, condition);
        setter.accept(skill);
    }

}
