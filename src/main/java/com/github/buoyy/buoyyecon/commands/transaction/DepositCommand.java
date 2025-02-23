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

public class DepositCommand implements SubCommand {
    private final Economy econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED+"Can't add to console!");
            return true;
        }
        int oldBalance = econ.getBalance(player);
        int amount = 0;
        if (args[1].equals("all")) {
            for (ItemStack i: player.getInventory().getStorageContents()) {
                if (i != null && i.getType() == Material.DIAMOND) {
                    amount += i.getAmount();
                }
            }
        } else amount = Integer.parseInt(args[1]);
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Can't add zero diamonds!");
            return true;
        }
        if (!(player.getInventory().contains(Material.DIAMOND, amount))) {
            player.sendMessage(ChatColor.RED + "You don't have " + amount + " diamonds" +
                    " in your inventory!");
            return true;
        }
        if (getAvailableSpace(player) == 0) {
            player.sendMessage(ChatColor.RED+"You don't have more storage space!");
            return true;
        }
        player.getInventory().removeItem(new ItemStack(Material.DIAMOND, amount));
        Transaction deposition = BuoyyEcon.getEconomy().add(player, amount);
        if (!deposition.isSuccessful()) {
            sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + deposition.message);
            return true;
        }
        int notDeposited = oldBalance + amount - 3456;
        if (notDeposited > 0) {
            player.getWorld()
                    .dropItemNaturally(player.getLocation(),
                            new ItemStack(Material.DIAMOND, notDeposited));
            player.sendMessage(ChatColor.DARK_GREEN+econ.format(notDeposited)+
                    " have been dropped to you as they couldn't be stored.");
        }
        return true;
    }
    @Override
    public List<String> getCompletions(String[] args) {
        return args.length == 2 ?
                List.of("1", "16", "32", "64", "all") :
                List.of();
    }
    private int getAvailableSpace(Player player) {
        return (3456 - econ.getBalance(player));
    }
}
