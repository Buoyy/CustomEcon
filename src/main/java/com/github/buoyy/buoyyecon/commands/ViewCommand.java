package com.github.buoyy.buoyyecon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

import java.util.List;

public class ViewCommand implements SubCommand {
    private final EconHandler econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length < 2) {
            sender.sendMessage(ChatColor.RED+"Balance command is not available for server console.");
            return true;
        }
        Player target = (args.length < 2 ? ((Player) sender) : Bukkit.getOfflinePlayer(args[1]).getPlayer());
        if (target == null) {
            sender.sendMessage(ChatColor.RED+"No such player exists/has ever joined the server.");
            return true;
        }
        econ.createPlayerAccount(target);
        sender.sendMessage(ChatColor. AQUA + target.getName() + "'s" + 
                        ChatColor.GREEN + " balance is: " +
                        ChatColor.GOLD + econ.formattedBalance(target));
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        return null;
    }
}
