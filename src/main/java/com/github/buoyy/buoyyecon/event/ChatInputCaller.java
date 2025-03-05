package com.github.buoyy.buoyyecon.event;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.input.ChatInputManager;
import com.github.buoyy.buoyyecon.input.InputType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatInputCaller implements Listener {
    private final ChatInputManager manager = BuoyyEcon.getChatInputManager();
    private String input;
    private OfflinePlayer target;
    private InputType inputType;
    private CurrencyType currencyType;

    @EventHandler
    public void onPlayerInput(ChatInputStartEvent e) {
        manager.addPlayerWithType(e.getSender(), e.getInputType());
        e.getSender().sendMessage(ChatColor.GREEN+"Enter amount: ");
        target = e.getTarget();
        inputType = e.getInputType();
        currencyType = e.getCurrencyType();
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (manager.isPlayerWaiting(e.getPlayer())) {
            e.setCancelled(true);
            input = e.getMessage();
            Bukkit.getScheduler().runTask(BuoyyEcon.getInstance(), ()->Bukkit.getPluginManager()
                    .callEvent(new ChatInputProcEvent(e.getPlayer(), target, currencyType, inputType, input)));
            manager.removePlayer(e.getPlayer());
        }
    }
}
