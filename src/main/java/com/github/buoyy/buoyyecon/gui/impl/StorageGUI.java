package com.github.buoyy.buoyyecon.gui.impl;

import com.github.buoyy.api.CurrencyType;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StorageGUI implements InventoryHandler {
    private final OfflinePlayer holder;
    private final Inventory inv;
    private final CurrencyType type;
    public StorageGUI(OfflinePlayer holder, CurrencyType type) {
        this.inv = Bukkit.createInventory(null,
                54, ChatColor.DARK_AQUA+"Your "+type.getNamePlural()+" storage");
        this.holder = holder;
        this.type = type;
    }
    public void populate() {
        int amount = BuoyyEcon.getEconomy().getBalance(holder, type);
        if (amount == 0) return;
        ItemStack toAdd = new ItemStack(type.getMaterial(), amount);
        this.inv.addItem(toAdd);
    }
    @Override
    public void onClick(InventoryClickEvent e) {
        ItemStack clickedStack = e.getCurrentItem();
        if (clickedStack != null && clickedStack.getType() != type.getMaterial()) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED+"Only "+type.getNamePlural()+" can be stored here.");
        }
    }
    @Override
    public void onOpen(InventoryOpenEvent e) {
        this.populate();
    }
    @Override
    public void onClose(InventoryCloseEvent e) {
        int balance = 0;
        for (ItemStack i : inv.getStorageContents())
            if (i != null && i.getType() == type.getMaterial())
                balance += i.getAmount();
        BuoyyEcon.getEconomy().setBalance(holder, type, balance);
    }
    public Inventory getInv() {
        return inv;
    }
}
