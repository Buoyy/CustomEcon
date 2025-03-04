package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class MainMenuGUI extends EconInvGUI {
    public MainMenuGUI(OfflinePlayer owningPlayer) {
        super(owningPlayer, null);
        this.inv = Bukkit.createInventory(null, 9, ChatColor.GOLD+"Economy");
    }
    @Override
    public void decorate() {
        InvButton goldEcon = InvButton.Builder.newBuilder()
                .setIcon(Material.GOLD_BLOCK)
                .setName("Open Gold Menu")
                .setLore("Balance: "+BuoyyEcon.getEconomy().prettyBal(this.owningPlayer, CurrencyType.GOLD))
                .setOnClick(e->
                        BuoyyEcon.getGUIManager()
                                .openGUI((Player)this.owningPlayer,
                                        new CurrencyMenuGUI(this.owningPlayer, CurrencyType.GOLD)))
                .build();
        InvButton diaEcon = InvButton.Builder.newBuilder()
                .setIcon(Material.DIAMOND_BLOCK)
                .setName("Open Diamond Menu")
                .setLore("Balance: "+BuoyyEcon.getEconomy().prettyBal(this.owningPlayer, CurrencyType.DIAMOND))
                .setOnClick(e->
                        BuoyyEcon.getGUIManager()
                                .openGUI((Player)this.owningPlayer,
                                        new CurrencyMenuGUI(this.owningPlayer, CurrencyType.DIAMOND)))
                .build();
        this.addButton(3, goldEcon);
        this.addButton(5, diaEcon);
        super.decorate();
    }
}
