package com.github.buoyy.buoyyecon.economy;

import com.github.buoyy.api.CurrencyType;
import com.github.buoyy.api.Economy;
import com.github.buoyy.api.Transaction;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.files.YAML;
import org.bukkit.entity.Player;

// This class handles everything data related.
// For storage stuff, go check the gui package.
public class EconomyImpl implements Economy {

    private final YAML dataFile = BuoyyEcon.getDataFile();
    // Check if there is a section with the player's UUID
    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return dataFile.getConfig().contains(player.getUniqueId().toString());
    }

    // Self-explanatory
    @Override
    public int getBalance(OfflinePlayer player, CurrencyType type) {
        return dataFile.getConfig().getInt(player.getUniqueId()+
                ".balance."+type.getNamePlural());
    }

    @Override
    public boolean has(OfflinePlayer player, CurrencyType type, int amount) {
        return getBalance(player, type) >= amount;
    }

    @Override
    public String format(int a, CurrencyType type) {
        return a + " " + (a == 1 ? type.getNameSingular() : type.getNamePlural());
    }

    public String prettyBal(OfflinePlayer player, CurrencyType type) {
        return format(getBalance(player, type), type);
    }

    // Set the amount and don't forget to save! This private method is pretty useful.
    public void setBalance(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount <= 3456) {
            dataFile.getConfig().set(player.getUniqueId() + ".balance."+type.getNamePlural(), amount);
            dataFile.save();
            BuoyyEcon.getMessenger().consoleOK("Set balance of player " + player.getName() + " to " +prettyBal(player, type));
            if (player.isOnline())
                ((Player)player).sendMessage(ChatColor.GREEN+"Now, your balance is "+ChatColor.GOLD+prettyBal(player, type));
        } else {
            if (player.isOnline())
                ((Player)player).sendMessage(ChatColor.RED + "54 stacks is the current limit!\nCan't have more diamonds.");
            setBalance(player, type, 3456);
        }
    }

    // Amount shouldn't be negative. EVER.
    @Override
    public Transaction add(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    type,
                    "Negative amount",
                    Transaction.TransactionResult.FAILURE);
        setBalance(player, type, getBalance(player, type)+amount);
        return new Transaction(amount,
                type,
                "",
                Transaction.TransactionResult.SUCCESS);
    }

    // Again, amount shouldn't be negative.
    // Also, can't subtract if player doesn't have that much. Obviously
    @Override
    public Transaction subtract(OfflinePlayer player, CurrencyType type, int amount) {
        if (amount < 0)
            return new Transaction(amount,
                    type,
                    "Negative amount",
                    Transaction.TransactionResult.FAILURE);
        if (!has(player, type, amount))
            return new Transaction(amount,
                    type,
                    "Insufficient funds",
                    Transaction.TransactionResult.FAILURE);
        return new Transaction(amount,
                type,
                "",
                Transaction.TransactionResult.SUCCESS);
    }

    public void loadAccount(OfflinePlayer player) {
        if (hasAccount(player)) {
            BuoyyEcon.getMessenger().consoleOK("Player account found: "+player.getName());
            final int dia = getBalance(player, CurrencyType.DIAMOND);
            final int gold = getBalance(player, CurrencyType.GOLD);
            setBalance(player, CurrencyType.DIAMOND, dia);
            setBalance(player, CurrencyType.GOLD, gold);
        } else {
            BuoyyEcon.getMessenger().consoleOK("Account not found for: "+player.getName());
            BuoyyEcon.getMessenger().consoleGood("Creating account for "+player.getName());
            setBalance(player, CurrencyType.DIAMOND, 0);
            setBalance(player, CurrencyType.GOLD, 0);
        }
    }
}
