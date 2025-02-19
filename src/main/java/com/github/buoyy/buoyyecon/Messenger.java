package com.github.buoyy.buoyyecon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Messenger {
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    public void consoleOK(String message) {
        console.sendMessage(ChatColor.AQUA + "[FINE]: "+message);
    }
    public void consoleGood(String message) {
        console.sendMessage(ChatColor.GREEN + "[GOOD]: "+message);
    }
    public void consoleBad(String message) {
        console.sendMessage(ChatColor.RED + "[BAD]: " +message);
    }
}
