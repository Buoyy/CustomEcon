package com.github.buoyy.buoyyecon.gui;

import com.github.buoyy.buoyyecon.gui.impl.StorageGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GUIManager {
    private final Map<Inventory, InventoryHandler> storages = new HashMap<>();
    public void registerInv(Inventory inv, InventoryHandler handler) {
        this.storages.put(inv, handler);
    }
    public void unregisterInv(Inventory inv) {
        this.storages.remove(inv);
    }
    public void handleClick(InventoryClickEvent e) {
        InventoryHandler handler = this.storages.get(e.getInventory());
        if (handler != null) handler.onClick(e);
    }
    public void handleOpen(InventoryOpenEvent e) {
        InventoryHandler handler = this.storages.get(e.getInventory());
        if (handler != null) handler.onOpen(e);
    }
    public void handleClose(InventoryCloseEvent e) {
        InventoryHandler handler = this.storages.get(e.getInventory());
        if (handler != null) {
            handler.onClose(e);
            this.unregisterInv(e.getInventory());
        }
    }
    public void openGUI(Player player, StorageGUI handler) {
        this.registerInv(handler.getInv(), handler);
        player.openInventory(handler.getInv());
    }
    public void openGUI(Player player, InventoryGUI handler) {
        this.registerInv(handler.getInv(), handler);
        player.openInventory(handler.getInv());
    }
}
