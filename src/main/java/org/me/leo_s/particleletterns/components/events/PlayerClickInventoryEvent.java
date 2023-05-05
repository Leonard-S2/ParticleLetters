package org.me.leo_s.particleletterns.components.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.me.leo_s.particleletterns.ParticleLetters;
import org.me.leo_s.particleletterns.components.builders.maths.MathsUtils;
import org.me.leo_s.particleletterns.components.text.TextParticle;
import org.me.leo_s.particleletterns.components.text.TextSession;

import static org.me.leo_s.particleletterns.components.FileOutput.*;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;

public class PlayerClickInventoryEvent implements Listener {
    public final ParticleLetters plugin = ParticleLetters.getInstance();
    @EventHandler
    public void onPlayerClickInventoryEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        String title = LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title());
        TextSession session = plugin.getTextSession(player);

        if (title.equals("§8[§6ParticleLetters§8] §7Editor")){
            event.setCancelled(true);
            switch (slot) {
                case 49 -> player.closeInventory();
                case 11 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color(EDITOR_SEND_TEXT)));
                    plugin.setEditing(player, "text");
                }
                case 12 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color(EDITOR_SEND_DURATION)));
                    plugin.setEditing(player, "duration");
                }
                case 13 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color(EDITOR_SEND_LINES)));
                    plugin.setEditing(player, "lines");
                }
                case 14 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color(EDITOR_SEND_SPACING)));
                    plugin.setEditing(player, "spacing");
                }
                case 30 -> {
                    plugin.setEditing(player, "preview");

                    if(session.allValuesCompleted()) {
                        player.closeInventory();
                        TextParticle preview = new TextParticle(session.getText(), -1, session.getLengthLines(), session.getSpaceLetters());
                        session.setPreview(preview);
                        session.getPreview().generate(player.getLocation().add(0, 6, 0));
                        player.sendMessage(Component.text(color(PREVIEW_CANCEL)));
                    } else {
                        player.sendMessage(Component.text(color(PREVIEW_NO_COMPLETE_ALL_VALUES)));
                    }
                }
                case 31 -> {
                    try {
                        session.ready();
                        player.closeInventory();
                        player.sendMessage(Component.text(color(TEXT_GENERATED)));
                        player.sendMessage(Component.text(color("&8[&6ParticleLetters&8] &7Show your files in &fplugins/ParticleLetters/texts/" + MathsUtils.clearVanillaText(session.getText()) + ".json")));
                        plugin.removeTextSession(player);
                        plugin.removeEditing(player);
                    } catch (Exception e) {
                        player.sendMessage(Component.text(color(e.getMessage())));
                    }
                }
            }
        }
    }
}
