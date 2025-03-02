package com.github.buoyy.buoyyecon.commands.util;

import com.github.buoyy.api.command.SubCommand;
import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.Economy;
import com.github.buoyy.api.economy.PaymentRequest;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RequestsCommand implements SubCommand {
    private final Economy econ = BuoyyEcon.getEconomy();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED+"Cannot check requests to console!");
            return true;
        }
        List<PaymentRequest> diaRequests = econ.getRequests(player, CurrencyType.DIAMOND);
        List<PaymentRequest> goldRequests = econ.getRequests(player, CurrencyType.GOLD);
        player.sendMessage(ChatColor.GREEN+"You have the following payment requests: ");
        player.sendMessage(ChatColor.AQUA+"DIAMONDS: ");
        for (PaymentRequest dia: diaRequests) {
            player.sendMessage(ChatColor.GREEN+dia.from.getName()+": "+ChatColor.GOLD+dia.amount);
        }
        player.sendMessage(ChatColor.AQUA+"GOLD: ");
        for (PaymentRequest gold: goldRequests) {
            player.sendMessage(ChatColor.GREEN+gold.from.getName()+": "+ChatColor.GOLD+gold.amount);
        }
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return List.of();
    }
}
