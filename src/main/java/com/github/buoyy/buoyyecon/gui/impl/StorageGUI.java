package com.github.buoyy.buoyyecon.gui.impl;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class StorageGUI implements InventoryHandler {
    private final OfflinePlayer holder;
    private final Inventory inv;
    public StorageGUI(OfflinePlayer holder) {
        this.inv = Bukkit.createInventory(null, 54, "Your storage");
        this.holder = holder;
    }
    public void populate() {
        int amount = BuoyyEcon.getEconomy().getBalance(holder);
        if (amount == 0) return;
        ItemStack dia = new ItemStack(Material.DIAMOND, amount);
        this.inv.addItem(dia);
    }
    @Override
    public void onClick(InventoryClickEvent e) {
        ItemStack clickedStack = e.getCurrentItem();
        if (clickedStack != null && clickedStack.getType() != Material.DIAMOND) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED+"Only diamonds can be stored here.");
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
            if (i != null && i.getType() == Material.DIAMOND)
                balance += i.getAmount();
        BuoyyEcon.getEconomy().setBalance(holder, balance);
    }
    public Inventory getInv() {
        return inv;
    }
}
