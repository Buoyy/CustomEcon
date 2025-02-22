package com.github.buoyy.buoyyecon.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    boolean execute(CommandSender sender, String[] args);
    List<String> getCompletions(String[] args);
}