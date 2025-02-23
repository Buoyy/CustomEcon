package com.github.buoyy.buoyyecon.gui.impl;

import com.github.buoyy.buoyyecon.gui.InvButton;
import com.github.buoyy.buoyyecon.gui.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EconOpenerGUI extends InventoryGUI {
    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, 9, ChatColor.GOLD+"Economy");
    }

    @Override
    public void decorate() {
        InvButton goldEcon = InvButton.Builder.newBuilder()
                .setIcon(Material.GOLD_BLOCK)
                .setName("Open Gold storage")
                .setOnClick(e->
                        ((Player)e.getWhoClicked()).performCommand("gold open"))
                .build();
        InvButton diaEcon = InvButton.Builder.newBuilder()
                .setIcon(Material.DIAMOND_BLOCK)
                .setName("Open Diamond storage")
                .setOnClick(e->
                        ((Player)e.getWhoClicked()).performCommand("dia open"))
                .build();
        this.addButton(3, goldEcon);
        this.addButton(5, diaEcon);
        super.decorate();
    }
}
