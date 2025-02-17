package com.github.buoyy.buoyyecon.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

import java.util.List;
import java.util.Objects;

public class ViewCommand implements SubCommand {
    private final EconHandler econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args.length < 2) {
            sender.sendMessage(ChatColor.RED+"Balance command is not available for server console.");
            return true;
        }
        OfflinePlayer target = args.length < 2 ? (Player) sender : BuoyyEcon.getPlayers().stream()
                .filter(p -> Objects.equals(p.getName(), args[1]))
                .findFirst()
                .orElse(null);
        if (target == null || !target.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + "No such player exists/has ever joined the server.");
            return true;
        }
        sender.sendMessage(ChatColor.AQUA + target.getName() + ChatColor.GREEN + "'s balance is: "
                + ChatColor.GOLD + econ.format(econ.getBalance(target)));
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        return null;
    }
}
