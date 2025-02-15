package com.github.buoyy.buoyyecon.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MainCommand implements CommandExecutor {

    private final Map<String, SubCommand> subs = new HashMap<>();

    public void registerSubCommand(String name, SubCommand command) {
        subs.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "Usage: /econ <add/remove/view/set>");
            return true;
        }
        SubCommand subCommand = subs.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage(ChatColor.RED + "Unknown subcommand!\nUsage: /econ <add/remove/view/set>");
            return true;
        }
        return subCommand.execute(sender, args);
    }
}
