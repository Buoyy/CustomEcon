package com.github.buoyy.buoyyecon.commands;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class OpenCommand implements SubCommand{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This is a player command only");
            return true;
        }
        player.openInventory(BuoyyEcon.getEconomy().getStorage(player));
        return true;
    }

    @Override
    public List<String> getTabs(String[] args) {
        return List.of();
    }
}
