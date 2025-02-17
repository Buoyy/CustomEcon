package com.github.buoyy.buoyyecon.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.buoyy.buoyyecon.BuoyyEcon;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        BuoyyEcon.getEconomy().createPlayerAccount(event.getPlayer());
    }
}
