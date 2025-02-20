package com.github.buoyy.buoyyecon.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AccountCreationEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;

    public AccountCreationEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() { return player; }
    public static HandlerList getHandlerList() { return HANDLER_LIST; }
    @Override public @NotNull HandlerList getHandlers() { return HANDLER_LIST; }
}
