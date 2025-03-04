package com.github.buoyy.buoyyecon.commands.util;

import com.github.buoyy.api.command.SubCommand;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.input.ChatInputManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InputCommand implements SubCommand {
    private final ChatInputManager manager = BuoyyEcon.getChatInputManager();
    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Not for console");
            return true;
        }
        manager.addPlayerWithType(player, null);
        player.sendMessage("Enter: ");
        return true;
    }

    @Override
    public List<String> getCompletions(String[] strings) {
        return List.of();
    }
}
