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
import org.me.leo_s.particleletterns.components.commands.list.CommandSpawnText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandExtend implements CommandExecutor, TabExecutor {
    private final List<AbstractCommand> commands = new ArrayList<>();

    public CommandExtend() {
        commands.add(new CommandGenerateText());
        commands.add(new CommandSpawnText());
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p){
            if(args.length > 0){
                for(AbstractCommand cmd : commands){
                    if(cmd.getName().equalsIgnoreCase(args[0])){
                        String[] newArgs = Arrays.stream(args).skip(1).toArray(String[]::new);
                        if(!p.hasPermission(cmd.getPermission())){
                            p.sendMessage(Component.text("§8[§cParticleLetters§8] §cYou don't have permission to do this."));
                            return true;
                        }

                        if(cmd.getArgsLength() > 0 && newArgs.length != cmd.getArgsLength()){
                            p.sendMessage(Component.text("§8[§cParticleLetters§8] §cInvalid arguments."));
                            return true;
                        }

                        cmd.execute(p, newArgs);
                        return true;
                    }
                }
                p.sendMessage(Component.text("§8[§cParticleLetters§8] §cThis command doesn't exist."));
            }else{
                p.sendMessage(Component.text("§8[§cParticleLetters§8] §cUse /particleletters <command>"));
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
