package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class CurrencyMenuGUI extends InventoryGUI {
    private final CurrencyType type;
    public CurrencyMenuGUI(CurrencyType type) {
        this.type = type;
        this.inv = this.createInv();
    }
    @Override
    public Inventory createInv() {
        return Bukkit.createInventory(null, 9, ChatColor.GREEN+type.getNamePlural()+" Menu");
    }
    @Override
    public void decorate() {
        InvButton openStorage = InvButton.Builder.newBuilder()
                .setIcon(type == CurrencyType.DIAMOND ? Material.DIAMOND_BLOCK : Material.GOLD_BLOCK)
                .setName("Open "+type.getNamePlural()+" storage")
                .setOnClick(e->
                        ((Player)e.getWhoClicked()).performCommand(type.getNameSingular()+" open"))
                .build();
        InvButton yourHead = InvButton.Builder.newBuilder()
                .setIcon(getPlayerHead(Bukkit.getOfflinePlayer(UUID.fromString("381b20c8-3679-468c-890d-093b8c2ad6e7"))))
                .build();
        this.addButton(5, openStorage);
        this.addButton(3, yourHead);
        super.decorate();
    }
    ItemStack getPlayerHead(OfflinePlayer player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null)
            meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        return head;
    }
}
