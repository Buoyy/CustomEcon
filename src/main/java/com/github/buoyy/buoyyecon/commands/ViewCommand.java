package com.github.buoyy.buoyyecon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;

public class ViewCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        
        if (args.length < 2) {
            if (sender instanceof Player player) {
                player.sendMessage(ChatColor.GREEN+"Your current balance is: "+ChatColor.GOLD+BuoyyEcon.getEconomy().getBalance(player));
            }
            else {
                sender.sendMessage(ChatColor.RED+"No balance for server console.");
            }
            return true;
        }
        Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
        sender.sendMessage(ChatColor.AQUA+" "+target.getName()+ChatColor.GREEN+" has "+BuoyyEcon.getEconomy().format(BuoyyEcon.getEconomy().getBalance(target)));

        return true;
    }
}
