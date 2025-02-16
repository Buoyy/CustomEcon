package com.github.buoyy.buoyyecon.economy;

import com.github.buoyy.buoyyecon.BuoyyEcon;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.OfflinePlayer;

import java.util.Map;
import java.util.UUID;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

public class EconHandler implements Economy {

    private final Map<UUID, Double> bal = new HashMap<>();

    @Override
    public boolean isEnabled() {
        return BuoyyEcon.getPlugin().isEnabled();
    }

    @Override
    public String getName() {
        return BuoyyEcon.getPlugin().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return (((int) amount) + " " + ((int) amount == 1 ? currencyNameSingular() : currencyNamePlural()));
    }

    @Override
    public String currencyNamePlural() {
        return "Coins";
    }

    @Override
    public String currencyNameSingular() {
        return "Coin";
    }
    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return bal.containsKey(player.getUniqueId());
    }
    @Override
    public double getBalance(OfflinePlayer player) {
        return bal.get(player.getUniqueId());
    }
    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return bal.get(player.getUniqueId()) >= amount;
    }
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (amount < 0)
            return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Negative amount");
        if (amount > getBalance(player))
            return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        bal.put(player.getUniqueId(), getBalance(player) - amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, "Successful operation");
    }
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (amount < 0)
            return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Negative amount");
        bal.put(player.getUniqueId(), getBalance(player) + amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, "Successful operation");
    }
    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if (!hasAccount(player)) {
            bal.put(player.getUniqueId(), 0.0);
            return true;
        }
        return false;
    }
    public String formattedBalance(OfflinePlayer player) {
        return format(getBalance(player));
    }
    public EconomyResponse setBalance(OfflinePlayer player, double amount) {
        if (amount < 0) {
            return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Negative amount");
        }
        bal.put(player.getUniqueId(), amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, "Successful operation");
    }
// ----------------------------- WORLD SPECIFIC METHODS: NO NEED! ---------------------------------------------------------

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }
    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }
    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }
    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }
    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }
// ----------------------------- DEPRECATED METHODS: DON'T USE! -----------------------------------------------------------

    @Deprecated @Override
    public boolean hasAccount(String playerName) {
        return false;
    }

    @Deprecated @Override
    public boolean hasAccount(String playerName, String worldName) {
        return false;
    }

    @Deprecated @Override
    public double getBalance(String playerName) {
        return 0;
    }

    @Deprecated @Override
    public double getBalance(String playerName, String world) {
        return 0;
    }

    @Deprecated @Override
    public boolean has(String playerName, double amount) {
        return false;
    }

    @Deprecated @Override
    public boolean has(String playerName, String worldName, double amount) {
        return false;
    }

    @Deprecated @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return null;
    }

    @Deprecated @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Deprecated @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Deprecated @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Deprecated @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Deprecated @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }
// ---------- BANK METHODS: NOT IMPLEMENTED!!

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not implemented");
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }



}
