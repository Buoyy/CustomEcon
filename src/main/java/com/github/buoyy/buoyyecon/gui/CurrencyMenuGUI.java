package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CurrencyMenuGUI extends EconInvGUI {
    public CurrencyMenuGUI(OfflinePlayer owningPlayer, CurrencyType type) {
        super(owningPlayer, type);
        this.inv = Bukkit.createInventory(null, 9, ChatColor.GREEN+type.getNamePlural()+" Menu");
    }
    @Override
    public void decorate() {
        InvButton openStorage = InvButton.Builder.newBuilder()
                .setIcon(type == CurrencyType.DIAMOND ? Material.DIAMOND_BLOCK : Material.GOLD_BLOCK)
                .setName("Open "+type.getNamePlural()+" storage")
                .setOnClick(e->
                        ((Player)owningPlayer).performCommand(type.getNameSingular()+" open"))
                .build();
        InvButton paymentMenu = InvButton.Builder.newBuilder()
                .setIcon(Material.WRITTEN_BOOK)
                .setName("Open Players' List")
                .setLore("Click here to open a menu", "of players to pay/request money.")
                .setOnClick(e->
                        BuoyyEcon.getGUIManager()
                                .openGUI((Player) this.owningPlayer,
                                        new PlayerListGUI(this.owningPlayer, type)))
                .build();
        this.addButton(5, openStorage);
        this.addButton(3, paymentMenu);
        super.decorate();
    }

}
