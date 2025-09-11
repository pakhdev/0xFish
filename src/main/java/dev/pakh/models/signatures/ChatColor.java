package dev.pakh.models.signatures;

import dev.pakh.models.capture.ColorRGB;

/**
 * Represents a color by defining acceptable ranges for its Red, Green, and Blue channels.
 * <p>
 * This class allows you to define a "named color" with flexible thresholds for each
 * RGB channel. For example, the color "Red" can have ranges that tolerate slight variations
 * in R, G, and B values while still being considered red.
 * <p>
 * Usage examples:
 * <ul>
 *   <li>Create a named color:
 *       {@code ChatColor red = new ChatColor("Red", ChatColorRange.between(200, 255),
 *       ChatColorRange.atMost(50), ChatColorRange.atMost(50));}</li>
 *   <li>Check if a color value matches this named color:
 *       {@code red.matches(210, 30, 40); // returns true}</li>
 * </ul>
 */
public class ChatColor {
    /**
     * The human-readable name of the color.
     */
    private final String name;

    /**
     * The range of acceptable values for the Red channel (0–255).
     */
    private final ChatColorRange redRange;

    /**
     * The range of acceptable values for the Green channel (0–255).
     */
    private final ChatColorRange greenRange;

    /**
     * The range of acceptable values for the Blue channel (0–255).
     */
    private final ChatColorRange blueRange;

    /**
     * Constructs a new {@code ChatColor} with the specified name and RGB ranges.
     *
     * @param name       the name of the color
     * @param redRange   the range of acceptable Red values
     * @param greenRange the range of acceptable Green values
     * @param blueRange  the range of acceptable Blue values
     */
    public ChatColor(String name, ChatColorRange redRange, ChatColorRange greenRange, ChatColorRange blueRange) {
        this.name = name;
        this.redRange = redRange;
        this.greenRange = greenRange;
        this.blueRange = blueRange;
    }

    /**
     * Returns the name of this color.
     *
     * @return the color name
     */
    public String getName() {
        if (name == null) return "Unknown";
        return name;
    }

    /**
     * Checks if the provided RGB values fall within this color's defined ranges.
     *
     * @param r the Red value (0–255)
     * @param g the Green value (0–255)
     * @param b the Blue value (0–255)
     * @return {@code true} if all RGB values are within their respective ranges, {@code false} otherwise
     */
    public boolean matches(ColorRGB colorRGB) {
        return redRange.contains(colorRGB.r) &&
                greenRange.contains(colorRGB.g) &&
                blueRange.contains(colorRGB.b);
    }

    @Override
    public String toString() {
        return name;
    }
}
