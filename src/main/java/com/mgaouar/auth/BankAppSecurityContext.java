package com.mgaouar.auth;

import io.dropwizard.auth.Authorizer;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class BankAppSecurityContext<P extends Principal> implements SecurityContext {

    private P principal;
    private SecurityContext securityContext;
    private Authorizer<P> authorizer;

    public BankAppSecurityContext(P principal, SecurityContext securityContext, Authorizer<P> authorizer) {
        this.principal = principal;
        this.securityContext = securityContext;
        this.authorizer = authorizer;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return authorizer.authorize(principal, role);
    }

    @Override
    public boolean isSecure() {
        return securityContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}