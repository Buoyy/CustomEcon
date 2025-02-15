package com.github.buoyy.buoyyecon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

import net.milkbowl.vault.economy.EconomyResponse;

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
            sender.sendMessage(ChatColor.RED + "That player does not exist");
            return true;
        }
        double amount = Double.parseDouble(args[2]);
        EconomyResponse response = econ.setBalance(target, amount);
        if (response.transactionSuccess()) {
            sender.sendMessage(ChatColor.GREEN+"Successfully set " + ChatColor.AQUA + target.getName() +
                               ChatColor.GREEN+"'s balance to " + ChatColor.GOLD + econ.format(response.amount));
            return true;
        } else {
            sender.sendMessage(ChatColor.RED+"An error occured: "+response.errorMessage);
        }
        return true;
    }
}
