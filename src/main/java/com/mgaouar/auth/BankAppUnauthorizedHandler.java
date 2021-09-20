package com.mgaouar.auth;

import io.dropwizard.auth.UnauthorizedHandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BankAppUnauthorizedHandler implements UnauthorizedHandler {

    public static final String UNAUTHORIZED_UNAUTHENTICATED_MESSAGE =
            "Credentials are required to access this resource. You are unauthorized to access this resource";

    @Override
    public Response buildResponse(String prefix, String realm) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(UNAUTHORIZED_UNAUTHENTICATED_MESSAGE)
                .build();
    }
}