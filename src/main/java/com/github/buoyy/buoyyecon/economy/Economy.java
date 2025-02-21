package com.github.buoyy.buoyyecon.economy;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.files.YAML;
import org.bukkit.entity.Player;

// This class handles everything data related.
// For storage stuff, go check the gui package.
public class Economy {

    private final YAML dataFile = BuoyyEcon.getDataFile();
    private final String CURRENCY_SINGULAR = BuoyyEcon.getPlugin().getConfig().getString("currency-singular");
    private final String CURRENCY_PLURAL = BuoyyEcon.getPlugin().getConfig().getString("currency-plural");

    // Check if there is a section with the player's UUID
    public boolean hasAccount(OfflinePlayer player) {
        return dataFile.getConfig().contains(player.getUniqueId().toString());
    }

    // Self-explanatory
    public int getBalance(OfflinePlayer player) {
        return dataFile.getConfig().getInt(player.getUniqueId() + ".balance");
    }

    public String format(int amount) {
        return amount + " " + (amount == 1 ? CURRENCY_SINGULAR : CURRENCY_PLURAL);
    }

    public String prettyBal(OfflinePlayer player) {
        return format(getBalance(player));
    }

    // Set the amount and don't forget to save! This private method is pretty useful.
    public void setBalance(OfflinePlayer player, float amount) {
        if (amount <= 64 * 54) {
            dataFile.getConfig().set(player.getUniqueId() + ".balance", amount);
            dataFile.save();
            BuoyyEcon.getMessenger().consoleOK("Set balance of player " + player.getName() + " to " +getBalance(player));
        } else {
            if (player.isOnline())
                ((Player)player).sendMessage(ChatColor.RED + "54 stacks is the current limit!\nCan't have more diamonds.");
            setBalance(player, 54*64);
        }
    }

    // Amount shouldn't be negative. EVER.
    public Transaction deposit(OfflinePlayer player, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    false, "Negative amount");
        setBalance(player, getBalance(player) + amount);
        return new Transaction(amount,
                true, "");
    }

    // Again, amount shouldn't be negative.
    // Also, can't withdraw if player doesn't have that much. Obviously
    public Transaction withdraw(OfflinePlayer player, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    false, "Negative amount");
        if (amount > getBalance(player))
            return new Transaction(amount, false, "Insufficient funds");
        setBalance(player, getBalance(player) - amount);
        return new Transaction(amount, true, "");
    }

    public void loadAccount(OfflinePlayer player) {
        if (hasAccount(player)) {
            BuoyyEcon.getMessenger().consoleOK("Player found: "+player.getName());
            final int bal = getBalance(player);
            setBalance(player, bal);
        } else {
            BuoyyEcon.getMessenger().consoleOK("Player not found: "+player.getName());
            BuoyyEcon.getMessenger().consoleGood("Creating account for "+player.getName());
            setBalance(player, 0);
        }
    }
}
