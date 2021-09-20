package com.mgaouar.auth;

import com.mgaouar.db.BankUserDao;
import com.mgaouar.models.BankUser;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

public class BankAppAuthenticator implements Authenticator<BasicCredentials, BankUser> {

    private BankUserDao dao;

    public BankAppAuthenticator(BankUserDao bankUserDao) {
        this.dao = bankUserDao;
    }

    @Override
    public Optional<BankUser> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if (basicCredentials == null || basicCredentials.getUsername() == null) {
            return Optional.empty();
        }
        BankUser user = dao.getUser(basicCredentials.getUsername());
        return user != null && user.getPin().equals(basicCredentials.getPassword()) ? Optional.of(user) : Optional.empty();
    }
}
