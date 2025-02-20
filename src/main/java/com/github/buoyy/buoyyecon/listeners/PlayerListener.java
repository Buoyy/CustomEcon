package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
    public void onInvClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(ChatColor.AQUA+"Storage")) return;
        ItemStack item = e.getCursor();
        Player player = (Player) e.getWhoClicked();
        if (e.isShiftClick() || (item != null) && item.getType() != Material.DIAMOND) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED+"Only diamonds can be stored here.");
        }
        if (item != null && !item.getType().isAir()) {
            player.getInventory().addItem(item);
        }
    }
    @EventHandler
    public void onInv(InventoryDragEvent e) {
        if (!e.getView().getTitle().equals(ChatColor.AQUA+"Storage")) return;
        Player player = (Player) e.getWhoClicked();
        for (ItemStack i: e.getNewItems().values()) {
            if (i.getType() != Material.DIAMOND) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED+"Only diamonds can be stored here.");
            }
        }
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
