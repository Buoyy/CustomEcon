package com.github.buoyy.buoyyecon.economy;

import org.bukkit.OfflinePlayer;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.files.CustomYAML;

import net.milkbowl.vault.economy.EconomyResponse;

// This class is really just a wrapper for handling the data file
public class DataStorageManager {

    private final CustomYAML dataFile = BuoyyEcon.getDataFile();
    
    // Check if there is a section with the player's UUID
    public boolean hasAccount(OfflinePlayer player) {
        return dataFile.getConfig().contains(player.getUniqueId().toString());
    }

    // Self-explanatory
    public int getBalance(OfflinePlayer player) {
        createAccount(player);
        return dataFile.getConfig().getInt(player.getUniqueId()+".balance");
    }

    // Set the amount and don't forget to save! This private method is pretty useful.
    private void setBalance(OfflinePlayer player, int amount) {
        dataFile.getConfig().set(player.getUniqueId()+".balance", amount);
        dataFile.save();
    }

    // Amount shouldn't be negative. EVER.
    public EconomyResponse deposit(OfflinePlayer player, int amount) {
        if (amount < 0) 
            return new EconomyResponse(amount, getBalance(player),
                                EconomyResponse.ResponseType.FAILURE, "Negative amount");
        setBalance(player, getBalance(player)+amount);
        return new EconomyResponse(amount, getBalance(player),
                            EconomyResponse.ResponseType.SUCCESS, "");
    }

    // Again, amount shouldn't be negative.
    // Also, can't withdraw if player doesn't have that much. Obviously
    public EconomyResponse withdraw(OfflinePlayer player, int amount) {
        if (amount < 0) 
                    return new EconomyResponse(amount, getBalance(player),
                                        EconomyResponse.ResponseType.FAILURE, "Negative amount");
        if (amount > getBalance(player)) 
                    return new EconomyResponse(amount, getBalance(player),
                                        EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        setBalance(player, getBalance(player)-amount);
        return new EconomyResponse(amount, getBalance(player),
                            EconomyResponse.ResponseType.SUCCESS, "");
    }

    // I'm not really sure if this method is needed, but wrapper needs it. 
    public boolean createAccount(OfflinePlayer player) {
        if (hasAccount(player)) return false;
        setBalance(player, 0);
        BuoyyEcon.getPlayers().add(player);
        return true;
    }

}
