package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import com.github.buoyy.buoyyecon.event.ChatInputStartEvent;
import com.github.buoyy.buoyyecon.input.InputType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerActionsGUI extends EconInvGUI{
    private final OfflinePlayer target;
    public PlayerActionsGUI(OfflinePlayer owningPlayer, OfflinePlayer target, CurrencyType type) {
        super(owningPlayer, type);
        this.target = target;
        this.inv = Bukkit.createInventory(null, 9, "Actions for "+target.getName());
    }

    @Override
    public void decorate() {
        InvButton payTarget = InvButton.Builder.newBuilder()
                .setIcon(Material.GREEN_STAINED_GLASS_PANE)
                .setName("Pay "+target.getName())
                .setOnClick(e->{
                    Bukkit.getPluginManager()
                            .callEvent(new ChatInputStartEvent((Player) this.owningPlayer, this.target,
                                    this.type, InputType.PAY));
                    ((Player)this.owningPlayer).closeInventory();
                })
                .build();
        InvButton payAllTarget = InvButton.Builder.newBuilder()
                .setIcon(Material.ORANGE_STAINED_GLASS_PANE)
                .setName("Pay request amount to "+target.getName())
                .setOnClick(e->{
                    ((Player)this.owningPlayer).performCommand(type.getNameSingular()+" pay "
                    + target.getName()+" "+BuoyyEcon.getEconomy()
                            .getRequest(target, this.owningPlayer, type).amount);
                    ((Player)this.owningPlayer).closeInventory();
                })
                .build();
        InvButton reqTarget = InvButton.Builder.newBuilder()
                .setIcon(Material.YELLOW_STAINED_GLASS_PANE)
                .setName("Request "+target.getName())
                .setOnClick(e->{
                    Bukkit.getPluginManager()
                            .callEvent(new ChatInputStartEvent((Player) this.owningPlayer, this.target,
                                    this.type, InputType.REQUEST));
                    ((Player)this.owningPlayer).closeInventory();
                })
                .build();
        this.addButton(3, payTarget);
        this.addButton(4, payAllTarget);
        this.addButton(5, reqTarget);
        super.decorate();
    }
}
