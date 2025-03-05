package com.github.buoyy.buoyyecon;

import com.github.buoyy.api.command.BaseCommand;
import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.Economy;
import com.github.buoyy.api.file.YAML;
import com.github.buoyy.api.gui.GUIManager;
import com.github.buoyy.api.util.Messenger;

import com.github.buoyy.buoyyecon.commands.transaction.DepositCommand;
import com.github.buoyy.buoyyecon.commands.transaction.PayCommand;
import com.github.buoyy.buoyyecon.commands.transaction.RequestCommand;
import com.github.buoyy.buoyyecon.commands.transaction.WithdrawCommand;
import com.github.buoyy.buoyyecon.commands.util.*;

import com.github.buoyy.buoyyecon.gui.MainMenuGUI;

import com.github.buoyy.buoyyecon.input.ChatInputManager;
import com.github.buoyy.buoyyecon.event.ChatInputCaller;
import com.github.buoyy.buoyyecon.listeners.ChatInputListener;
import com.github.buoyy.buoyyecon.listeners.PlayerListener;
import com.github.buoyy.buoyyecon.listeners.GUIListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public final class BuoyyEcon extends JavaPlugin {

    private static BuoyyEcon instance;
    private static Messenger messenger;
    private static YAML dataFile;
    private static Economy econ;
    private static GUIManager GUIManager;
    private static ChatInputManager chatInputManager;

    // Enable and load all needed data.
    @Override
    public void onEnable() {
        saveDefaultConfig();
        initiateObjects();
        registerTypedCommand("dia","/dia <withdraw/deposit/set/open/pay>", CurrencyType.DIAMOND);
        registerTypedCommand("gold","/gold <withdraw/deposit/set/open/pay>", CurrencyType.GOLD);
        registerEconCommand();
        registerListeners();
        setupEconomy();
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
        instance = this;
        messenger = new Messenger();
        dataFile = new YAML(this.getName(), "accounts", messenger);
        econ = new Economy(dataFile, messenger);
        GUIManager = new GUIManager();
        chatInputManager = new ChatInputManager();
    }

    private void loadAccounts() {
        for (String i: dataFile.getConfig().getKeys(false)) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(i));
            econ.loadAccount(player);
        }
    }
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatInputCaller(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new ChatInputListener(), this);
    }
    private void registerTypedCommand(String name, String usage, CurrencyType type) {
        BaseCommand command = new BaseCommand(usage,
                player -> player.sendMessage(ChatColor.GREEN+"Usage: "+usage));
        command.registerSubCommand("open", new OpenCommand(type));
        // command.registerSubCommand("set", new SetCommand(type));
        command.registerSubCommand("view", new ViewCommand(type));
        command.registerSubCommand("withdraw", new WithdrawCommand(type));
        command.registerSubCommand("deposit", new DepositCommand(type));
        command.registerSubCommand("pay", new PayCommand(type));
        command.registerSubCommand("request", new RequestCommand(type));
        Objects.requireNonNull(getCommand(name)).setExecutor(command);
        Objects.requireNonNull(getCommand(name)).setTabCompleter(command);
    }
    private void registerEconCommand() {
        BaseCommand econ = new BaseCommand("/econ <view/reload>",
                player -> GUIManager.openGUI(player, new MainMenuGUI(player)));
        econ.registerSubCommand("help", new HelpCommand());
        econ.registerSubCommand("view", new FullViewCommand());
        // econ.registerSubCommand("reload", new ReloadCommand());
        econ.registerSubCommand("requests", new RequestsCommand());
        Objects.requireNonNull(getCommand("econ")).setExecutor(econ);
        Objects.requireNonNull(getCommand("econ")).setTabCompleter(econ);
    }
    private void setupEconomy() {
        getServer().getServicesManager().register(Economy.class,
                econ, this, ServicePriority.Highest);
    }
    public static BuoyyEcon getInstance()    { return instance;      }
    public static Economy getEconomy()       { return econ;          }
    public static GUIManager getGUIManager() { return GUIManager;    }
    public static Messenger getMessenger()   { return messenger;     }
    public static YAML getDataFile()         { return dataFile;      }
    public static ChatInputManager getChatInputManager()
                                             { return chatInputManager; }
}