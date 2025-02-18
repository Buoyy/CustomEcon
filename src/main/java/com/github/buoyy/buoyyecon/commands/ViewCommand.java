package com.github.buoyy.buoyyecon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;

import java.util.List;

public class ViewCommand implements SubCommand {
    @Override @SuppressWarnings("deprecation")
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length < 2) {
            sender.sendMessage(ChatColor.RED+"Balance command is not available for server console.");
            return true;
        }
        OfflinePlayer target = args.length < 2 ? (Player) sender : Bukkit.getOfflinePlayer(args[1]);
        if (!target.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + "No such player exists/has ever joined the server.");
            return true;
        }
        sender.sendMessage(ChatColor.AQUA + target.getName() + ChatColor.GREEN + "'s balance is: "
                + ChatColor.GOLD + BuoyyEcon.getEconomy().getBalance(target));
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) { return null; }
}
