package com.github.buoyy.buoyyecon.event;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.input.ChatInputManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatInputCaller implements Listener {
    private final ChatInputManager manager = BuoyyEcon.getChatInputManager();
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (manager.isPlayerWaiting(player)) {
            e.setCancelled(true);
            Bukkit.getScheduler().runTask(BuoyyEcon.getInstance(),
                    ()->Bukkit.getPluginManager()
                            .callEvent(new ChatInputEvent(player, e.getMessage(), manager.getInputType(player))));
        }
        manager.removePlayer(player);
    }
}
