package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.commands.*;
import com.github.buoyy.buoyyecon.economy.EconomyManager;

import com.github.buoyy.buoyyecon.files.CustomYAML;
import com.github.buoyy.buoyyecon.listeners.PlayerEventListener;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BuoyyEcon extends JavaPlugin {

    private static BuoyyEcon plugin;
    private static CustomYAML dataFile;
    private static final EconomyManager econ = new EconomyManager();

    public static EconomyManager getEconomy() {
        return econ;
    }

    @Override
    public void onEnable() {

        saveDefaultConfig();
        initiateObjects();
        registerCommands();
        registerEventListeners();
        getLogger().info("Economy has been loaded successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is shutting down!");
        getServer().getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
        saveConfig();
        dataFile.save();
    }

    // This is for initiating all needed objects excluding main command
    private void initiateObjects() {
        plugin = this;
        dataFile = new CustomYAML("accounts");
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
    public static CustomYAML getDataFile() { return dataFile; }
}