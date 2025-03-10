package com.github.buoyy.buoyyecon.commands.transaction;

import com.github.buoyy.api.command.SubCommand;
import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.Economy;
import com.github.buoyy.api.economy.Transaction;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DepositCommand implements SubCommand {
    private final Economy econ;
    private final CurrencyType type;

    public DepositCommand(CurrencyType type) {
        this.econ = BuoyyEcon.getEconomy();
        this.type = type;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Can't add to console!");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Incomplete command!");
            return true;
        }
        int oldBalance = econ.getBalance(player, type);
        int amount = 0;
        if (args[1].equals("all")) {
            for (ItemStack i : player.getInventory().getStorageContents()) {
                if (i != null && i.getType() == type.getMaterial()) {
                    amount += i.getAmount();
                }
            }
        } else try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "That's not a number.");
            return true;
        }
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Can't add zero " + type.getNamePlural());
            return true;
        }
        if (!(player.getInventory().contains(type.getMaterial(), amount))) {
            player.sendMessage(ChatColor.RED + "You don't have " + econ.format(amount, type) +
                    " in your inventory!");
            return true;
        }
        if (getAvailableSpace(player) == 0) {
            player.sendMessage(ChatColor.RED + "You don't have more storage space!");
            return true;
        }
        player.getInventory().removeItem(new ItemStack(type.getMaterial(), amount));
        Transaction deposition = econ.add(player, type, amount);
        if (!deposition.isSuccessful()) {
            sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.RED + deposition.message);
            return true;
        }
        int notDeposited = oldBalance + amount - 3456;
        if (notDeposited > 0) {
            player.getWorld()
                    .dropItemNaturally(player.getLocation(),
                            new ItemStack(type.getMaterial(), notDeposited));
            player.sendMessage(ChatColor.DARK_GREEN + econ.format(notDeposited, type) +
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
        return (3456 - econ.getBalance(player, type));
    }
}
