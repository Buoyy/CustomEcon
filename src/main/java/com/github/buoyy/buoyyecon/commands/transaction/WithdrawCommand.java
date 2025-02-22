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

import java.util.HashMap;
import java.util.List;


public class WithdrawCommand implements SubCommand {
    private final Economy econ = BuoyyEcon.getEconomy();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Can't remove from console!");
            return true;
        }
        int amount = Integer.parseInt(args[1]);
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Can't add zero diamonds!");
            return true;
        }
        if (!(econ.getBalance(player) < amount)) {
            player.sendMessage(ChatColor.RED + "You don't have " + amount + " diamonds" +
                    " in your storage!");
            return true;
        }
        if (getAvailableSpace(player) == 0) {
            player.sendMessage(ChatColor.RED + "Your inventory is full! Clear some space first.");
            return true;
        }
        Transaction withdrawal = econ.subtract(player, amount);
        if (!withdrawal.isSuccessful()) {
            player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + withdrawal.message);
            return true;
        }
        player.sendMessage(ChatColor.GREEN + "Now, your balance is " + ChatColor.GOLD + econ.prettyBal(player));
        HashMap<Integer, ItemStack> remainder = player.getInventory()
                .addItem(new ItemStack(Material.DIAMOND, amount));
        if (!remainder.isEmpty()) {
            int diamonds = 0;
            for (ItemStack i: remainder.values()) {
                diamonds += i.getAmount();
                player.getWorld().dropItemNaturally(player.getLocation(), i);
            }
            player.sendMessage(ChatColor.DARK_GREEN+"Due to insufficient space, " +
                    econ.format(diamonds) + " were dropped near you.");
        }
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return args.length == 2 ?
                List.of("1", "16", "32", "64") :
                List.of();
    }

    private int getAvailableSpace(Player player) {
        int space = 0;
        for (ItemStack i: player.getInventory().getContents()) {
            if (i == null || i.getType() == Material.AIR)
                space += 64;
            else if (i.getType() == Material.DIAMOND && i.getAmount() < 64)
                space += (64 - i.getAmount());
        }
        return space;
    }
}
