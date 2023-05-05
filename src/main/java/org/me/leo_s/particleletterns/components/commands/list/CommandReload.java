package org.me.leo_s.particleletterns.components.commands.list;

import org.bukkit.entity.Player;
import org.me.leo_s.particleletterns.components.FileOutput;
import org.me.leo_s.particleletterns.components.commands.AbstractCommand;

import java.io.File;
import java.util.List;

import static org.me.leo_s.particleletterns.components.FileOutput.loadFileOutput;

public class CommandReload extends AbstractCommand {
    public CommandReload() {
        super("reload", "particleletterns.reload", 0);
    }

    @Override
    public void execute(Player player, String[] args) {
        try {
            File file = new File(getPlugin().getDataFolder(), "messages.yml");
            FileOutput.CONFIG.load(file);
            FileOutput.CONFIG.save(file);
            loadFileOutput(false);
            player.sendMessage("§8[§cParticleLetters§8] §aConfig reloaded!");
        } catch (Exception e) {
            player.sendMessage("§8[§cParticleLetters§8] §cError while reloading config!");
        }
    }

    @Override
    public List<String> onTabComplete(Player p, String[] newArgs) {
        return null;
    }
}
