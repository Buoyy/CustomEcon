package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.buoyyecon.event.ChatInputEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatInputListener implements Listener {
    @EventHandler
    public void onPlayerInput(ChatInputEvent e) {
        String input = e.getInput();
        Player player = e.getPlayer();
        if (input.equals("cancel")) {
            player.sendMessage("Your input was cancelled.");
        } else {
            player.sendMessage("Your input is: "+input);
        }
    }
}
