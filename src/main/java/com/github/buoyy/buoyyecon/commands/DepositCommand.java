package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.Economy;
import com.github.buoyy.buoyyecon.economy.Transaction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class DepositCommand implements SubCommand{
    private final Economy econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED+"Can't add to console!");
            return true;
        }
        int amount = Integer.parseInt(args[1]);
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Can't add zero diamonds!");
        }
        if (!(player.getInventory().contains(Material.DIAMOND, amount))) {
            player.sendMessage(ChatColor.RED + "You don't have " + amount + " diamonds" +
                    " in your inventory!");
            return true;
        }
        player.getInventory().removeItem(new ItemStack(Material.DIAMOND, amount));
        Transaction deposition = BuoyyEcon.getEconomy().add(player, amount);
        if (!deposition.isSuccessful()) {
            sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + deposition.message);
        } else {
            player.sendMessage(ChatColor.GREEN + "Your balance was set to "
                        + ChatColor.GOLD + econ.prettyBal(player));
        }
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        List<String> tabs;
        if (args.length == 2) {
            tabs = Arrays.asList("1", "10", "100", "1000");
        } else {
            tabs = List.of();
        }
        return tabs;
    }
}
