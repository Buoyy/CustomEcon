package com.github.buoyy.buoyyecon.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface InventoryHandler {
    void onClick(InventoryClickEvent e);
    void onOpen(InventoryOpenEvent e);
    void onClose(InventoryCloseEvent e);
}
