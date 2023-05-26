package org.me.leo_s.particleletterns.components.builders.maths;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;
import org.me.leo_s.particleletterns.components.text.TextParticle;

import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.me.leo_s.particleletterns.components.FileOutput.DEBUG_MODE;
import static org.me.leo_s.particleletterns.components.text.TextParticle.invertLetter;

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

            return new TextParticle(text, timePerLetter, lengthLines, spaceLetters);
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleLetters&8] &cError loading text from JSON file: " + e.getMessage()));
        }
        return null;
    }

    public static boolean isTextValid(String text) throws TextFormattedInvalid {
        if(!text.matches("^([A-Za-z0-9Ññ&# ]+)$")) {
            throw new TextFormattedInvalid("&7The text can only contain letters from &cA - Z&7, numbers from &c0 - 9&7 and the character &c&&7.");
        }
        return true;
    }

    public static String clearVanillaText(String text) {
        return text.replaceAll("&[A-Za-z0-9]", "").replaceAll(" ", "_");
    }

    /**
     * @param text The text to get the letters from.
     * @return A list of letters from the text.
     * @apiNote This method is not recommended for use, it is only used for debugging.
     */
    public static List<TextParticle.Letter> getLettersForEachImpl(String text) {
        List<TextParticle.Letter> letters = new ArrayList<>();
        char letterChar;
        Color currentColor = Color.BLACK;

        for (int i = 0; i < text.length(); i++) {
            letterChar = text.charAt(i);
            if(DEBUG_MODE) Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleDebug&8] &7Letter Start: " + letterChar));
            if (letterChar == ' ') letters.add(new TextParticle.Letter(' ', null));
            else if (letterChar == '&') {
                if (i + 2 < text.length()) {
                    char colorChar = text.charAt(i + 1);
                    char l = text.charAt(i + 2);
                    Color color = ColorValue.fromChar(colorChar);
                    if (color != null) {
                        i += 2;
                        currentColor = color;
                    }
                    if(DEBUG_MODE) {
                        Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleDebug&8] &7Color: " + colorChar));
                        Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleDebug&8] &7Letter Sequence: " + l));
                    }
                    letters.add(new TextParticle.Letter(l, currentColor));
                }
            } else if (letterChar != '#') letters.add(new TextParticle.Letter(letterChar, currentColor));
            else if (i + 7 < text.length()) {
                String hexString = text.substring(i + 1, i + 7);
                Color color = ColorValue.fromHex(hexString);
                List<TextParticle.Letter> colorLetters = new ArrayList<>();

                if(DEBUG_MODE){
                    Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleDebug&8] &7Hex String: " + hexString));
                    Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleDebug&8] &7Color: " + color));
                }

                for (int j = i + 7; j < text.length(); j++) {
                    char c = text.charAt(j);
                    if (c == '#' || c == '&') break;
                    colorLetters.add(new TextParticle.Letter(c, color));
                }
                letters.addAll(colorLetters);
                i += (6 + colorLetters.size());
            }
        }
        return letters;
    }
    public static String fromHexString(String hex) {
        String regex = "(#[a-fA-F0-9]{6})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(hex);

        while (matcher.find()) {
            String group = matcher.group();
            hex = hex.replace(group, hexToMinecraftColor(group));
        }
        hex = hex.replace("#", "").replace("&", "§");
        return hex;
    }

    /**
     * @param group the group to convert
     * @return the converted group
     * @apiNote The group must be a valid hex color
     */
    private static CharSequence hexToMinecraftColor(String group) {
        StringBuilder builder = new StringBuilder();
        builder.append("§x");
        for (int i = 1; i < group.length(); i++) {
            char c = group.charAt(i);
            builder.append("§").append(c);
        }
        return builder.toString();
    }

    /**
     * @param pattern the pattern to check
     * @return true if the pattern is valid, false if not
     * @apiNote The pattern must be a 7x5 matrix and the value of each cell must be 0 or 1
     */
    public static boolean isPatternValid(byte[][] pattern) {
        if (pattern.length != 7) return false;
        for (byte[] bytes : pattern) {
            if (bytes.length != 5) return false;
            for (byte aByte : bytes) {
                if (aByte != 0 && aByte != 1) return false;
            }
        }
        return true;
    }

    /**
     * @param pattern the pattern to check
     * @return true if the pattern is valid, false if not
     * @apiNote The matrix of this pattern doesn't matter the size, but the value of each cell must be 0 or 1
     */
    public static boolean isCustomPatternValid(byte[][] pattern) {
        for (byte[] bytes : pattern) {
            for (byte aByte : bytes) {
                if (aByte != 0 && aByte != 1) return false;
            }
        }
        return true;
    }

    public static Map<String, byte[][]> loadCustomPatterns(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Map<String, byte[][]> patterns = new HashMap<>();

        for (String key : config.getKeys(false)) {
            List<String> stringList = config.getStringList(key);
            byte[][] pattern = new byte[stringList.size()][];
            String error = "-1";

            for (int i = 0; i < stringList.size(); i++) {
                String string = stringList.get(i).replaceAll("[\\[\\] ]", "");
                String[] split = string.split(",");
                byte[] bytes = new byte[split.length];

                for (int j = 0; j < split.length; j++) {
                    String s = split[j];

                    try {
                        byte b = Byte.parseByte(s);

                        if (b != 0 && b != 1) {
                            error = (i + 1) + "," + (j + 1);
                            break;
                        }

                        bytes[j] = b;
                    } catch (NumberFormatException e) {
                        error = (i + 1) + "," + (j + 1);
                        break;
                    }
                }

                pattern[i] = bytes;
            }

            if (error.equals("-1")) {
                patterns.put(key, invertLetter(pattern));
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleLetters&8] The pattern &c" + key + " &7is invalid. " + "&7The error " +
                        "is in the row &c[" + error.split(",")[0] + "] &7and column &c[" + error.split(",")[1] + "]&7."));
            }
        }

        return patterns;
    }
}
