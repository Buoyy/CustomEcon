package com.github.buoyy.buoyyecon.events;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventCaller implements Listener {
    private final Economy economy = BuoyyEcon.getEconomy();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPlayedBefore() || economy.hasAccount(player)) return;
        final AccountCreationEvent creation = new AccountCreationEvent(player);
        Bukkit.getPluginManager().callEvent(creation);
    }

    @EventHandler
    public void onInventory(InventoryDragEvent e) {

    }
}
