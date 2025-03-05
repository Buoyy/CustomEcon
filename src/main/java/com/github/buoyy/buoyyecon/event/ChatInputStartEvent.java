package com.github.buoyy.buoyyecon.event;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.buoyyecon.input.InputType;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ChatInputStartEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player sender;
    private final OfflinePlayer target;
    private final CurrencyType currencyType;
    private final InputType inputType;

    public ChatInputStartEvent(Player sender, OfflinePlayer target, CurrencyType currencyType, InputType inputType) {
        this.sender = sender;
        this.target = target;
        this.currencyType = currencyType;
        this.inputType = inputType;
    }

    public Player getSender() {
        return sender;
    }

    public OfflinePlayer getTarget() {
        return target;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public InputType getInputType() {
        return inputType;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
