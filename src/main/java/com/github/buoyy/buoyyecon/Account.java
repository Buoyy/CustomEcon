package com.github.buoyy.buoyyecon;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class Account {
    private final Inventory storage;
    private final OfflinePlayer player;
    public Account(OfflinePlayer player) {
        this.storage = Bukkit.createInventory(null, 9, player.getName());
        this.player = player;
    }
    public void deposit(int amount) {
        ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
        Player target = (Player) player;
        if (!target.getInventory().contains(diamonds)) {
            target.sendMessage("You don't have that many diamonds");
            return;
        }
        if (!storage.isEmpty()) {
            target.sendMessage("Storage is full.");
        }
        target.getInventory().removeItem(diamonds);
        storage.addItem(diamonds);
    }
    public void withdraw(int amount) {
        ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
        Player target = (Player) player;
        if (!storage.contains(diamonds)) {
            target.sendMessage("You don't have that many diamonds");
            return;
        }
        if (!target.getInventory().isEmpty()) {
            target.sendMessage("Inventory is full.");
            return;
        }
        storage.removeItem(diamonds);
        target.getInventory().addItem(diamonds);
    }
}
