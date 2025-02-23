package com.github.buoyy.buoyyecon.commands.transaction;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.commands.SubCommand;
import com.github.buoyy.buoyyecon.economy.Economy;
import com.github.buoyy.buoyyecon.economy.Transaction;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class WithdrawCommand implements SubCommand {
    private final Economy econ = BuoyyEcon.getEconomy();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Can't remove from console!");
            return true;
        }
        int amount = (args[1].equals("all")) ?
                econ.getBalance(player) :
                Integer.parseInt(args[1]);
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Can't add zero diamonds!");
            return true;
        }
        if (econ.getBalance(player) < amount) {
            player.sendMessage(ChatColor.RED + "You don't have " + amount + " diamonds" +
                    " in your storage!");
            return true;
        }
        Transaction withdrawal = econ.subtract(player, amount);
        if (!withdrawal.isSuccessful()) {
            player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + withdrawal.message);
            return true;
        }
        //Then we can drop the withdrawn diamonds at the player.
        ItemStack toDrop = new ItemStack(Material.DIAMOND, amount);
        player.getWorld().dropItemNaturally(player.getLocation(), toDrop);
        player.sendMessage(ChatColor.GREEN+"The withdrawn diamonds have been dropped near you.");
        return true;
    }
    @Override
    public List<String> getCompletions(String[] args) {
        return args.length == 2 ?
                List.of("1", "16", "32", "64", "all") :
                List.of();
    }
}
