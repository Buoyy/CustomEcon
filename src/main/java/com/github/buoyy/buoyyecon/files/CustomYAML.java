package com.github.buoyy.buoyyecon.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomYAML {
    private File file;
    private FileConfiguration config;

    public CustomYAML(String filename) {
        setup(filename);
    }
    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void setup(String name) {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("BuoyyEcon").getDataFolder(), name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.getMessage();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
}
