package com.github.buoyy.buoyyecon.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

public class OpenCommand implements SubCommand {

    private EconHandler econ = BuoyyEcon.getEconomy();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED+"Can't open inventory in console.");
            return true;
        }
        Player target = (args.length < 2 ? ((Player) sender) : Bukkit.getOfflinePlayer(args[1]).getPlayer());
        if (target == null) {
            sender.sendMessage(ChatColor.RED+"No such player exists/has ever joined the server.");
            return true;
        }
        econ.createPlayerAccount(target);
        player.openInventory(econ.getInventory(target));
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        return null;
    }
    
}
