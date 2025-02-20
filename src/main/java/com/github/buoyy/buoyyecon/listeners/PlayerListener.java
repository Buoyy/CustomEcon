package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (BuoyyEcon.getEconomy().hasAccount(e.getPlayer())) return;
        BuoyyEcon.getEconomy().loadAccount(e.getPlayer());
    }
}
