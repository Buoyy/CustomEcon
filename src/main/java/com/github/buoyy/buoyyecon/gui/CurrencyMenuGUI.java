package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InvButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
        InvButton yourHead = InvButton.Builder.newBuilder()
                .setIcon(getPlayerHead(Bukkit.getOfflinePlayer(this.owningPlayer.getUniqueId())))
                .setName("Your management")
                .build();
        this.addButton(5, openStorage);
        this.addButton(3, yourHead);
        super.decorate();
    }
    ItemStack getPlayerHead(OfflinePlayer player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null)
            meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        return head;
    }
}
