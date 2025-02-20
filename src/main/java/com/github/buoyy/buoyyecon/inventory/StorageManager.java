package com.github.buoyy.buoyyecon.inventory;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.files.YAML;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageManager {
    private final YAML dataFile;
    private final Map<UUID, Storage> store;
    public StorageManager() {
        dataFile = BuoyyEcon.getDataFile();
        store = new HashMap<>();
        for (String i : dataFile.getConfig().getKeys(false)) {
            UUID player = UUID.fromString(i);
            store.put(player,
                    new Storage(Bukkit.getOfflinePlayer(player)));
        }
    }
    public boolean hasNoStorage(OfflinePlayer player) {
        return !store.containsKey(player.getUniqueId());
    }
    public void deposit(OfflinePlayer player, int amount) {
        if (hasNoStorage(player)) return;
        Storage current = store.get(player.getUniqueId());
        if (!current.hasInInv(amount) || !current.isEmpty()) return;
        current.removeFromPlayer(amount);
        current.addToStorage(amount);
    }
    public void withdraw(OfflinePlayer player, int amount) {
        if (hasNoStorage(player)) return;
        Storage current = store.get(player.getUniqueId());
        if (!current.hasInStorage(amount) || !current.getPlayerInv().isEmpty()) return;
        current.removeFromStorage(amount);
        current.addToPlayer(amount);
    }
}
