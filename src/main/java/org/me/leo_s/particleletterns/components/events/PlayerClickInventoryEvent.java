package org.me.leo_s.particleletterns.components.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.me.leo_s.particleletterns.ParticleLetters;
import org.me.leo_s.particleletterns.components.exceptions.TextFormattedInvalid;
import org.me.leo_s.particleletterns.components.text.TextParticle;
import org.me.leo_s.particleletterns.components.text.TextSession;

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
                    player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Please type the new text in chat.")));
                    plugin.setEditing(player, "text");
                }
                case 12 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Please type the new duration in chat.")));
                    plugin.setEditing(player, "duration");
                }
                case 13 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Please type the new color in chat.")));
                    player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Colors: &fRED, GREEN, BLUE, YELLOW, PURPLE, CYAN, WHITE, BLACK, ORANGE, PINK, LIME, LIGHT_BLUE, MAGENTA, LIGHT_GRAY, GRAY or BROWN&7.")));
                    plugin.setEditing(player, "color");
                }
                case 14 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Please type the new lines in chat.")));
                    plugin.setEditing(player, "lines");
                }
                case 15 -> {
                    player.closeInventory();
                    player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Please type the new spacing in chat.")));
                    plugin.setEditing(player, "spacing");
                }
                case 30 -> {
                    plugin.setEditing(player, "preview");
                    boolean containsCharacterSpecial = session.getText().contains("&");

                    if(session.allValuesCompleted(containsCharacterSpecial)) {
                        player.closeInventory();
                        String text = session.getText().toUpperCase();
                        TextParticle preview;

                        try {
                            if (!containsCharacterSpecial) preview = new TextParticle(text, -1, session.getColor(), session.getLengthLines(), session.getSpaceLetters());
                            else preview = new TextParticle(text, -1, session.getLengthLines(), session.getSpaceLetters());

                            session.setPreview(preview);
                            session.getPreview().generate(player.getLocation().add(0, 6, 0));
                            player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &aHit to cancel preview.")));
                        } catch (TextFormattedInvalid e) {
                            player.sendMessage(e.getMessage());
                        }
                    } else {
                        player.sendMessage(Component.text(color("&8[&cParticleLetters&8] &7Please complete all the values before previewing.")));
                    }
                }
                case 31 -> {
                    try {
                        session.ready();
                        player.closeInventory();
                        player.sendMessage(Component.text(color("&8[&6ParticleLetters&8] &7You have generated the text.")));
                        player.sendMessage(Component.text(color("&8[&6ParticleLetters&8] &7Show your files in &fplugins/ParticleLetters/texts/" + session.getText().replace(" ", "_") + ".json")));
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
