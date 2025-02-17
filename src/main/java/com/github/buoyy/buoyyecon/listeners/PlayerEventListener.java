package com.github.buoyy.buoyyecon.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

public class PlayerEventListener implements Listener {
    private final EconHandler econ = BuoyyEcon.getEconomy();
    public void onPlayerJoin(PlayerJoinEvent event) {
        econ.createPlayerAccount(event.getPlayer());
    }
}
