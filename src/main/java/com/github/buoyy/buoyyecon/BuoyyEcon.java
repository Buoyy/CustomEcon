package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.commands.MainCommand;
import com.github.buoyy.buoyyecon.commands.SetCommand;
import com.github.buoyy.buoyyecon.commands.ViewCommand;
import com.github.buoyy.buoyyecon.economy.EconHandler;
import com.github.buoyy.buoyyecon.listeners.PlayerEventListener;

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
        registerCommands();
        registerEventListeners();
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

    private void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    private void registerCommands() {
        MainCommand main = new MainCommand();
        main.registerSubCommand("set", new SetCommand());
        main.registerSubCommand("view", new ViewCommand());
        getCommand("econ").setExecutor(main);
    }

    // You guessed it.
    public static BuoyyEcon getPlugin() {
        return plugin;
    }
    public static EconHandler getEconomy() {
        return handler;
    }
}
