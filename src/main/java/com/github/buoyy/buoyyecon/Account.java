package com.github.buoyy.buoyyecon;

import com.github.buoyy.buoyyecon.inventory.Storage;
import org.bukkit.OfflinePlayer;

public class Account {

    private final OfflinePlayer player;
    private final Storage storage;

    public Account(OfflinePlayer player) {
        this.player = player;
        this.storage = new Storage(player);
    }

    public Storage getStorage() { return storage; }
    public OfflinePlayer getPlayer() { return player; }
}
