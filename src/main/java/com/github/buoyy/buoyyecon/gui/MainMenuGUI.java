package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.api.gui.InventoryGUI;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenuGUI extends InventoryGUI {
    public MainMenuGUI() {
        this.inv = this.createInv();
    }
    @Override
    public Inventory createInv() {
        return Bukkit.createInventory(null, 9, ChatColor.GOLD+"Economy");
    }

    @Override
    public void decorate() {
        InvButton goldEcon = InvButton.Builder.newBuilder()
                .setIcon(Material.GOLD_BLOCK)
                .setName("Open Gold Menu")
                .setOnClick(e->
                        BuoyyEcon.getGUIManager()
                                .openGUI((Player)e.getWhoClicked(), new CurrencyMenuGUI(CurrencyType.GOLD)))
                .build();
        InvButton diaEcon = InvButton.Builder.newBuilder()
                .setIcon(Material.DIAMOND_BLOCK)
                .setName("Open Diamond Menu")
                .setOnClick(e->
                        BuoyyEcon.getGUIManager()
                                .openGUI((Player)e.getWhoClicked(), new CurrencyMenuGUI(CurrencyType.DIAMOND)))
                .build();
        this.addButton(3, goldEcon);
        this.addButton(5, diaEcon);
        super.decorate();
    }
}
