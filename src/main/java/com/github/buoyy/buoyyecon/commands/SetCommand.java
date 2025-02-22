package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.economy.Transaction;
import com.github.buoyy.buoyyecon.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SetCommand implements SubCommand {

    private final Economy econ = BuoyyEcon.getEconomy();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player && !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "This command is only for OPs.");
        }
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Incomplete command!");
            return true;
        }
        OfflinePlayer target = args[1].equals("self")
                ? (OfflinePlayer) sender
                : Bukkit.getOfflinePlayer(args[1]);
        if (econ.hasAccount(target)) {
            // Float because we cant check if it is NaN.
            float amount = Float.parseFloat(args[2]);
            if (Float.isNaN(amount)) {
                sender.sendMessage(ChatColor.RED + "Amount must be a number!");
                return true;
            }
            Transaction set = ((amount < econ.getBalance(target)) ?
                    econ.subtract(target, econ.getBalance(target)-(int)amount) :
                    econ.add(target, (int)amount-econ.getBalance(target)));
            if (!set.isSuccessful()) {
                sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + set.message);
            } else {
                sender.sendMessage(ChatColor.AQUA + target.getName() + "'s" +
                        ChatColor.GREEN + " balance was set to " + ChatColor.GOLD + econ.prettyBal(target));
                if (target.isOnline())
                    Objects.requireNonNull(target.getPlayer()).sendMessage(ChatColor.GREEN + "Your balance was set to "
                            + ChatColor.GOLD + econ.prettyBal(target));
            }
        } else
            sender.sendMessage(ChatColor.RED + "No such player exists/has ever joined the server.");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        List<String> tabs;
        if (args.length == 2) {
            tabs = MainCommand.getPlayerNames();
        } else if (args.length == 3) {
            tabs = Arrays.asList("1", "10", "100", "1000");
        } else {
            tabs = List.of();
        }
        return tabs;
    }
}
