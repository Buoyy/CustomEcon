package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class ManagementGUI extends EconInvGUI{
    public ManagementGUI(OfflinePlayer owningPlayer, CurrencyType type) {
        super(owningPlayer, type);
        this.inv = Bukkit.createInventory(null, 9, "Management");
    }

    @Override
    public void decorate() {
        super.decorate();
    }
}
