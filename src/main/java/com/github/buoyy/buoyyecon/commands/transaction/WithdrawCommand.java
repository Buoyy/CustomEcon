package com.github.buoyy.buoyyecon.commands.transaction;

import com.github.buoyy.api.CurrencyType;
import com.github.buoyy.api.Transaction;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.commands.api.SubCommand;
import com.github.buoyy.buoyyecon.economy.EconomyImpl;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WithdrawCommand implements SubCommand {
    private final EconomyImpl econ;
    private final CurrencyType type;

    public WithdrawCommand(CurrencyType type) {
        this.econ = BuoyyEcon.getEconomy();
        this.type = type;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Can't remove from console!");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Incomplete command!");
            return true;
        }
        int amount;
        if (args[1].equals("all"))
            amount = econ.getBalance(player, type);
        else try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED+"That's not a number.");
            return true;
        }
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Can't add zero diamonds!");
            return true;
        }
        if (!econ.has(player, type, amount)) {
            player.sendMessage(ChatColor.RED + "You don't have " +econ.format(amount, type) +
                    " in your storage!");
            return true;
        }
        Transaction withdrawal = econ.subtract(player,type, amount);
        if (!withdrawal.isSuccessful()) {
            player.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + withdrawal.message);
            return true;
        }
        //Then we can drop the withdrawn diamonds at the player.
        ItemStack toDrop = new ItemStack(type.getMaterial(), amount);
        player.getWorld().dropItemNaturally(player.getLocation(), toDrop);
        player.sendMessage(ChatColor.GREEN+"The withdrawn "+type.getNamePlural()+" have been dropped near you.");
        return true;
    }
    @Override
    public List<String> getCompletions(String[] args) {
        return args.length == 2 ?
                List.of("1", "16", "32", "64", "all") :
                List.of();
    }
}
