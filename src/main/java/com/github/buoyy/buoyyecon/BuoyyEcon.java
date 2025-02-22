package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.commands.*;
import com.github.buoyy.buoyyecon.economy.Economy;

import com.github.buoyy.buoyyecon.files.YAML;

import com.github.buoyy.buoyyecon.gui.GUIManager;
import com.github.buoyy.buoyyecon.listeners.PlayerListener;
import com.github.buoyy.buoyyecon.listeners.GUIListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public final class BuoyyEcon extends JavaPlugin {

    private static BuoyyEcon plugin;
    private static Messenger messenger;
    private static YAML dataFile;
    private static Economy econ;
    private static GUIManager GUIManager;

    // Enable and load all needed data.
    @Override
    public void onEnable() {
        saveDefaultConfig();
        initiateObjects();
        registerCommands();
        registerListeners();
        messenger.consoleGood("Found " + dataFile.getConfig().getKeys(false).size() +
                " players in data file.");
        loadAccounts();
        messenger.consoleGood("Economy has been loaded successfully.");
    }

    // Disable everything and free memory
    @Override
    public void onDisable() {
        messenger.consoleGood("Plugin is shutting down!");
        getServer().getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
        saveConfig();
        dataFile.save();
    }

    // This is for initiating all needed objects excluding main command
    private void initiateObjects() {
        plugin = this;
        messenger = new Messenger();
        dataFile = new YAML(); dataFile.setup("accounts");
        econ = new Economy();
        GUIManager = new GUIManager();
    }

    private void loadAccounts() {
        for (String i: dataFile.getConfig().getKeys(false)) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(i));
            econ.loadAccount(player);
        }
    }
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }
    private void registerCommands() {
        MainCommand main = new MainCommand();
        main.registerSubCommand("open", new OpenCommand());
        main.registerSubCommand("set", new SetCommand());
        main.registerSubCommand("view", new ViewCommand());
        main.registerSubCommand("reload", new ReloadCommand());
        main.registerSubCommand("deposit", new DepositCommand());
        Objects.requireNonNull(getCommand("econ")).setExecutor(main);
        Objects.requireNonNull(getCommand("econ")).setTabCompleter(main);
    }

    // You guessed it.
    public static Economy getEconomy() { return econ; }
    public static GUIManager getGUIManager() { return GUIManager; }
    public static Messenger getMessenger() { return messenger; }
    public static BuoyyEcon getPlugin() { return plugin; }
    public static YAML getDataFile() { return dataFile; }
}