package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

public class ListPlayersCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("Players are: ");
        BuoyyEcon.getPlayers()
                .forEach(p -> sender.sendMessage(Objects.requireNonNull(p.getName())));
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        return null;
    }
}
