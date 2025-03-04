package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.api.economy.CurrencyType;
import com.github.buoyy.api.gui.InventoryGUI;
import org.bukkit.OfflinePlayer;

public class EconInvGUI extends InventoryGUI {
    protected final OfflinePlayer owningPlayer;
    protected final CurrencyType type;
    public EconInvGUI(OfflinePlayer owningPlayer, CurrencyType type) {
        this.owningPlayer = owningPlayer;
        this.type = type;
    }
}
