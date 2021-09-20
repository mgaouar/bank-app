package com.mgaouar.auth;

import com.google.common.io.BaseEncoding;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.eclipse.jetty.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public class BankAppBasicAuthFilter<P extends Principal> extends AuthFilter<BasicCredentials, P> {

    private static final Logger logger = LoggerFactory.getLogger(BankAppBasicAuthFilter.class);

    private BankAppBasicAuthFilter() {
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        /**
         * Allow OPTIONS requests to get through since they have no cookies.
         */
        if (HttpMethod.OPTIONS.asString().equalsIgnoreCase(containerRequestContext.getMethod())) {
            logger.debug("We received an OPTIONS request, allowing the request through! {}", containerRequestContext.getUriInfo().getPath());
            return;
        }


        final BasicCredentials credentials =
                getCredentials(containerRequestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        if (!authenticate(containerRequestContext, credentials, SecurityContext.BASIC_AUTH)) {
            throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
        }
    }

    /**
     * Parses a Base64-encoded value of the `Authorization` header
     * in the form of `Basic dXNlcm5hbWU6cGFzc3dvcmQ=`.
     *
     * @param header the value of the `Authorization` header
     * @return a username and a password as {@link BasicCredentials}
     */
    @Nullable
    private BasicCredentials getCredentials(String header) {
        if (header == null) {
            return null;
        }

        final int space = header.indexOf(' ');
        if (space <= 0) {
            return null;
        }

        final String method = header.substring(0, space);
        if (!prefix.equalsIgnoreCase(method)) {
            return null;
        }

        final String decoded;
        try {
            decoded = new String(BaseEncoding.base64().decode(header.substring(space + 1)), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            logger.warn("Error decoding credentials", e);
            return null;
        }

        // Decoded credentials is 'username:password'
        final int i = decoded.indexOf(':');
        if (i <= 0) {
            return null;
        }

        final String username = decoded.substring(0, i);
        final String password = decoded.substring(i + 1);
        return new BasicCredentials(username, password);
    }

    @Override
    protected boolean authenticate(ContainerRequestContext requestContext, BasicCredentials credentials, String scheme) {
        try {
            final Optional<P> optionalPrincipal = authenticator.authenticate(credentials);
            if (!optionalPrincipal.isPresent()) {
                return false;
            }

            final SecurityContext securityContext = requestContext.getSecurityContext();
            BankAppSecurityContext pdaSecurityContext = new BankAppSecurityContext(optionalPrincipal.get(), securityContext, authorizer);
            requestContext.setSecurityContext(pdaSecurityContext);

            return true;
        } catch (AuthenticationException e) {
            logger.error("Error authenticating credentials", e);
            throw new InternalServerErrorException();
        }

    }

    /**
     * Builder for {@link BankAppBasicAuthFilter}.
     * <p>An {@link Authenticator} must be provided during the building process.</p>
     *
     * @param <P> the principal
     */
    public static class Builder<P extends Principal> extends
            AuthFilterBuilder<BasicCredentials, P, BankAppBasicAuthFilter<P>> {

        @Override
        protected BankAppBasicAuthFilter<P> newInstance() {
            return new BankAppBasicAuthFilter<>();
        }
    }
}