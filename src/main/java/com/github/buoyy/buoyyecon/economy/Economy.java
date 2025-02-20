package com.github.buoyy.buoyyecon.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.files.YAML;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


// This class is really just a wrapper for handling the data file
public class Economy {

    private final YAML dataFile = BuoyyEcon.getDataFile();
    private final Map<UUID, Inventory> storage = new HashMap<>();
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

    public Inventory getStorage(OfflinePlayer player) {
        return storage.get(player.getUniqueId());
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
            if (amount != 0) {
                getStorage(player).clear();
                getStorage(player).addItem(new ItemStack(Material.DIAMOND, (int) amount));
            }
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

    @SuppressWarnings("deprecation")
    public void loadAccount(OfflinePlayer player) {
        storage.put(player.getUniqueId(), Bukkit.createInventory(null, 54, ChatColor.AQUA+"Storage"));
        if (hasAccount(player)) {
            BuoyyEcon.getMessenger().consoleOK("Player found: "+player.getName());
            final int bal = getBalance(player);
            setBalance(player, bal);
        } else {
            BuoyyEcon.getMessenger().consoleOK("Player not found: "+player.getName());
            setBalance(player, 0);
        }
    }
}
