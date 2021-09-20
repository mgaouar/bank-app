package com.mgaouar.services;

import com.mgaouar.db.AccountDao;
import com.mgaouar.db.BankUserDao;
import com.mgaouar.models.Account;
import com.mgaouar.models.AccountOperation;

public class AccountManagementService {

    private BankUserDao bankUserDao;
    private AccountDao accountDao;

    public AccountManagementService(BankUserDao bankUserDao, AccountDao accountDao) {
        this.bankUserDao = bankUserDao;
        this.accountDao = accountDao;
    }

    public int getBankAccountBalance(String accountId) {
        Account account = accountDao.getAccount(accountId);
        return account.getBalance();
    }

    public int processAccountUpdate(String accountId, AccountOperation accountOperation) {
        Account account = accountDao.getAccount(accountId);
        if (account == null) {
            return -1;
        }
        if (accountOperation.getOperationType() == AccountOperation.AccountOperationType.DEPOSIT) {
            return account.deposit(accountOperation.getAmount());
        } else if (accountOperation.getOperationType() == AccountOperation.AccountOperationType.WITHDRAW) {
            return account.withdraw(accountOperation.getAmount());
        }
        return 0;
    }
}
