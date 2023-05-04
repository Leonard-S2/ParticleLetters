package org.me.leo_s.particleletterns.components.commands.list;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.me.leo_s.particleletterns.components.commands.AbstractCommand;

import java.util.Arrays;
import java.util.List;

import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class CommandSpawnText extends AbstractCommand {
    public CommandSpawnText() {
        super("spawn", "particleletterns.spawn", -1);
    }

    @Override
    public void execute(Player player, String[] args) {
        String text = args.length > 1 ? Arrays.stream(args).reduce((a, b) -> a + " " + b).orElse("") : args[0];
        try {
            Bukkit.getServer().getConsoleSender().sendMessage(color("&8[&cParticleLetters&8] &7Generating text: &c" + text));
            getPlugin().getTextParticle(text).generate(player.getLocation());
        } catch (Exception e) {
            player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Invalid text: &c" + text)));
        }
    }

    @Override
    public List<String> onTabComplete(Player p, String[] newArgs) {
        return getPlugin().getTextParticles().keySet().stream().toList();
    }
}
