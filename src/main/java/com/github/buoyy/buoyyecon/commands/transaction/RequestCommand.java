package com.github.buoyy.buoyyecon.commands.transaction;

import com.github.buoyy.api.command.BaseCommand;
import com.github.buoyy.api.command.SubCommand;
import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.Economy;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@SuppressWarnings("deprecation")
public class RequestCommand implements SubCommand {
    private final Economy econ;
    private final CurrencyType type;

    public RequestCommand(CurrencyType type) {
        this.econ = BuoyyEcon.getEconomy();
        this.type = type;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This is a player only command!");
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Incomplete command!");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (target.getPlayer() == player) {
            player.sendMessage(ChatColor.RED+"Cannot request self!");
            return true;
        }
        if (econ.hasAccount(target)) { // Process only if target has played before (obviously)
            final int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "That's not a number.");
                return true;
            }
            if (amount == 0) {
                player.sendMessage(ChatColor.DARK_GREEN + "Can't request for zero amount.");
                return true;
            }
            if (!econ.setRequest(player, target, type, amount)) {
                player.sendMessage(ChatColor.RED + "Request could not be set successfully.");
                return true;
            }
            player.sendMessage(ChatColor.GREEN + "Your request to " + ChatColor.AQUA + target.getName() + ChatColor.GREEN + " for "
                    + ChatColor.GOLD + econ.format(amount, type) + ChatColor.GREEN + " has been added successfully.");
            if (target.isOnline()) {
                ((Player) target).sendMessage(ChatColor.GREEN + "You have a payment request from player " + ChatColor.AQUA
                        + player.getName() + ChatColor.GREEN + " of " + ChatColor.GOLD + econ.format(amount, type) + '.');
            }
        } else player.sendMessage(ChatColor.RED + "No such player exists on this server!");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        List<String> tabs;
        if (args.length == 2) {
            tabs = BaseCommand.getPlayerNames(false);
        } else if (args.length == 3)
            tabs = List.of("1", "16", "32", "64");
        else
        tabs = List.of();
        return tabs;
    }
}
