package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.commands.*;
import com.github.buoyy.buoyyecon.economy.VaultEconomy;
import com.github.buoyy.buoyyecon.files.CustomYAML;
import com.github.buoyy.buoyyecon.listeners.PlayerEventListener;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public final class BuoyyEcon extends JavaPlugin {

    private static VaultEconomy econ;
    private static BuoyyEcon plugin;
    private static CustomYAML dataFile;
    private static final Set<OfflinePlayer> players = new HashSet<>();

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

    @Override
    public void onDisable() {
        getLogger().info("Plugin is shutting down!");
        getServer().getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
        getServer().getServicesManager().unregister(Economy.class, econ);
        saveConfig();
        dataFile.save();
        players.clear();
        
    }

    // Set up the economy from the EconHandler class
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("This is BAD! Vault was not found!");
            return false;
        }
        getServer().getServicesManager().register(Economy.class, econ, this, ServicePriority.Highest);
        return true;
    }

    // This is for initiating all needed objects excluding main command
    private void initiateObjects() {
        plugin = this;
        dataFile = new CustomYAML("accounts");
        econ = new VaultEconomy();
        updatePlayersSet();
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
        main.registerSubCommand("list-players", new ListPlayersCommand());
        Objects.requireNonNull(getCommand("econ")).setExecutor(main);
        Objects.requireNonNull(getCommand("econ")).setTabCompleter(main);
    }

    public static void updatePlayersSet() {
        dataFile.getConfig().getKeys(false)
                .forEach(uuid -> Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
    }

    // You guessed it.
    public static BuoyyEcon getPlugin() { return plugin; }
    public static VaultEconomy getEconomy() { return econ; }
    public static CustomYAML getDataFile() { return dataFile; }
    public static Set<OfflinePlayer> getPlayers() { return players; }
}