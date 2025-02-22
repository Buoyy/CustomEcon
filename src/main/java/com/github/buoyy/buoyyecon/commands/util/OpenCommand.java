package com.github.buoyy.buoyyecon.commands.util;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.commands.SubCommand;
import com.github.buoyy.buoyyecon.gui.impl.StorageGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class OpenCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This is a player command only");
            return true;
        }
        BuoyyEcon.getGUIManager()
                .openGUI(player, new StorageGUI(player));
        return true;
    }

    @Override
    public List<String> getCompletions(String[] args) {
        return List.of();
    }
}
