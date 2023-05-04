package org.me.leo_s.particleletterns.components.builders.maths;

import org.bukkit.Color;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;

public class ColorValue {
    public static int[] fromStringBukkit(String color) throws TextFormattedInvalid {
        return switch (color) {
            case "RED" -> new int[]{255, 0, 0};
            case "GREEN" -> new int[]{0, 255, 0};
            case "BLUE" -> new int[]{0, 0, 255};
            case "YELLOW" -> new int[]{255, 255, 0};
            case "PURPLE" -> new int[]{255, 0, 255};
            case "CYAN" -> new int[]{0, 255, 255};
            case "WHITE" -> new int[]{255, 255, 255};
            case "BLACK" -> new int[]{0, 0, 0};
            case "ORANGE" -> new int[]{255, 128, 0};
            case "PINK" -> new int[]{255, 0, 128};
            case "LIME" -> new int[]{128, 255, 0};
            case "LIGHT_BLUE" -> new int[]{0, 255, 128};
            case "MAGENTA" -> new int[]{128, 0, 255};
            case "LIGHT_GRAY" -> new int[]{128, 128, 128};
            case "GRAY" -> new int[]{64, 64, 64};
            case "BROWN" -> new int[]{128, 64, 0};
            default -> throw new TextFormattedInvalid("§7The color must be §cRED, GREEN, BLUE, YELLOW, PURPLE, CYAN, WHITE, BLACK, ORANGE, PINK, LIME, LIGHT_BLUE, MAGENTA, LIGHT_GRAY, GRAY or BROWN§7.");
        };
    }

    public static Color fromStringBukkitColor(String color) throws TextFormattedInvalid {
        return switch (color) {
            case "RED" -> Color.RED;
            case "GREEN" -> Color.GREEN;
            case "BLUE" -> Color.BLUE;
            case "YELLOW" -> Color.YELLOW;
            case "PURPLE" -> Color.PURPLE;
            case "CYAN" -> Color.AQUA;
            case "WHITE" -> Color.WHITE;
            case "BLACK" -> Color.BLACK;
            case "ORANGE" -> Color.fromRGB(255, 128, 0);
            case "PINK" -> Color.fromRGB(255, 0, 128);
            case "LIME" -> Color.fromRGB(128, 255, 0);
            case "LIGHT_BLUE" -> Color.fromRGB(0, 255, 128);
            case "MAGENTA" -> Color.fromRGB(128, 0, 255);
            case "LIGHT_GRAY" -> Color.fromRGB(128, 128, 128);
            case "GRAY" -> Color.fromRGB(64, 64, 64);
            case "BROWN" -> Color.fromRGB(128, 64, 0);
            default -> throw new TextFormattedInvalid("§7The color must be §cRED, GREEN, BLUE, YELLOW, PURPLE, CYAN, WHITE, BLACK, ORANGE, PINK, LIME, LIGHT_BLUE, MAGENTA, LIGHT_GRAY, GRAY or BROWN§7.");
        };
    }

    public static Color fromRGBBukkitColor(int[] colorRGB) {
        return Color.fromRGB(colorRGB[0], colorRGB[1], colorRGB[2]);
    }
}
