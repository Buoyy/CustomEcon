package com.github.buoyy.buoyyecon.economy;

import org.bukkit.OfflinePlayer;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.files.CustomYAML;

// This class is really just a wrapper for handling the data file
public class EconomyManager {

    private final CustomYAML dataFile = BuoyyEcon.getDataFile();
    private final String CURRENCY_SINGULAR = BuoyyEcon.getPlugin().getConfig().getString("currency-singular");
    private final String CURRENCY_PLURAL = BuoyyEcon.getPlugin().getConfig().getString("currency-plural");
    // Check if there is a section with the player's UUID
    public boolean hasAccount(OfflinePlayer player) {
        return dataFile.getConfig().contains(player.getUniqueId().toString());
    }

    // Self-explanatory
    public float getBalance(OfflinePlayer player) {
        return dataFile.getConfig().getInt(player.getUniqueId() + ".balance");
    }

    public String format(float amount) {
        return (int)amount+" "+((int) amount == 1 ? CURRENCY_SINGULAR : CURRENCY_PLURAL);
    }

    // Set the amount and don't forget to save! This private method is pretty useful.
    private void setBalance(OfflinePlayer player, float amount) {
        dataFile.getConfig().set(player.getUniqueId()+".balance", amount);
        dataFile.save();
    }

    // Amount shouldn't be negative. EVER.
    public EconomyAction deposit(OfflinePlayer player, float amount) {
        if (amount < 0) 
            return new EconomyAction(amount, getBalance(player),
                                false, "Negative amount");
        setBalance(player, getBalance(player)+amount);
        return new EconomyAction(amount, getBalance(player),
                            true, "");
    }

    // Again, amount shouldn't be negative.
    // Also, can't withdraw if player doesn't have that much. Obviously
    public EconomyAction withdraw(OfflinePlayer player, float amount) {
        if (amount < 0) 
                    return new EconomyAction(amount, getBalance(player),
                                        false, "Negative amount");
        if (amount > getBalance(player)) 
                    return new EconomyAction(amount, getBalance(player),
                                        false, "Insufficient funds");
        setBalance(player, getBalance(player)-amount);
        return new EconomyAction(amount, getBalance(player),
                            true, "");
    }

    public void createAccount(OfflinePlayer player) {
        if (!hasAccount(player)) setBalance(player, 0);
    }

}
