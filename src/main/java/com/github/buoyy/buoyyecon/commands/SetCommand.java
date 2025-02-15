package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements SubCommand {
    private final Economy econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Incomplete command");
            return true;
        }
        Player target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
        double amount = Double.parseDouble(args[1]);
        if (econ.getBalance(target) < amount) {
            EconomyResponse response = econ.depositPlayer(target, amount - econ.getBalance(target));
            if (response.transactionSuccess()) {
                sender.sendMessage(ChatColor.AQUA+"Successfully set " + ChatColor.AQUA + target.getName() +
                        "'s balance to " + ChatColor.GOLD + econ.format(amount));
                target.sendMessage(ChatColor.AQUA+"Your balance was set to " + ChatColor.GOLD + econ.format(amount));
            } else {
                sender.sendMessage(ChatColor.RED+"There was an error: "+response.errorMessage);
            }
            return true;
        }
        if (econ.getBalance(target) > amount) {
            EconomyResponse response = econ.withdrawPlayer(target, econ.getBalance(target) - amount);

        }
        return true;
    }
}
