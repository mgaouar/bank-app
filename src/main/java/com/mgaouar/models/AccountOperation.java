package com.mgaouar.models;

public class AccountOperation {

    public enum AccountOperationType {
        WITHDRAW,
        DEPOSIT
    }

    private AccountOperationType operationType;

    private int amount;

    public AccountOperation() {
    }

    public AccountOperation(AccountOperationType operationType, int amount) {
        this.operationType = operationType;
        this.amount = amount;
    }

    public AccountOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(AccountOperationType operationType) {
        this.operationType = operationType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
