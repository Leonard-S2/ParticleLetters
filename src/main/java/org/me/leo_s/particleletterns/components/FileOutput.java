package org.me.leo_s.particleletterns.components;

import org.bukkit.configuration.file.YamlConfiguration;
import org.me.leo_s.particleletterns.ParticleLetters;

import java.io.File;

public class FileOutput {
    public static YamlConfiguration CONFIG;
    public static String PREFIX;
    public static String INVALID_TEXT;
    public static String SPAWNED_TEXT;
    public static String COMMAND_NO_PERMISSION;
    public static String COMMAND_INVALID_ARGUMENTS;
    public static String COMMAND_NO_EXIST;
    public static String COMMAND_0_ARGUMENTS;
    public static String EDITOR_CANCEL;
    public static String EDITOR_TEXT;
    public static String EDITOR_DURATION;
    public static String EDITOR_COLOR;
    public static String EDITOR_LINES;
    public static String EDITOR_SPACING;
    public static String EDITOR_SEND_TEXT;
    public static String EDITOR_SEND_DURATION;
    public static String EDITOR_SEND_COLOR;
    public static String EDITOR_SEND_COLORS;
    public static String EDITOR_SEND_LINES;
    public static String EDITOR_SEND_SPACING;
    public static String PREVIEW_CANCEL;
    public static String PREVIEW_CANCELLED;
    public static String PREVIEW_NO_COMPLETE_ALL_VALUES;
    public static String TEXT_GENERATED;
    public static boolean DEBUG_MODE;
    public static void loadFileOutput(boolean init) {
        File file = new File(ParticleLetters.getInstance().getDataFolder(), "messages.yml");
        if (!file.exists()) {
            ParticleLetters.getInstance().saveResource("messages.yml", false);
        }
        if(init) CONFIG = YamlConfiguration.loadConfiguration(file);

        PREFIX = CONFIG.getString("prefix");
        INVALID_TEXT = PREFIX + CONFIG.getString("invalid-text");
        SPAWNED_TEXT = PREFIX + CONFIG.getString("spawned-text");
        COMMAND_NO_PERMISSION = PREFIX + CONFIG.getString("command-no-permission");
        COMMAND_INVALID_ARGUMENTS = PREFIX + CONFIG.getString("command-invalid-arguments");
        COMMAND_NO_EXIST = PREFIX + CONFIG.getString("command-no-exist");
        COMMAND_0_ARGUMENTS = PREFIX + CONFIG.getString("command-0-arguments");
        EDITOR_CANCEL = PREFIX + CONFIG.getString("editor.cancel");
        EDITOR_TEXT = PREFIX + CONFIG.getString("editor.text");
        EDITOR_DURATION = PREFIX + CONFIG.getString("editor.duration");
        EDITOR_COLOR = PREFIX + CONFIG.getString("editor.color");
        EDITOR_LINES = PREFIX + CONFIG.getString("editor.lines");
        EDITOR_SPACING = PREFIX + CONFIG.getString("editor.spacing");
        EDITOR_SEND_TEXT = PREFIX + CONFIG.getString("editor.send-text");
        EDITOR_SEND_DURATION = PREFIX + CONFIG.getString("editor.send-duration");
        EDITOR_SEND_COLOR = PREFIX + CONFIG.getString("editor.send-color");
        EDITOR_SEND_COLORS = PREFIX + CONFIG.getString("editor.send-colors");
        EDITOR_SEND_LINES = PREFIX + CONFIG.getString("editor.send-lines");
        EDITOR_SEND_SPACING = PREFIX + CONFIG.getString("editor.send-spacing");
        PREVIEW_CANCEL = PREFIX + CONFIG.getString("editor.cancel-preview");
        PREVIEW_CANCELLED = PREFIX + CONFIG.getString("editor.cancelled-preview");
        PREVIEW_NO_COMPLETE_ALL_VALUES = PREFIX + CONFIG.getString("editor.no-complete-all-values");
        TEXT_GENERATED = PREFIX + CONFIG.getString("editor.text-generated");
        DEBUG_MODE = CONFIG.getBoolean("config.debug-mode");
    }
}
