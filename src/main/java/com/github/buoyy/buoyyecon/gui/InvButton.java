package com.github.buoyy.buoyyecon.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Consumer;

public class InvButton {
    private final ItemStack icon;
    private final Consumer<InventoryClickEvent> onClick;

    private InvButton(Builder builder) {
        this.icon = builder.icon;
        this.onClick = builder.onClick;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Consumer<InventoryClickEvent> getOnClick() {
        return onClick;
    }

    public static class Builder {
        private ItemStack icon;
        private Consumer<InventoryClickEvent> onClick;

        public static Builder newBuilder() {
            return new Builder();
        }
        public Builder setIcon(Material icon) {
            this.icon = new ItemStack(icon);
            return this;
        }

        public Builder setName(String name) {
            ItemMeta meta = this.icon.getItemMeta();
            assert meta != null;
            meta.setDisplayName(name);
            this.icon.setItemMeta(meta);
            return this;
        }

        public Builder setLore(String... lore) {
            ItemMeta meta = this.icon.getItemMeta();
            assert meta != null;
            meta.setLore(Arrays.asList(lore));
            this.icon.setItemMeta(meta);
            return this;
        }
        public Builder setOnClick(Consumer<InventoryClickEvent> onClick) {
            this.onClick = onClick;
            return this;
        }
        public InvButton build() {
            return new InvButton(this);
        }
    }
}