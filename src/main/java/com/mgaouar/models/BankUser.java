package com.mgaouar.models;

import java.security.Principal;

public class BankUser implements Principal {

    private String bankAccount;

    private String pin;

    public BankUser(String bankAccount, String pin) {
        this.bankAccount = bankAccount;
        this.pin = pin;
    }

    @Override
    public String getName() {
        return bankAccount;
    }

    public String getPin() {
        return pin;
    }
}
