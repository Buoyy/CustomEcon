package com.github.buoyy.buoyyecon.commands.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class BaseCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subs;
    private final String usage;
    private final Consumer<Player> onNoArgs;

    public BaseCommand(String usage, Consumer<Player> onNoArgs) {
        this.subs = new HashMap<>();
        this.usage = usage;
        this.onNoArgs = onNoArgs;
    }
    public void registerSubCommand(String name, SubCommand command) {
        subs.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatColor.GREEN+"Usage: "+usage);
                return true;
            }
            this.onNoArgs.accept(player);
            return true;
        }
        SubCommand subCommand = subs.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage(ChatColor.RED + "Unknown subcommand!\nUsage: "+usage);
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
            tabs = subs.get(args[0]).getCompletions(args);
        }
        return tabs;
    }

    public static List<String> getPlayerNames(boolean includeSelf) {
        List<String> names = new ArrayList<>();
        Arrays.stream(Bukkit.getOfflinePlayers())
                .toList()
                .forEach(p -> names.add(p.getName()));
        if (includeSelf) names.add("self");
        return names;
    }
}
