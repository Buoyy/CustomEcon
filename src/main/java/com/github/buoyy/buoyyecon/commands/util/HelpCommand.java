package com.github.buoyy.buoyyecon.commands.util;

import com.github.buoyy.api.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] strings) {
        sender.sendMessage(ChatColor.GREEN+"Welcome to BuoyyEcon, an item based dual economy system.");
        sender.sendMessage(ChatColor.GREEN+"There are two currencies: "+ChatColor.GOLD+"gold and diamonds.");
        sender.sendMessage(ChatColor.GREEN+"You have a storage system for each of these currencies equivalent of a double chest.");
        sender.sendMessage(ChatColor.GREEN+"You can withdraw/deposit currency into your account and pay/request players for both the currencies.");
        sender.sendMessage(ChatColor.GREEN+"For graphical usage, use"+ChatColor.AQUA+" /econ"+ChatColor.GREEN+" to open main menu.");
        sender.sendMessage(ChatColor.GREEN+"For managing gold, do /gold. For diamond, do /dia.");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] strings) {
        return List.of();
    }
}
