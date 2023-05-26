package org.me.leo_s.particleletterns.components.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.me.leo_s.particleletterns.components.commands.list.CommandGenerateText;
import org.me.leo_s.particleletterns.components.commands.list.CommandReload;
import org.me.leo_s.particleletterns.components.commands.list.CommandSpawnCustomText;
import org.me.leo_s.particleletterns.components.commands.list.CommandSpawnText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.me.leo_s.particleletterns.components.FileOutput.*;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class CommandExtend implements CommandExecutor, TabExecutor {
    private final List<AbstractCommand> commands = new ArrayList<>();

    public CommandExtend() {
        commands.add(new CommandGenerateText());
        commands.add(new CommandSpawnText());
        commands.add(new CommandReload());
        commands.add(new CommandSpawnCustomText());
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            if(args.length > 0){
                for(AbstractCommand cmd : commands){
                    if(cmd.getName().equalsIgnoreCase(args[0])){
                        String[] newArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
                        if(!p.hasPermission(cmd.getPermission())){
                            p.sendMessage(Component.text(color(COMMAND_NO_PERMISSION)));
                            return true;
                        }

                        if(cmd.getArgsLength() > 0 && newArgs.length != cmd.getArgsLength()){
                            p.sendMessage(Component.text(color(COMMAND_INVALID_ARGUMENTS)));
                            return true;
                        }

                        cmd.execute(p, newArgs);
                        return true;
                    }
                }
                p.sendMessage(Component.text(color(COMMAND_NO_EXIST)));
            }else{
                p.sendMessage(Component.text(color(COMMAND_0_ARGUMENTS)));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

            List<String> tabComplete = new ArrayList<>();

            if(sender instanceof Player p){
                if(args.length == 1){
                    for(AbstractCommand cmd : commands){
                        if(cmd.getName().startsWith(args[0])){
                            tabComplete.add(cmd.getName());
                        }
                    }
                }else if(args.length > 1){
                    for(AbstractCommand cmd : commands){
                        if(cmd.getName().equalsIgnoreCase(args[0])){
                            String[] newArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
                            if(newArgs.length <= cmd.getArgsLength()){
                                tabComplete = cmd.onTabComplete(p, newArgs);
                            }else if(cmd.getArgsLength() == -1){
                                tabComplete = cmd.onTabComplete(p, newArgs);
                            }
                        }
                    }
                }
            }

            return tabComplete;
    }
}
