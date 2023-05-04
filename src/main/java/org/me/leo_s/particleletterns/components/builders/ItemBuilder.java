package org.me.leo_s.particleletterns.components.builders;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.color;
import static org.me.leo_s.particleletterns.components.builders.maths.MathsUtils.colorComponent;

@SuppressWarnings({"unused"})
public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
    public ItemBuilder displayName(String displayName) {
        if(displayName == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(color(displayName)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder lore(String... lore) {
        if(lore == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(colorComponent(List.of(lore)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }
    public void flag(ItemFlag... flags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(flags);
    }

    public ItemStack build() {
        return itemStack;
    }
}
