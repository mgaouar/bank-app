package com.mgaouar.resources;

import com.mgaouar.models.AccountOperation;
import com.mgaouar.services.AccountManagementService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private AccountManagementService service;

    public AccountResource(AccountManagementService accountManagementService) {
        this.service = accountManagementService;
    }

    @GET
    @Path("/{accountId}")
    @PermitAll
    public Response getAccountBalance(@PathParam("accountId") String accountId) {
        int balance = service.getBankAccountBalance(accountId);
        return Response.ok(balance).build();
    }

    @PUT
    @Path("/{accountId}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@PathParam("accountId") String accountId, AccountOperation accountOperation) {
        int balance = service.processAccountUpdate(accountId, accountOperation);
        if (balance == -1) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Operation Not authorized").build();
        }
        return Response.ok(balance).build();
    }
}
