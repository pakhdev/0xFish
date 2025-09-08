package dev.pakh.logic.locators;

import dev.pakh.logic.signatures.SignaturesManager;
import dev.pakh.models.capture.CaptureProcessor;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.geometry.RectangleArea;
import dev.pakh.models.geometry.VerticalRange;
import dev.pakh.utils.PixelInspectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChatBoxLocator extends CaptureProcessor {
    private final GameLayout gameLayout;
    private final int BOTTOM_IDENTIFICATION_HEIGHT = 81;
    private final int CHAT_WIDTH = 315;
    private final int CHAT_LEFT_OFFSET = 5;
    private final int CHAT_BOTTOM_OFFSET = 51;
    private final int SCROLLBAR_WIDTH = 14;
    private final int SCROLLBAR_MIN_HEIGHT = 14;
    private final int SCROLLBAR_MAX_HEIGHT = 400;
    private final int SCROLLBAR_LEFT_OFFSET = 5;
    private final int MIN_SPACE_BETWEEN_ARROW_BOXES = 96;
    private final int MAX_SPACE_BETWEEN_ARROW_BOXES = 400;
    private final int ARROW_BOX_SIZE = 14;
    private final String[] VALID__BORDER_COLORS = {
            "403D35", "464339", "4A463D", "4A463C", "4C483E", "4F4B42",
            "444138", "423F36", "514D42", "545145", "555146", "5A564A",
            "625D53", "746E63", "787366", "797365", "7D7768", "7E786B",
            "878275", "898376"
    };

    public ChatBoxLocator(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    @Override
    public void process(BufferedImage image) {
        if (!hasValidChatBottomSignature(image)) {
            System.out.println("Critical error: Chat bottom part is incorrect");
            return;
        }

        VerticalRange scrollbarRange = findScrollbarRange(image);
        System.out.println("ScrollBar range " + scrollbarRange);

        if (scrollbarRange != null && !isScrollbarAtBottom(scrollbarRange, image)) {
            throw new RuntimeException("Chat scroll should be at the bottom");
        }

        int searchArrowUpFromY = scrollbarRange == null
                ? arrowDownBoxYTop(image) - MIN_SPACE_BETWEEN_ARROW_BOXES
                : scrollbarRange.startY() - 1;

        Point arrowUpTopPoint = findArrowUpBox(image, new Point(SCROLLBAR_LEFT_OFFSET, searchArrowUpFromY));

        int chatStartX = (int) arrowUpTopPoint.getX() + ARROW_BOX_SIZE + CHAT_LEFT_OFFSET - 1;
        int chatEndX = chatStartX + CHAT_WIDTH - 1;

        RectangleArea chatArea = new RectangleArea(
                chatStartX,
                chatEndX,
                (int) arrowUpTopPoint.getY(),
                chatBottomY(image)
        );
        gameLayout.setChatArea(chatArea);
    }

    private boolean hasValidChatBottomSignature(BufferedImage image) {
        int yPos = arrowDownBoxYTop(image);
        return PixelInspectionUtils.hasValidSignature(image, new Point(0, yPos), SignaturesManager.find("ChatBottom"));
    }

    private int arrowDownBoxYTop(BufferedImage image) {
        return image.getHeight() - BOTTOM_IDENTIFICATION_HEIGHT;
    }

    private int chatBottomY(BufferedImage image) {
        return image.getHeight() - CHAT_BOTTOM_OFFSET;
    }

    private VerticalRange findScrollbarRange(BufferedImage image) {
        int searchYLimit = arrowDownBoxYTop(image) - SCROLLBAR_MAX_HEIGHT + SCROLLBAR_MIN_HEIGHT;

        Point nextElement = PixelInspectionUtils.findValidElementUp(
                image,
                new Point(SCROLLBAR_LEFT_OFFSET, arrowDownBoxYTop(image)),
                VALID__BORDER_COLORS,
                searchYLimit,
                SCROLLBAR_WIDTH,
                0
        );

        if (nextElement != null) {
            System.out.println("Possible scrollbar found at " + nextElement);
            int scrollbarHeight = PixelInspectionUtils.countConsecutiveValidPixelsUp(
                    image,
                    nextElement,
                    SCROLLBAR_MAX_HEIGHT,
                    VALID__BORDER_COLORS
            );

            Point validationPoint = new Point((int) nextElement.getX(), (int) nextElement.getY() - scrollbarHeight);
            if (scrollbarHeight + 1 >= SCROLLBAR_MIN_HEIGHT && !matchesArrowUpPattern(image, validationPoint))
                return new VerticalRange(
                        (int) nextElement.getY(),
                        (int) nextElement.getY() - scrollbarHeight,
                        (int) nextElement.getY());
        }
        return null;
    }

    private boolean isScrollbarAtBottom(VerticalRange scrollbarRange, BufferedImage image) {
        return scrollbarRange.endY() == arrowDownBoxYTop(image) - 1 || scrollbarRange.endY() == arrowDownBoxYTop(image) - 2;
    }

    private Point findArrowUpBox(BufferedImage image, Point point) {
        int limitY = (int) point.getY() - MAX_SPACE_BETWEEN_ARROW_BOXES;
        Point arrowBox = PixelInspectionUtils.findValidElementUp(image, point, VALID__BORDER_COLORS, limitY, 0, 14);
        if (!matchesArrowUpPattern((image), arrowBox))
            throw new RuntimeException("Arrow up box not found");
        return arrowBox;
    }

    private boolean matchesArrowUpPattern(BufferedImage image, Point point) {
        return PixelInspectionUtils.hasValidSignature(image, point, SignaturesManager.find("ChatArrowUpBox"));
    }
}
