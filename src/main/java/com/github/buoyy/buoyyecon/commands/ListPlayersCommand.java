package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

public class ListPlayersCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("Players are: ");
        for (OfflinePlayer i : BuoyyEcon.getPlayers()) {
            sender.sendMessage('\t'+Objects.requireNonNull(i.getName()));
        }
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        return null;
    }
}
