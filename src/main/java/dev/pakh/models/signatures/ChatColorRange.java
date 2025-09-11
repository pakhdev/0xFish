package dev.pakh.models.signatures;

/**
 * Represents a range of color values for a single color channel (Red, Green, or Blue).
 * <p>
 * This class is used to define minimum and maximum values for a color channel and
 * provides utility methods to check if a specific color value falls within the range.
 * <p>
 * Usage examples:
 * <ul>
 *   <li>Create a range between two values: {@code ChatColorRange.between(50, 200)}</li>
 *   <li>Create a range with a minimum value: {@code ChatColorRange.atLeast(100)}</li>
 *   <li>Create a range with a maximum value: {@code ChatColorRange.atMost(150)}</li>
 *   <li>Check if a value is within the range: {@code range.contains(120)}</li>
 * </ul>
 */
public class ChatColorRange {
    /**
     * Minimum value of the color range (inclusive).
     */
    private final int min;

    /**
     * Maximum value of the color range (inclusive).
     */
    private final int max;

    /**
     * Constructs a new {@code ChatColorRange} with the specified minimum and maximum values.
     *
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (inclusive)
     */
    public ChatColorRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Checks if a given value is within this color range.
     *
     * @param value the color value to check
     * @return {@code true} if {@code value} is between {@code min} and {@code max} (inclusive),
     * {@code false} otherwise
     */
    public boolean contains(int value) {
        return value >= min && value <= max;
    }

    /**
     * Creates a color range with a specified minimum value and a maximum of 255.
     *
     * @param min the minimum value (inclusive)
     * @return a new {@code ChatColorRange} representing all values from {@code min} to 255
     */
    public static ChatColorRange atLeast(int min) {
        return new ChatColorRange(min, 255);
    }

    /**
     * Creates a color range with a specified maximum value and a minimum of 0.
     *
     * @param max the maximum value (inclusive)
     * @return a new {@code ChatColorRange} representing all values from 0 to {@code max}
     */
    public static ChatColorRange atMost(int max) {
        return new ChatColorRange(0, max);
    }

    /**
     * Creates a color range between the specified minimum and maximum values.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a new {@code ChatColorRange} representing all values from {@code min} to {@code max}
     */
    public static ChatColorRange between(int min, int max) {
        return new ChatColorRange(min, max);
    }
}

