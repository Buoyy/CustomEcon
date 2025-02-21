package com.github.buoyy.buoyyecon.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
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
        private Material display;
        private String name;
        private String[] lore;
        private Consumer<InventoryClickEvent> onClick;
        public static Builder newBuilder() {
            return new Builder();
        }
        public Builder setDisplay(Material display) {
            this.display = display;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setLore(String... lore) {
            this.lore = lore;
            return this;
        }
        public InvButton build() {
            this.icon = new ItemStack(this.display);
            ItemMeta meta = this.icon.getItemMeta();
            meta.setDisplayName(this.name);
            meta.setLore(Arrays.asList(this.lore));
            this.icon.setItemMeta(meta);
            return new InvButton(this);
        }
    }
}

