package com.github.buoyy.buoyyecon.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BalanceChangeEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final OfflinePlayer player;
    private final int amount;

    public BalanceChangeEvent(OfflinePlayer player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public OfflinePlayer getPlayer() { return player; }
    public int getAmount() { return amount; }

    public static HandlerList getHandlerList() { return HANDLER_LIST; }
    @Override public @NotNull HandlerList getHandlers() { return HANDLER_LIST; }
}
