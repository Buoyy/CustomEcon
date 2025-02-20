package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class PlayerListener implements Listener {
    private final Economy econ = BuoyyEcon.getEconomy();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (econ.hasAccount(e.getPlayer())) return;
        econ.loadAccount(e.getPlayer());
    }
    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (!e.getView().getTitle().equals(ChatColor.AQUA+"Storage")) return;
        econ.setBalance((OfflinePlayer)e.getPlayer(), getAmount(e.getInventory()));
    }
    @EventHandler
    public void onInv(InventoryDragEvent e) {
        if (!e.getView().getTitle().equals(ChatColor.AQUA+"Storage")) return;
        if (!e.getOldCursor().getType().equals(Material.DIAMOND))
            e.setCancelled(true);
    }
    private int getAmount(Inventory inv) {
        int amount = 0;
        for (ItemStack i: inv.getStorageContents()) {
            if (i == null) continue;
            if (i.getType().equals(Material.DIAMOND)) {
                amount += i.getAmount();
            }
        }
        return amount;
    }
}
