package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.VaultEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WithdrawCommand implements SubCommand {
    private final VaultEconomy econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Incomplete command!");
            return true;
        }
        OfflinePlayer target = BuoyyEcon.getPlayers().stream()
                .filter(p -> Objects.equals(p.getName(), args[1]))
                .findFirst()
                .orElse(null);
        if (target == null || !target.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + "No such player exists/has ever joined the server.");
            return true;
        }
        double amount = Double.parseDouble(args[2]);
        if (Double.isNaN(amount)) {
            sender.sendMessage(ChatColor.RED + "Amount must be a number!");
            return true;
        }
        EconomyResponse response = econ.withdrawPlayer(target, amount);
        if (response.transactionSuccess()) {
            sender.sendMessage(ChatColor.GOLD+econ.format(response.amount)+ChatColor.GREEN+
                    " were withdrawn from "+ChatColor.AQUA+target.getName()+ChatColor.GREEN+"'s balance.");
            if (target.isOnline())
                Objects.requireNonNull(target.getPlayer()).sendMessage(ChatColor.GOLD+econ.format(response.amount)+ChatColor.GREEN+
                    " were withdrawn from your balance.");
        } else {
            sender.sendMessage(ChatColor.RED + "Error!: " + ChatColor.DARK_RED + response.errorMessage);
        }
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        List<String> tabs;
        if (args.length == 2) {
            tabs = null;
        } else if (args.length == 3) {
            tabs = Arrays.asList("1", "10", "100", "1000");
        } else {
            tabs = null;
        }
        return tabs;
    }
}
