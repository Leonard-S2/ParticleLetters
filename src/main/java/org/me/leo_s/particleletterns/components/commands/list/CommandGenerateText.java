package org.me.leo_s.particleletterns.components.commands.list;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.me.leo_s.particleletterns.components.commands.AbstractCommand;

import java.util.Arrays;
import java.util.List;

import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class CommandGenerateText extends AbstractCommand {
    public CommandGenerateText() {
        super("text", "particleletterns.text", -1);
    }

    @Override
    public void execute(Player player, String[] args) {
        String text = Arrays.stream(args).reduce((a, b) -> a + " " + b).orElse("");
        try {
            getPlugin().addTextSession(player, text);
            getPlugin().getEditorTextInterface().open(player, getPlugin().getTextSession(player));
        } catch (Exception e) {
            player.sendMessage(Component.text(color("&8[&cParticleLetters&8] " + e.getMessage())));
        }
    }

    @Override
    public List<String> onTabComplete(Player p, String[] newArgs) {
        return null;
    }
}
