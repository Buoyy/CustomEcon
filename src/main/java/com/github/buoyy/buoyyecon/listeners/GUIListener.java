package com.github.buoyy.buoyyecon.listeners;

import com.github.buoyy.api.gui.GUIManager;
import com.github.buoyy.buoyyecon.BuoyyEcon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListener implements Listener {
    private final GUIManager manager = BuoyyEcon.getGUIManager();
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        manager.handleClick(e);
    }
    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        manager.handleOpen(e);
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        manager.handleClose(e);
    }
}
