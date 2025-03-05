package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.buoyyecon.event.ChatInputProcEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatInputListener implements Listener {
    @EventHandler
    public void onPlayerInput(ChatInputProcEvent e) {
        Player player = e.getSender();
        OfflinePlayer target = e.getTarget();
        String currencyName = e.getCurrencyType().getNameSingular();
        String input = e.getInput();
        if (player == target) {
            player.sendMessage("Can't use action on self!");
        }
        switch (e.getInputType()) {
            case PAY -> player.performCommand(currencyName+" pay "+target.getName()+" "+input);
            case REQUEST -> player.performCommand(currencyName+" request "+target.getName()+" "+input);
        }
    }
}
