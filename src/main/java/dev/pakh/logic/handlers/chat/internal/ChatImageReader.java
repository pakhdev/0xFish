package dev.pakh.logic.handlers.chat.internal;

import dev.pakh.models.capture.ColorHSB;
import dev.pakh.models.capture.ColorRGB;
import dev.pakh.models.chat.ChatMessageLine;
import dev.pakh.models.chat.ChatSnapshot;
import dev.pakh.models.game.GameLayout;
import dev.pakh.models.signatures.ChatColor;
import dev.pakh.models.signatures.ChatColors;
import dev.pakh.utils.PixelInspectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is responsible for reading and interpreting the chat area of the game screen.
 * <p>
 * It captures the pixel data from the chat region and encodes each line into a
 * {@link ChatMessageLine}, which contains color information and a normalized representation
 * of the text pixels. The captured lines are aggregated into a {@link ChatSnapshot}.
 * <p>
 * The main goal is to provide a reliable and efficient way to extract chat messages
 * from the screen image without duplicating processing logic.
 * <p>
 * Usage:
 * <ul>
 *   <li>Create an instance by providing the {@link GameLayout} to locate the chat area.</li>
 *   <li>Call {@link #readChatSnapshot(BufferedImage)} with a captured screen image to obtain
 *       a {@link ChatSnapshot} containing all detected chat lines.</li>
 *   <li>The internal encoding process normalizes the pixel data to binary representation
 *       to simplify downstream message processing.</li>
 * </ul>
 */
public class ChatImageReader {
    private final GameLayout gameLayout;
    private final int LINE_HEIGHT = 15;
    private int totalLines = 0;

    /**
     * Constructs a ChatImageReader with the specified game layout.
     *
     * @param gameLayout the layout of the game window, used to determine
     *                   the chat area coordinates
     */
    public ChatImageReader(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        this.totalLines = countTotalLines();
    }

    /**
     * Reads the chat area from the given image and returns a snapshot
     * containing all lines of chat detected.
     * <p>
     * Lines are read from bottom to top, and only the first 160 pixels
     * in width are used for reliable detection.
     *
     * @param image the captured screen image
     * @return a {@link ChatSnapshot} containing all chat lines in order
     */
    public ChatSnapshot readChatSnapshot(BufferedImage image) {
        ChatSnapshot chatSnapshot = new ChatSnapshot();

        int xStart = gameLayout.getChatArea().startX();
        int xEnd = 160; // optimized width for line recognition

        for (int i = totalLines; i > 0; i--) {
            ChatMessageLine messageLine = readMessageLine(image, xStart, xEnd, calculateLineY(i));
            chatSnapshot.addMessage(messageLine);
        }
        return chatSnapshot;
    }

    /**
     * Reads a single line of chat from the specified coordinates in the image.
     *
     * @param image  the captured screen image
     * @param startX the starting X coordinate of the chat line
     * @param endX   the ending X coordinate of the chat line
     * @param y      the Y coordinate of the line
     * @return a {@link ChatMessageLine} containing color and normalized encoding
     */
    private ChatMessageLine readMessageLine(BufferedImage image, int startX, int endX, int y) {
        ChatColor messageColor = null;
        StringBuilder encoding = new StringBuilder();
//        int darkestShadow = 3;

        for (int x = startX; x <= endX; x++) {
            ColorRGB pixelRGB = PixelInspectionUtils.getRGBColor(image, new Point(x, y));
            ColorHSB pixelHSB = PixelInspectionUtils.getHSBColor(image, new Point(x, y));
            ChatColor pixelColorName = ChatColors.findColor(pixelRGB);
//            boolean isShadow = pixelColorName == ChatColors.SHADOW;
            boolean isShadow = pixelHSB.b <= 2;

            if (isShadow) {
//                darkestShadow = Math.min(darkestShadow, pixelRGB.r);
//                encoding.append(pixelRGB.r);
                encoding.append(1);
                continue;
            }

            if (messageColor == null && pixelColorName != null) {
                messageColor = pixelColorName;
            }

            encoding.append(0);
//            encoding.append(5);
        }

//        String normalizedEncoding = normalizeLineEncoding(encoding.toString(), darkestShadow);
        String normalizedEncoding = encoding.toString();
        return new ChatMessageLine(messageColor, normalizedEncoding);
    }

    /**
     * Converts the raw pixel encoding of a chat line into a normalized binary string.
     * <p>
     * Each character in the output represents whether a pixel value is darker than
     * the threshold determined by the darkest shadow.
     *
     * @param rawEncoding   the raw string encoding of pixel values
     * @param darkestShadow the darkest shadow value found in the line
     * @return a normalized binary string representing the line
     */
    private String normalizeLineEncoding(String rawEncoding, int darkestShadow) {
        StringBuilder normalized = new StringBuilder();
        int threshold = darkestShadow + 1;
        for (int i = 0; i < rawEncoding.length(); i++) {
            int value = Character.getNumericValue(rawEncoding.charAt(i));
            normalized.append(value <= threshold ? '1' : '0');
        }
        return normalized.toString();
    }

    /**
     * Calculates the Y coordinate of a specific line in the chat area.
     *
     * @param lineNumber the line number (1-based, bottom to top)
     * @return the Y coordinate of the line
     */
    private int calculateLineY(int lineNumber) {
        return gameLayout.getChatArea().startY() + LINE_HEIGHT * lineNumber;
    }

    /**
     * Computes the total number of chat lines available in the chat area
     * based on the configured line height.
     *
     * @return the total number of lines
     */
    private int countTotalLines() {
        int startY = gameLayout.getChatArea().startY();
        int endY = gameLayout.getChatArea().endY();
        int availableHeight = endY - startY;
        return availableHeight / LINE_HEIGHT;
    }
}
