package org.me.leo_s.particleletterns.components.events;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.me.leo_s.particleletterns.ParticleLetters;
import org.me.leo_s.particleletterns.components.text.TextSession;

import static org.me.leo_s.particleletterns.components.FileOutput.PREVIEW_CANCELLED;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class PlayerPreviewEvent implements Listener {

    private final ParticleLetters plugin = ParticleLetters.getInstance();

    @EventHandler
    public void onPlayerPunch(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        boolean isEdit = plugin.isEditing(player);
        if(isEdit) {
            String editing = plugin.getEditing(player);
            if(editing.equals("preview")){
                if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
                    TextSession textSession = plugin.getTextSession(player);

                    plugin.removeEditing(player);
                    player.sendMessage(Component.text(color(PREVIEW_CANCELLED)));
                    textSession.stopPreview();
                    plugin.getEditorTextInterface().open(player, textSession);
                }
            }
        }
    }
}
