package dev.pakh.logic.handlers.characterHp;

import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.Direction;
import dev.pakh.models.geometry.HorizontalRange;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.utils.PixelColorUtils;
import dev.pakh.utils.PixelFinderUtils;
import dev.pakh.utils.SoundUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterHpHandler extends CaptureProcessor {
    private final int HP_LEFT_OFFSET = 19;
    private final int HP_RIGHT_OFFSET = 5;
    private final int HP_TOP_OFFSET = 44;
    private final String[] HP_BAR_VALID_COLORS = {"6B170D", "791C11"};

    private final GameLayout gameLayout;
    private HorizontalRange hpRange;
    private int hpPosition;
    private boolean hpPositionInitialized = false;

    public CharacterHpHandler(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    @Override
    public void process(BufferedImage image) throws Exception {
        if (!hpPositionInitialized) {
            hpRange = this.computeHpRange(gameLayout);
            hpPosition = locateHpPosition(image);
            hpPositionInitialized = true;
            return;
        }

        int newPosition = locateHpPosition(image);
        if (newPosition < hpPosition)
            SoundUtils.danger();
        hpPosition = newPosition;
    }

    @Override
    protected int getTimeoutTicks() {
        return 10;
    }

    private HorizontalRange computeHpRange(GameLayout gameLayout) {
        RectangleArea boxArea = gameLayout.getCharacterInfoBoxArea();
        int y = boxArea.startY() + HP_TOP_OFFSET;
        int xStart = boxArea.startX() + HP_LEFT_OFFSET;
        int xEnd = boxArea.endX() - HP_RIGHT_OFFSET;
        return new HorizontalRange(y, xStart, xEnd);
    }

    private int locateHpPosition(BufferedImage image) {
        if (!hpPositionInitialized || currentPositionHasValidColor(image)) {
            if (isHpFull()) return hpPosition;
            return locateHpInDirection(image, Direction.RIGHT);
        }
        return locateHpInDirection(image, Direction.LEFT);
    }

    private boolean currentPositionHasValidColor(BufferedImage image) {
        Point point = new Point(hpRange.startX() + hpPosition, hpRange.y());
        return PixelColorUtils.hasValidColors(image, point, HP_BAR_VALID_COLORS);
    }

    private int locateHpInDirection(BufferedImage image, Direction direction) {
        int x = (direction == Direction.LEFT)
                ? hpRange.startX() + hpPosition
                : hpRange.endX();

        Point fromPoint = new Point(x, hpRange.y());

        Point foundPoint = PixelFinderUtils.findValidElementLeft(
                image,
                fromPoint,
                HP_BAR_VALID_COLORS,
                hpRange.startX(),
                0,
                0
        );

        return (foundPoint != null) ? foundPoint.x : 0;
    }

    private boolean isHpFull() {
        return (hpRange.endX() - hpRange.startX() + 1) == hpPosition - HP_LEFT_OFFSET;
    }
}
