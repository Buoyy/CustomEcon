package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final Economy econ = BuoyyEcon.getEconomy();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (econ.hasAccount(e.getPlayer())) return;
        econ.loadAccount(e.getPlayer());
    }
}