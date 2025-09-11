package dev.pakh.logic.handlers.characterCp;

import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.Direction;
import dev.pakh.models.geometry.HorizontalRange;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.utils.PixelInspectionUtils;
import dev.pakh.utils.SoundUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterCpHandler extends CaptureProcessor {
    private final int CP_LEFT_OFFSET = 19;
    private final int CP_RIGHT_OFFSET = 5;
    private final int CP_TOP_OFFSET = 31;
    private final String[] CP_BAR_VALID_COLORS = {"794A00", "885A00"};

    private final GameLayout gameLayout;
    private HorizontalRange cpRange;
    private int cpPosition;
    private boolean cpPositionInitialized = false;

    public CharacterCpHandler(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    @Override
    public void process(BufferedImage image) throws Exception {
        if (!cpPositionInitialized) {
            cpRange = this.computeCpRange(gameLayout);
            cpPosition = locateCpPosition(image);
            cpPositionInitialized = true;
            return;
        }

        int newPosition = locateCpPosition(image);
        if (newPosition < cpPosition)
            SoundUtils.danger();
        cpPosition = newPosition;
    }

    @Override
    protected int getTimeoutTicks() {
        return 10;
    }

    private HorizontalRange computeCpRange(GameLayout gameLayout) {
        RectangleArea boxArea = gameLayout.getCharacterInfoBoxArea();
        int y = boxArea.startY() + CP_TOP_OFFSET;
        int xStart = boxArea.startX() + CP_LEFT_OFFSET;
        int xEnd = boxArea.endX() - CP_RIGHT_OFFSET;
        return new HorizontalRange(y, xStart, xEnd);
    }

    private int locateCpPosition(BufferedImage image) {
        if (!cpPositionInitialized || currentPositionHasValidColor(image)) {
            if (isCpFull()) return cpPosition;
            return locateCpInDirection(image, Direction.RIGHT);
        }
        return locateCpInDirection(image, Direction.LEFT);
    }

    private boolean currentPositionHasValidColor(BufferedImage image) {
        Point point = new Point(cpRange.startX() + cpPosition, cpRange.y());
        return PixelInspectionUtils.hasValidColors(image, point, CP_BAR_VALID_COLORS);
    }

    private int locateCpInDirection(BufferedImage image, Direction direction) {
        int x = (direction == Direction.LEFT)
                ? cpRange.startX() + cpPosition
                : cpRange.endX();

        Point fromPoint = new Point(x, cpRange.y());

        Point foundPoint = PixelInspectionUtils.findValidElementLeft(
                image,
                fromPoint,
                CP_BAR_VALID_COLORS,
                cpRange.startX(),
                0,
                0
        );

        return (foundPoint != null) ? foundPoint.x : 0;
    }

    private boolean isCpFull() {
        return (cpRange.endX() - cpRange.startX() + 1) == cpPosition - CP_LEFT_OFFSET;
    }
}
