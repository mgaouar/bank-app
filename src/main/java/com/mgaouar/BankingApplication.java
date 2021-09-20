package com.mgaouar;

import com.mgaouar.auth.BankAppAuthenticator;
import com.mgaouar.auth.BankAppAuthorizer;
import com.mgaouar.auth.BankAppBasicAuthFilter;
import com.mgaouar.auth.BankAppUnauthorizedHandler;
import com.mgaouar.db.AccountDao;
import com.mgaouar.db.BankUserDao;
import com.mgaouar.models.BankUser;
import com.mgaouar.resources.AccountResource;
import com.mgaouar.services.AccountManagementService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.session.SessionHandler;

import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

public class BankingApplication extends Application<BankingApplicationConfiguration> {

    public static final String PDA_REALM = "BankApp Realm";

    public static void main(final String[] args) throws Exception {
        new BankingApplication().run(args);
    }

    @Override
    public String getName() {
        return "Banking Application";
    }

    @Override
    public void initialize(final Bootstrap<BankingApplicationConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BankingApplicationConfiguration configuration,
                    final Environment environment) {

        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setMaxInactiveInterval(5);
        sessionHandler.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));

        environment.servlets().setSessionHandler(sessionHandler);

        BankUserDao bankUserDao = new BankUserDao();
        AccountDao accountDao = new AccountDao();

        BankAppAuthenticator authenticator = new BankAppAuthenticator(bankUserDao);
        environment.jersey().register(new AuthDynamicFeature(
                new BankAppBasicAuthFilter.Builder<BankUser>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(new BankAppAuthorizer())
                        .setUnauthorizedHandler(new BankAppUnauthorizedHandler())
                        .setRealm(PDA_REALM)
                        .buildAuthFilter()
        ));


        AccountManagementService accountManagementService = new AccountManagementService(bankUserDao, accountDao);

        AccountResource accountResource = new AccountResource(accountManagementService);

        environment.jersey().register(accountResource);

    }

}
