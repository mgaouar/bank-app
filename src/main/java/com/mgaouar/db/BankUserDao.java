package com.mgaouar.db;

import com.mgaouar.models.BankUser;

import java.util.HashMap;
import java.util.Map;

public class BankUserDao {

    private Map<String, BankUser> users;

    private BankUser user;

    public BankUserDao() {
        users = new HashMap<>();
        user = new BankUser("test", "1234");
        users.put("test", user);
    }

    public BankUser getUser(String account) {
        return users.getOrDefault(account, null);
    }
}
