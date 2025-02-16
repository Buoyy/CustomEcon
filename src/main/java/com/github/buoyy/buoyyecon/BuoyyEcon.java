package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.commands.*;
import com.github.buoyy.buoyyecon.economy.EconHandler;
import com.github.buoyy.buoyyecon.files.CustomYAML;
import com.github.buoyy.buoyyecon.listeners.PlayerEventListener;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BuoyyEcon extends JavaPlugin {

    private static EconHandler handler;
    private static BuoyyEcon plugin;
    private static CustomYAML balances;

    @Override
    public void onEnable() {

        saveDefaultConfig();

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
        balances = new CustomYAML("balances");
    }

    private void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    private void registerCommands() {
        MainCommand main = new MainCommand();
        main.registerSubCommand("set", new SetCommand());
        main.registerSubCommand("view", new ViewCommand());
        main.registerSubCommand("deposit", new DepositCommand());
        main.registerSubCommand("withdraw", new WithdrawCommand());
        Objects.requireNonNull(getCommand("econ")).setExecutor(main);
        Objects.requireNonNull(getCommand("econ")).setTabCompleter(main);
    }

    // You guessed it.
    public static BuoyyEcon getPlugin() { return plugin; }
    public static EconHandler getEconomy() { return handler; }
}
