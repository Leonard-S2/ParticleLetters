package org.me.leo_s.particleletterns.components.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.me.leo_s.particleletterns.ParticleLetters;

public class PlayerDisconnectEvent implements Listener{
    private final ParticleLetters plugin = ParticleLetters.getInstance();
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(plugin.isEditing(player)) {
            plugin.removeEditing(player);
            plugin.removeTextSession(player);
        }
    }
}
