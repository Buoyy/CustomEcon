package com.github.buoyy.buoyyecon.economy;

// Type info: 0 for failure, 1 for success, -1 for no implementation

public class Transaction {
    public final String amount;
    private final boolean success;
    public final String message;

    public Transaction(int amount, boolean success, String message) {
        this.amount = String.valueOf(amount);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccessful() {
        return success;
    }
}
