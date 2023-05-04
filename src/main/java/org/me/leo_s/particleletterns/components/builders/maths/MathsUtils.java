package org.me.leo_s.particleletterns.components.builders.maths;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;
import org.me.leo_s.particleletterns.components.text.TextParticle;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MathsUtils {
    public static String color(String string) {
        if (string == null) return null;
        return string.replace("&", "§");
    }
    public static List<String> color(List<String> strings) {
        if (strings == null) return null;
        return strings.stream().map(MathsUtils::color).toList();
    }
    public static List<TextComponent> colorComponent(List<String> strings) {
        if (strings == null) return null;
        return strings.stream().map(string -> Component.text(color(string))).toList();
    }

    public static TextParticle loadTextFromJson(FileReader f) {
        try {
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(f);
            String text = (String) jsonObject.get("text");
            int timePerLetter = ((Long) jsonObject.get("timePerLetter")).intValue();
            double lengthLines = (double) jsonObject.get("lengthLines");
            double spaceLetters = (double) jsonObject.get("spaceLetters");

            if(!text.matches(".*&.*")) {
                Color color = Color.deserialize((JSONObject) jsonObject.get("color"));
                return new TextParticle(text.toUpperCase(), timePerLetter, color, lengthLines, spaceLetters);
            }

            return new TextParticle(text.toUpperCase(), timePerLetter, lengthLines, spaceLetters);
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleLetters&8] &cError loading text from JSON file: " + e.getMessage()));
        }
        return null;
    }

    public static Map<String, Integer> colorToJson(Color color) {
        return Map.of(
                "RED", color.getRed(),
                "GREEN", color.getGreen(),
                "BLUE", color.getBlue()
        );
    }

    public static boolean isTextValid(String text) throws TextFormattedInvalid {
        if(!text.matches("^([A-Za-z0-9Ññ& ]+)$")) {
            throw new TextFormattedInvalid("§8[§cParticleLetters§8] §7The text can only contain letters from A - Z, numbers from 0 - 9 and the character &.");
        }
        return true;
    }

    public static String clearVanillaText(String text) {
        return text.replaceAll("&[A-Za-z0-9]", "").replaceAll(" ", "_");
    }

}
