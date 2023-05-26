package org.me.leo_s.particleletterns.components.commands.list;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.me.leo_s.particleletterns.components.commands.AbstractCommand;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;

import java.util.List;

import static org.me.leo_s.particleletterns.components.FileOutput.INVALID_TEXT;
import static org.me.leo_s.particleletterns.components.FileOutput.TEXT_GENERATED;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class CommandSpawnCustomText extends AbstractCommand {
    public CommandSpawnCustomText() {
        super("custom", "particleletterns.spawn", -1);
    }

    @Override
    public void execute(Player player, String[] args) {
        int y = 0;
        double length;
        boolean isCustom;
        byte[][] customPattern;

        try {
            if(args.length == 3){
                y = Integer.parseInt(args[0]);
                length = Double.parseDouble(args[2]);
                customPattern = getPlugin().getCustomPattern(args[1]) == null ? getPlugin().invertLetter(args[1].toUpperCase().charAt(0)) : getPlugin().getCustomPattern(args[1]);
                if(customPattern == null) {
                    player.sendMessage(Component.text(color(INVALID_TEXT.replace("%text%", args[1]))));
                    return;
                }
                isCustom = getPlugin().getCustomPattern(args[1]) != null;
                getPlugin().generateLetterOrCustomPattern(player.getLocation().add(0, y, 0), customPattern, length, isCustom);
                player.sendMessage(Component.text(color(TEXT_GENERATED.replace("%text%", args[1]))));
            }

            if(args.length == 2) {
                length = Double.parseDouble(args[1]);
                customPattern = getPlugin().getCustomPattern(args[0]) == null ? getPlugin().invertLetter(args[0].toUpperCase().charAt(0)) : getPlugin().getCustomPattern(args[0]);

                if(customPattern == null) {
                    player.sendMessage(Component.text(color(INVALID_TEXT.replace("%text%", args[0]))));
                    return;
                }
                isCustom = getPlugin().getCustomPattern(args[0]) != null;
                getPlugin().generateLetterOrCustomPattern(player.getLocation().add(0, y, 0), customPattern, length, isCustom);
                player.sendMessage(Component.text(color(TEXT_GENERATED.replace("%text%", args[0]))));
            }
        } catch (Exception e) {
            if(e instanceof TextFormattedInvalid) {
                player.sendMessage(Component.text(color("&8[&cParticleLetter&8] &7" + e.getMessage())));
                return;
            }
            player.sendMessage(Component.text(color(INVALID_TEXT.replace("%text%", args[0]))));
        }
    }

    @Override
    public List<String> onTabComplete(Player p, String[] newArgs) {
        return getPlugin().getCustomPatterns().keySet().stream().toList();
    }
}
