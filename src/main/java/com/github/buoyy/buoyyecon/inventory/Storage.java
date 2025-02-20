package com.github.buoyy.buoyyecon.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@SuppressWarnings({"deprecation", "unused"})
public class Storage {
    private final Inventory inv;
    public Storage(OfflinePlayer player) {
        inv = Bukkit.createInventory((InventoryHolder)player, 54, player.getName()+"'s Vault");
    }
    public Inventory getPlayerInv() {
        return Objects.requireNonNull(inv.getHolder()).getInventory();
    }
    public boolean hasInStorage(int amount) {
        return inv.contains(Material.DIAMOND,amount);
    }
    public boolean hasInInv(int amount) {
        return Objects.requireNonNull(inv.getHolder()).getInventory().contains(Material.DIAMOND, amount);
    }
    public boolean isEmpty() {
        return inv.isEmpty();
    }
    public void addToStorage(int amount) {
        if (!inv.isEmpty()) return; 
        inv.addItem(new ItemStack(Material.DIAMOND, amount));
    }
    public void removeFromStorage(int amount) {
        if (!inv.contains(Material.DIAMOND, amount)) return;
        inv.removeItem(new ItemStack(Material.DIAMOND, amount));
    }
    public void addToPlayer(int amount) {
        if (!getPlayerInv().isEmpty()) return;
        getPlayerInv().addItem(new ItemStack(Material.DIAMOND, amount));
    }
    public void removeFromPlayer(int amount) {
        if (!getPlayerInv().contains(Material.DIAMOND, amount)) return;
        getPlayerInv().removeItem(new ItemStack(Material.DIAMOND, amount));
    }
}
