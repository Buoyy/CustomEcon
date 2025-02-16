package com.github.buoyy.buoyyecon.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.economy.EconHandler;

import net.milkbowl.vault.economy.EconomyResponse;

public class SetCommand implements SubCommand {
    private final EconHandler econ = BuoyyEcon.getEconomy();
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return true;
    }
}
