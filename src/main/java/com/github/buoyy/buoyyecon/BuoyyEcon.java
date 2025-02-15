package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.economy.EconHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class BuoyyEcon extends JavaPlugin {

    private static EconHandler handler;
    private static BuoyyEcon plugin;

    @Override
    public void onEnable() {
        initiateObjects();

        // Don't move forward in case Vault isn't installed.
        if (!setupEconomy()) {
            getLogger().severe("Vault economy system not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("Vault economy hooked successfully!");
    }

    // Set up the economy from the EconHandler class
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("This is BAD! Vault was not found!");
            return false;
        }
        getServer().getServicesManager().register(Economy.class, handler, this, ServicePriority.Highest);
        return true;
    }

    // This is for initiating all needed objects excluding main command
    private void initiateObjects() {
        plugin = this;
        handler = new EconHandler();
    }

    // You guessed it.
    public static BuoyyEcon getPlugin() {
        return plugin;
    }
    public static Economy getEconomy() {
        return handler;
    }
}
