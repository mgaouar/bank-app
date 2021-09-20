package com.mgaouar.models;

public class Account {
    private String accountNb;
    private int balance;

    public Account(String accountNb, int amount) {
        this.accountNb = accountNb;
        this.balance = amount;
    }

    public String getAccountNb() {
        return accountNb;
    }

    public int getBalance() {
        return balance;
    }

    public int withdraw(int amount) {
        if (amount > balance) {
            return -1;
        }
        return balance = balance - amount;
    }

    public int deposit(int amount) {
        balance += amount;
        return balance;
    }
}
