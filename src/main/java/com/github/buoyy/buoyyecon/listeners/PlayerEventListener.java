package com.github.buoyy.buoyyecon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.buoyy.buoyyecon.BuoyyEcon;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) BuoyyEcon.getEconomy().createPlayerAccount(event.getPlayer());
    }
}
