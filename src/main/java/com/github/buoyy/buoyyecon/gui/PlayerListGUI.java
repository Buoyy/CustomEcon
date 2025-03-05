package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.economy.Economy;
import com.github.buoyy.api.economy.PaymentRequest;
import com.github.buoyy.api.gui.InvButton;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerListGUI extends EconInvGUI {
    private final Economy econ;
    public PlayerListGUI(OfflinePlayer owningPlayer, CurrencyType type) {
        super(owningPlayer, type);
        econ = BuoyyEcon.getEconomy();
        this.inv = Bukkit.createInventory(null, 27, "All players");
    }

    @Override
    public void decorate() {
        int i = 0;
        for (OfflinePlayer target: Bukkit.getOfflinePlayers()) {
            if (owningPlayer == target) continue;
            PaymentRequest reqFromOwner = econ.getRequest(owningPlayer, target, type);
            PaymentRequest reqFromTarget = econ.getRequest(target, owningPlayer, type);
            InvButton playerHead = InvButton.Builder.newBuilder()
                    .setIcon(getPlayerHead(target))
                    .setLore("You owe "+econ.format((reqFromTarget != null ? reqFromTarget.amount : 0), type),
                            "They owe "+econ.format((reqFromOwner != null ? reqFromOwner.amount : 0), type))
                    .setOnClick(e->
                            BuoyyEcon.getGUIManager()
                                    .openGUI((Player) this.owningPlayer, new PlayerActionsGUI(this.owningPlayer,
                                            target, this.type)))
                    .setName(target.getName())
                    .build();
            this.addButton(i, playerHead);
            ++i;
        }
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
