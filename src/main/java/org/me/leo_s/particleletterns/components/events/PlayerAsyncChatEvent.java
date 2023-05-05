package org.me.leo_s.particleletterns.components.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.me.leo_s.particleletterns.ParticleLetters;

import static org.me.leo_s.particleletterns.components.FileOutput.*;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class PlayerAsyncChatEvent implements Listener{

    public final ParticleLetters plugin = ParticleLetters.getInstance();

    @EventHandler
    public void onPlayerAsyncChatEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();
        String message = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());

        boolean isEdit = plugin.isEditing(player);
        if(isEdit) {
            String editing = plugin.getEditing(player);
            if (message.equals("cancel")) {
                event.setCancelled(true);
                plugin.removeTextSession(player);
                player.sendMessage(Component.text(color(EDITOR_CANCEL)));
            } else {
                event.setCancelled(true);
                try {
                    switch (editing) {
                        case "text" -> {
                            plugin.addTextSession(player, message);
                            player.sendMessage(Component.text(color(EDITOR_TEXT)));
                        }
                        case "duration" -> {
                            plugin.getTextSession(player).setTimePerLetter(Integer.parseInt(message));
                            player.sendMessage(Component.text(color(EDITOR_DURATION)));
                        }
                        case "lines" -> {
                            plugin.getTextSession(player).setLengthLines(Double.parseDouble(message));
                            player.sendMessage(Component.text(color(EDITOR_LINES)));
                        }
                        case "spacing" -> {
                            plugin.getTextSession(player).setSpaceLetters(Double.parseDouble(message));
                            player.sendMessage(Component.text(color(EDITOR_SPACING)));
                        }
                    }
                    Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.getEditorTextInterface().open(player, plugin.getTextSession(player)), 1);
                } catch (Exception e) {
                    player.sendMessage(Component.text("§8[§cParticleLetters§8] §c" + e.getMessage()));
                }
            }
            plugin.removeEditing(player);
        }
    }
}
