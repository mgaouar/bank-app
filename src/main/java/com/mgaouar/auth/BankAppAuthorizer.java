package com.mgaouar.auth;

import com.mgaouar.models.BankUser;
import io.dropwizard.auth.Authorizer;

import javax.annotation.Nullable;
import javax.ws.rs.container.ContainerRequestContext;

public class BankAppAuthorizer implements Authorizer<BankUser> {

    @Override
    public boolean authorize(BankUser bankUser, String s) {
        return true;
    }

    @Override
    public boolean authorize(BankUser principal, String role, @Nullable ContainerRequestContext requestContext) {
        return true;
    }
}