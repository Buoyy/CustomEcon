package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand implements SubCommand{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        BuoyyEcon.getMessenger().consoleGood("Reloading plugin files...");
        sender.sendMessage(ChatColor.GREEN + "Reloading plugin files...");
        BuoyyEcon.getDataFile().reload();
        BuoyyEcon.getMessenger().consoleGood("...Done.");
        sender.sendMessage(ChatColor.GREEN + "...Done.");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return List.of();
    }
}
