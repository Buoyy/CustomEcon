package com.github.buoyy.buoyyecon.event;

import com.github.buoyy.buoyyecon.input.InputType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ChatInputEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String input;
    private final InputType type;

    public ChatInputEvent(Player player, String input, InputType type) {
        this.player = player;
        this.input = input;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public String getInput() {
        return input;
    }

    public InputType getType() {
        return type;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
