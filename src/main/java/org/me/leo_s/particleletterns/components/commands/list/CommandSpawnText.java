package org.me.leo_s.particleletterns.components.commands.list;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.me.leo_s.particleletterns.components.commands.AbstractCommand;
import org.me.leo_s.particleletterns.components.text.TextParticle;

import java.util.List;

import static org.me.leo_s.particleletterns.components.FileOutput.INVALID_TEXT;
import static org.me.leo_s.particleletterns.components.FileOutput.SPAWNED_TEXT;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class CommandSpawnText extends AbstractCommand {
    public CommandSpawnText() {
        super("spawn", "particleletterns.spawn", -1);
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 0) return;
        int y = -1;
        TextParticle text = getPlugin().getTextParticle(args[0]);
        try {
            if (text == null) {
                y = Integer.parseInt(args[0]);
                text = getPlugin().getTextParticle(args[1]);
                if (text != null) {
                    text.generate(player.getLocation().add(0, y, 0));
                    player.sendMessage(Component.text(color(SPAWNED_TEXT.replace("%text%", args[1]))));
                } else {
                    player.sendMessage(Component.text(color(INVALID_TEXT.replace("%text%", args[1]))));
                }
            } else {
                text.generate(player.getLocation().add(0, y, 0));
                player.sendMessage(Component.text(color(SPAWNED_TEXT.replace("%text%", args[0]))));
            }
        }catch (Exception e){
            player.sendMessage(Component.text(color(INVALID_TEXT.replace("%text%", args[0]))));
        }
    }

    @Override
    public List<String> onTabComplete(Player p, String[] newArgs) {
        return getPlugin().getTextParticles().keySet().stream().toList();
    }
}
