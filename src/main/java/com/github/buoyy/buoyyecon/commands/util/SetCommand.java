package com.github.buoyy.buoyyecon.commands.util;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.Economy;
import com.github.buoyy.api.economy.Transaction;
import com.github.buoyy.api.command.BaseCommand;
import com.github.buoyy.api.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class SetCommand implements SubCommand {

    private final Economy econ;
    private final CurrencyType type;

    public SetCommand(CurrencyType type) {
        this.econ = BuoyyEcon.getEconomy();
        this.type = type;
    }

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
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED+"That's not a number.");
                return true;
            }
            Transaction set = ((econ.has(target, type, amount)) ?
                    econ.subtract(target, type, econ.getBalance(target, type)-amount) :
                    econ.add(target, type, econ.getBalance(target, type)+amount));
            if (!set.isSuccessful()) {
                sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + set.message);
            } else {
                sender.sendMessage(ChatColor.AQUA + target.getName() + "'s" +
                        ChatColor.GREEN + " balance was set to " + ChatColor.GOLD + econ.prettyBal(target, type));
                if (target.isOnline())
                    Objects.requireNonNull(target.getPlayer()).sendMessage(ChatColor.GREEN + "Your balance was set to "
                            + ChatColor.GOLD + econ.prettyBal(target, type));
            }
        } else
            sender.sendMessage(ChatColor.RED + "No such player exists/has ever joined the server.");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        List<String> tabs;
        if (args.length == 2) {
            tabs = BaseCommand.getPlayerNames(true);
        } else if (args.length == 3) {
            tabs = Arrays.asList("1", "16", "32", "64");
        } else {
            tabs = List.of();
        }
        return tabs;
    }
}
