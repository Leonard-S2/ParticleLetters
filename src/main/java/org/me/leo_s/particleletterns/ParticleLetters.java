package org.me.leo_s.particleletterns;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.me.leo_s.particleletterns.components.api.ParticleLettersAPI;
import org.me.leo_s.particleletterns.components.builders.maths.MathsUtils;
import org.me.leo_s.particleletterns.components.commands.CommandExtend;
import org.me.leo_s.particleletterns.components.events.PlayerAsyncChatEvent;
import org.me.leo_s.particleletterns.components.events.PlayerClickInventoryEvent;
import org.me.leo_s.particleletterns.components.events.PlayerPreviewEvent;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;
import org.me.leo_s.particleletterns.components.interfaces.EditorTextInterface;
import org.me.leo_s.particleletterns.components.text.TextParticle;
import org.me.leo_s.particleletterns.components.text.TextSession;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.me.leo_s.particleletterns.components.FileOutput.loadFileOutput;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

@SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
public final class ParticleLetters extends JavaPlugin implements ParticleLettersAPI {

    private static ParticleLetters instance;
    private static final Map<Player, TextSession> textSessions = new HashMap<>();
    private static final Map<Player, String> editing = new HashMap<>();
    private static final Map<String, TextParticle> textParticles = new HashMap<>();
    private static EditorTextInterface editorTextInterface;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        editorTextInterface = new EditorTextInterface(color("&8[&6ParticleLetters&8] &7Editor"));
        Objects.requireNonNull(getCommand("particleletters")).setExecutor(new CommandExtend());
        registerListeners(new PlayerAsyncChatEvent(), new PlayerClickInventoryEvent(),
        new PlayerPreviewEvent());
        loadTexts();
        loadFileOutput(true);
    }

    private void loadTexts() {
        File file = new File(getDataFolder() + File.separator + "texts");
        if(!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if(files == null) return;
        for(File f : files) {
            if(f.getName().endsWith(".json")) {
                String name = f.getName().replace(".json", "");
                try {
                    FileReader reader = new FileReader(f);
                    addTextParticle(name.replace("_", " "), MathsUtils.loadTextFromJson(reader));
                } catch (Exception textFormattedInvalid) {
                    getServer().getConsoleSender().sendMessage("§8[§cParticleLetters§8] §7The text §c" + name + " §7could not be loaded.");
                }
            }
        }
        getServer().getConsoleSender().sendMessage("§8[§cParticleLetters§8] §7Loaded §c" + textParticles.size() + " §7texts.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        textParticles.clear();
        textSessions.clear();
        editing.clear();
    }

    private Map<Player, TextSession> getTextSessions() {
        return textSessions;
    }

    public static ParticleLetters getInstance() {
        return instance;
    }
    public void addTextSession(Player player, String text) throws TextFormattedInvalid {
        if(!MathsUtils.isTextValid(text)){
            throw new TextFormattedInvalid("&7The text can only contain letters from &cA - Z&7, numbers from &c0 - 9&7 and the character &c& or #&7.");
        }
        textSessions.put(player, new TextSession(text.toUpperCase()));
    }
    public void removeTextSession(Player player) {
        textSessions.remove(player);
    }
    public TextSession getTextSession(Player player) {
        return textSessions.get(player);
    }
    public void setEditing(Player player, String editing) {
        ParticleLetters.editing.put(player, editing);
    }
    public boolean isEditing(Player player) {
        return editing.containsKey(player);
    }
    public void removeEditing(Player player) {
        editing.remove(player);
    }
    public String getEditing(Player player) {
        return editing.get(player);
    }
    public EditorTextInterface getEditorTextInterface() {
        return editorTextInterface;
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }
    public Map<String, TextParticle> getTextParticles() {
        return textParticles;
    }

    @Override
    public TextParticle getTextParticle(@NotNull String name) {
        return textParticles.get(name);
    }
    public void addTextParticle(String name, TextParticle textParticle) {
        textParticles.put(name, textParticle);
    }

    @Override
    public void generateTextFromColorString(@NotNull String text, @NotNull Location origen, int timePerLetter, double lengthLines, double spaceLetters) {
        try {
            if (MathsUtils.isTextValid(text)) {
                List<TextParticle.Letter> letters = MathsUtils.getLettersForEachImpl(text);
                TextParticle textParticle = new TextParticle(text, timePerLetter, lengthLines, spaceLetters);
                textParticle.generate(origen);
            }
        } catch (TextFormattedInvalid e){
            getServer().getConsoleSender().sendMessage(color("&8[&cParticleLetters&8] " + e.getMessage()));
        }
    }

    @Override
    public void generateText(@NotNull TextParticle textParticle, @NotNull Location origen) {
        textParticle.generate(origen);
    }

    @Override
    public void forceStopText(@NotNull TextParticle textParticle) {
        textParticle.cancelTask();
    }

    @Override
    public void removeTextParticle(@NotNull String name) {
        textParticles.remove(name);
    }

    @Override
    public byte[][] getLetter(char letter) {
        return TextParticle.getLetter(letter);
    }

    @Override
    public byte[][] invertLetter(char letter) {
        return TextParticle.invertLetter(letter);
    }
}
