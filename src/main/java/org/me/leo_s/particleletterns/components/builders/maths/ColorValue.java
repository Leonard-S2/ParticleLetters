package org.me.leo_s.particleletterns.components.builders.maths;

import org.bukkit.Bukkit;
import org.bukkit.Color;

import static org.me.leo_s.particleletterns.components.FileOutput.DEBUG_MODE;

public class ColorValue {
    public static Color fromChar(char letterChar) {
        return switch (letterChar) {
            case '1' -> Color.fromRGB(0, 0, 170);
            case '2' -> Color.fromRGB(0, 170, 0);
            case '3' -> Color.fromRGB(0, 170, 170);
            case '4' -> Color.fromRGB(170, 0, 0);
            case '5' -> Color.fromRGB(170, 0, 170);
            case '6' -> Color.fromRGB(255, 170, 0);
            case '7' -> Color.fromRGB(170, 170, 170);
            case '8' -> Color.fromRGB(85, 85, 85);
            case '9' -> Color.fromRGB(85, 85, 255);
            case 'A' -> Color.fromRGB(85, 255, 85);
            case 'B' -> Color.fromRGB(85, 255, 255);
            case 'C' -> Color.fromRGB(255, 85, 85);
            case 'D' -> Color.fromRGB(255, 85, 255);
            case 'E' -> Color.fromRGB(255, 255, 85);
            case 'F' -> Color.WHITE;
            default -> Color.BLACK;
        };
    }
    public static String colorToChar(Color color) {
        return switch (color.asRGB()) {
            case 170 -> "1";
            case 43520 -> "2";
            case 43690 -> "3";
            case 11141120 -> "4";
            case 11141290 -> "5";
            case 16755200 -> "6";
            case 11184810 -> "7";
            case 5592405 -> "8";
            case 5592575 -> "9";
            case 5635925 -> "A";
            case 5636095 -> "B";
            case 16733525 -> "C";
            case 16733795 -> "D";
            case 16777045 -> "E";
            case 16777215 -> "F";
            default -> "0";
        };
    }
    public static Color fromHex(String ColorScheme) {
        float[] rgb = java.awt.Color.decode("#" + ColorScheme).getRGBColorComponents(null);
        if(DEBUG_MODE) {
            Bukkit.getServer().getConsoleSender().sendMessage("§7[§cParticleDebug§7] §7ColorScheme: §f" + ColorScheme);
            Bukkit.getServer().getConsoleSender().sendMessage("§7[§cParticleDebug§7] §7RGB: §f" + (int) (rgb[0] * 255) + " " + (int) (rgb[1] * 255) + " " + (int) (rgb[2] * 255));
            Bukkit.getServer().getConsoleSender().sendMessage("§7[§cParticleDebug§7] §7Color: §f" + Color.fromRGB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255)));
        }
        return Color.fromRGB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255));
    }
}
