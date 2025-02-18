package com.github.buoyy.buoyyecon.economy;

// Type info: 0 for failure, 1 for success, -1 for no implementation
public class EconomyAction {
    private final float amount, balance;
    private final boolean success;
    private final String message;

    public float getBalance() {
        return balance;
    }

    public float getAmount() {
        return amount;
    }

    public boolean isSuccessful() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public EconomyAction(float amount, float balance, boolean success, String message) {
        this.amount = amount;
        this.balance = balance;
        this.success = success;
        this.message = message;
    }
}
