package dev.pakh.models.signatures;

import dev.pakh.models.capture.ColorRGB;

import java.util.List;

public class ChatColors {
    public static final ChatColor GREEN = new ChatColor(
            "Green",
            ChatColorRange.atMost(5),
            ChatColorRange.atLeast(240),
            ChatColorRange.atMost(5)
    );

    public static final ChatColor PURPLE = new ChatColor(
            "Purple",
            ChatColorRange.atLeast(248),
            ChatColorRange.atMost(5),
            ChatColorRange.atLeast(248)
    );

    public static final ChatColor PINK = new ChatColor(
            "Pink",
            ChatColorRange.atLeast(249),
            ChatColorRange.between(0, 1),
            ChatColorRange.between(125, 127)
    );

    public static final ChatColor YELLOW = new ChatColor(
            "Yellow",
            ChatColorRange.between(249, 254),
            ChatColorRange.between(249, 254),
            ChatColorRange.atMost(2)
    );

    public static final ChatColor ORANGE = new ChatColor(
            "Orange",
            ChatColorRange.atLeast(249),
            ChatColorRange.between(119, 128),
            ChatColorRange.atMost(5)
    );

    public static final ChatColor CYAN = new ChatColor(
            "Cyan",
            ChatColorRange.between(126, 128),
            ChatColorRange.between(249, 253),
            ChatColorRange.between(249, 253)
    );

    public static final ChatColor TEAL = new ChatColor(
            "Teal",
            ChatColorRange.between(89, 90),
            ChatColorRange.between(172, 176),
            ChatColorRange.between(172, 176)
    );

    public static final ChatColor BLUE = new ChatColor(
            "Blue",
            ChatColorRange.between(94, 96),
            ChatColorRange.between(110, 112),
            ChatColorRange.atLeast(248)
    );

    public static final ChatColor BEIGE = new ChatColor(
            "Beige",
            ChatColorRange.between(173, 176),
            ChatColorRange.between(152, 155),
            ChatColorRange.between(120, 121)
    );

    public static final ChatColor GREY = new ChatColor(
            "Grey",
            ChatColorRange.between(215, 220),
            ChatColorRange.between(215, 220),
            ChatColorRange.between(215, 220)
    );

    public static final ChatColor OLIVE = new ChatColor(
            "Olive",
            ChatColorRange.between(126, 127),
            ChatColorRange.between(126, 127),
            ChatColorRange.between(63, 64)
    );

    public static final ChatColor SALMON = new ChatColor(
            "Salmon",
            ChatColorRange.between(250, 253),
            ChatColorRange.between(127, 128),
            ChatColorRange.between(127, 128)
    );

    public static final ChatColor SHADOW = new ChatColor(
            "Shadow",
            ChatColorRange.atMost(3),
            ChatColorRange.atMost(3),
            ChatColorRange.atMost(3)
    );

    public static final ChatColor UNKNOWN = new ChatColor(
            "Unknown",
            ChatColorRange.atLeast(256),
            ChatColorRange.atLeast(256),
            ChatColorRange.atLeast(256)
    );

    public static final List<ChatColor> ALL = List.of(
            GREEN, PURPLE, PINK, YELLOW, ORANGE, CYAN, TEAL, BLUE, BEIGE, GREY, OLIVE, SALMON, SHADOW
    );

    public static ChatColor findColor(ColorRGB colorRGB) {
        return ALL.stream()
                .filter(color -> color.matches(colorRGB))
                .findFirst()
                .orElse(null);
    }

}
