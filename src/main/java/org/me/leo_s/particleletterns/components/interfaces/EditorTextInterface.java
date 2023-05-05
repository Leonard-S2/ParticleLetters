package org.me.leo_s.particleletterns.components.interfaces;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.me.leo_s.particleletterns.components.builders.ItemBuilder;
import org.me.leo_s.particleletterns.components.builders.maths.MathsUtils;
import org.me.leo_s.particleletterns.components.text.TextSession;

public record EditorTextInterface(String title) {

    public EditorTextInterface(@NotNull String title) {
        this.title = title;
    }

    public void open(Player player, TextSession session) {
        int size = 54;
        Inventory inventory = Bukkit.createInventory(null, size, LegacyComponentSerializer.legacyAmpersand().deserialize(title()));

        ItemStack item = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName("&7").build();
        for (int i = 0; i < 54; i++) {
            if (i < 9 || i > 44 || i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, item);
            }
        }

        item = new ItemBuilder(Material.BARRIER).displayName("&cClose").build();
        inventory.setItem(49, item);

        item = new ItemBuilder(Material.PAPER).displayName("&7Text Name").lore(MathsUtils.fromHexString(session.getText())).build();
        inventory.setItem(11, item);

        item = new ItemBuilder(Material.BOOK).displayName("&8Text Duration").lore("&a" + session.getTimePerLetter() + "s").build();
        inventory.setItem(12, item);

        item = new ItemBuilder(Material.BOOK).displayName("&8Text Size Lines").lore("&a" + session.getLengthLines()).build();
        inventory.setItem(13, item);

        item = new ItemBuilder(Material.BOOK).displayName("&8Text Space Between Lines").lore("&a" + session.getSpaceLetters()).build();
        inventory.setItem(14, item);

        item = new ItemBuilder(Material.ARMOR_STAND).displayName("&8Text Preview").lore("&7Click to preview the text.").build();
        inventory.setItem(30, item);

        item = new ItemBuilder(Material.BOOK).displayName("&8Save Text").lore("&7Click to save the text.").build();
        inventory.setItem(31, item);

        player.openInventory(inventory);
    }
}
