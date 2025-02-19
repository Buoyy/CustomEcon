package com.github.buoyy.buoyyecon.files;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CustomYAML {
    private File file;
    private FileConfiguration config;
    private final Messenger messenger = BuoyyEcon.getMessenger();
    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            messenger.consoleBad(e.getMessage());
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void setup(String name) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("BuoyyEcon")).getDataFolder(), name + ".yml");
        if (!file.exists()) {
            messenger.consoleOK("Data file not found. Creating new file...");
            try {
                if (file.createNewFile())
                    messenger.consoleGood(name+" file was successfully created.");
            } catch (IOException e) {
                messenger.consoleBad(e.getMessage());
            }
        } else {
            messenger.consoleGood(name+" file found! Loading...");
        }
        config = YamlConfiguration.loadConfiguration(file);
        messenger.consoleGood("Loaded data file!");
    }
}
