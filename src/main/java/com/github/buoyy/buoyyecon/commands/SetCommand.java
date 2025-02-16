package com.github.buoyy.buoyyecon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

import net.milkbowl.vault.economy.EconomyResponse;

import java.util.Arrays;
import java.util.List;

public class SetCommand implements SubCommand {

    private final EconHandler econ = BuoyyEcon.getEconomy();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED+"Incomplete command!");
            return true;
        }
        Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
        if (target == null) {
            sender.sendMessage(ChatColor.RED+"No such player exists/has ever joined the server.");
            return true;
        }
        double amount = Double.parseDouble(args[2]);
        if (Double.isNaN(amount)) {
            sender.sendMessage(ChatColor.RED+"Amount must be a number!");
            return true;
        }
        EconomyResponse response = econ.setBalance(target, amount);
        if (response.transactionSuccess()) {
            sender.sendMessage(ChatColor.AQUA+target.getName()+"'s"+ChatColor.GREEN+" balance was set to "
                            +ChatColor.GOLD+econ.formattedBalance(target));
            target.sendMessage(ChatColor.GREEN+"Your balance was set to "
                    +ChatColor.GOLD+econ.formattedBalance(target));
        } else {
            sender.sendMessage(ChatColor.RED+"Error!: "+ChatColor.DARK_RED+response.errorMessage);
        }
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
