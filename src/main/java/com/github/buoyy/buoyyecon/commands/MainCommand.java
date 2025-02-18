package com.github.buoyy.buoyyecon.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subs = new HashMap<>();

    public void registerSubCommand(String name, SubCommand command) {
        subs.put(name.toLowerCase(), command);
    }

    @Override @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "Usage: /econ <deposit/withdraw/view/set>");
            return true;
        }
        SubCommand subCommand = subs.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage(ChatColor.RED + "Unknown subcommand!\nUsage: /econ <deposit/withdraw/view/set>");
            return true;
        }
        return subCommand.execute(sender, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> tabs;
        if (args.length == 1) {
            tabs = subs.keySet().stream().toList();
        } else {
            tabs = subs.get(args[0]).getTabs(args);
        }
        return tabs;
    }
}
