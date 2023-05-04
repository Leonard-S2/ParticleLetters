package org.me.leo_s.particleletterns.components.commands;

import org.bukkit.entity.Player;
import org.me.leo_s.particleletterns.ParticleLetters;

import java.util.List;

public abstract class AbstractCommand {
    private final String name;
    private final String permission;
    private final int argsLength;
    private final ParticleLetters plugin = ParticleLetters.getInstance();

    public AbstractCommand(String name, String permission, int argsLength) {
        this.name = name;
        this.permission = permission;
        this.argsLength = argsLength;
    }

    public String getName() {
        return name;
    }
    public String getPermission() {
        return permission;
    }
    public ParticleLetters getPlugin() {
        return plugin;
    }
    public int getArgsLength() {
        return argsLength;
    }

    public abstract void execute(Player player, String[] args);

    public abstract List<String> onTabComplete(Player p, String[] newArgs);
}
