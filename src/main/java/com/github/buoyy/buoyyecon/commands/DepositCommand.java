package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.Transaction;
import com.github.buoyy.buoyyecon.economy.EconomyManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DepositCommand implements SubCommand {
    private final EconomyManager econ = BuoyyEcon.getEconomy();
    @Override @SuppressWarnings("deprecation")
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Incomplete command!");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (econ.hasAccount(target)) {
            float amount = Float.parseFloat(args[2]);
            if (Float.isNaN(amount)) {
                sender.sendMessage(ChatColor.RED + "Amount must be a number!");
                return true;
            }
            Transaction deposit = econ.deposit(target, amount);
            if (deposit.isSuccessful()) {
                sender.sendMessage(ChatColor.GOLD + econ.format(deposit.getAmount()) + ChatColor.GREEN +
                        " were added to " + ChatColor.AQUA + target.getName() + ChatColor.GREEN + "'s balance.");
                if (target.isOnline())
                    Objects.requireNonNull(target.getPlayer()).sendMessage(ChatColor.GOLD + econ.format(deposit.getAmount()) + ChatColor.GREEN +
                            " were added to your balance.");
            } else {
                sender.sendMessage(ChatColor.RED + "Error!: " + ChatColor.DARK_RED + deposit.getMessage());
            }
        } else
            sender.sendMessage(ChatColor.RED + "No such player exists/has ever joined the server.");
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        List<String> tabs;
        if (args.length == 2) {
            tabs = null;
        } else if (args.length == 3) {
            tabs = Arrays.asList("1", "10", "100", "1000");
        } else {
            tabs = null;
        }
        return tabs;
    }
}
