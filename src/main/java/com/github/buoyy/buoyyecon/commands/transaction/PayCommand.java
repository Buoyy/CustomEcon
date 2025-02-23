package com.github.buoyy.buoyyecon.commands.transaction;

import com.github.buoyy.api.CurrencyType;
import com.github.buoyy.api.Transaction;
import com.github.buoyy.buoyyecon.BuoyyEcon;

import com.github.buoyy.buoyyecon.commands.api.BaseCommand;
import com.github.buoyy.buoyyecon.commands.api.SubCommand;
import com.github.buoyy.buoyyecon.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings("deprecation")
public class PayCommand implements SubCommand {
    private final Economy econ;
    private final CurrencyType type;
    public PayCommand(CurrencyType type) {
        this.econ = BuoyyEcon.getEconomy();
        this.type = type;
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command!");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (econ.hasAccount(target)) { // Process only if target has played before (obviously)
            int amount = Integer.parseInt(args[2]);
            if (amount == 0) {
                player.sendMessage(ChatColor.DARK_GREEN + "Can't pay zero amount.");
                return true;
            }
            if (!econ.has(player, type, amount)) {
                player.sendMessage(ChatColor.RED + "Amount is more than your current balance!");
                return true;
            }
            int targetBalance = econ.getBalance(target, type); // Caching target's current balance for future use
            Transaction removal = econ.subtract(player, type, amount);
            if (removal.isSuccessful()) {
                Transaction addition = econ.add(target, type, amount);
                if (!addition.isSuccessful() && target.isOnline()) {
                    ((Player) target).sendMessage(ChatColor.RED + "Error during payment: " +
                            ChatColor.DARK_RED + addition.message);
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Error during payment: " +
                        ChatColor.DARK_RED + removal.message);
                return true;
            }
            int notTransferred = targetBalance + amount - 3456;
            if (notTransferred > 0) {
                if (target.isOnline()) {
                    ((Player) target).sendMessage(ChatColor.DARK_GREEN + "Due to " +
                            "insufficient space, " + ChatColor.GOLD + econ.format(notTransferred, type) + ChatColor.DARK_GREEN +
                            "couldn't be transferred to your storage. ");
                    ItemStack toDrop = new ItemStack(type.getMaterial(), notTransferred);
                    ((Player) target).getWorld().dropItemNaturally(((Player) target).getLocation(), toDrop);
                    ((Player) target).sendMessage(ChatColor.DARK_GREEN + "They have been dropped near you.");
                } else { //Add the remainder back to sender
                    player.sendMessage(ChatColor.DARK_GREEN + "Targeted player didn't have enough space to store " +
                            ChatColor.GOLD + econ.format(amount, type) + ChatColor.DARK_GREEN + ". The remaining " + ChatColor.GOLD +
                            econ.format(notTransferred, type) + ChatColor.DARK_GREEN + " were added back to your storage.");
                    econ.add(player, type, notTransferred); //We are sure that there won't be any failures in this transaction. No need to cache it.
                }
            }
        } else
            player.sendMessage(ChatColor.RED+"No such player exists or has ever joined this server.");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        List<String> tabs;
        if (args.length == 2)
            tabs = BaseCommand.getPlayerNames(false);
        else if (args.length == 3)
            tabs = List.of("1", "16", "32", "64");
        else
            tabs = List.of();
        return tabs;
    }
}
