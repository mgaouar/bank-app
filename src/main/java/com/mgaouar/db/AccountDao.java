package com.mgaouar.db;

import com.mgaouar.models.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountDao {

    private Map<String, Account> accounts;

    public AccountDao() {
        accounts = new HashMap<>();
        Account newAccount = new Account("1234", 100);
        accounts.put("1234", newAccount);
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNb(), account);
    }

    public Account getAccount(String accountNb) {
        return accounts.getOrDefault(accountNb, null);
    }

    public void deleteAccount(String accountNb) {
        accounts.remove(accountNb);
    }


}
