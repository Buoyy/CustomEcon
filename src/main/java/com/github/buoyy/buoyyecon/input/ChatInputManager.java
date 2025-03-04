package com.github.buoyy.buoyyecon.input;

import org.bukkit.entity.Player;

import java.util.*;

public class ChatInputManager {
    private final Map<UUID, InputType> waitingPlayers = new HashMap<>();
    public void addPlayerWithType(Player player, InputType type) {
        waitingPlayers.put(player.getUniqueId(), type);
    }
    public void removePlayer(Player player) {
        waitingPlayers.remove(player.getUniqueId());
    }
    public InputType getInputType(Player player) {
        return waitingPlayers.get(player.getUniqueId());
    }
    public boolean isPlayerWaiting(Player player) {
        return waitingPlayers.containsKey(player.getUniqueId());
    }
}
