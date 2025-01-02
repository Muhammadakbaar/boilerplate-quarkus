package org.acme.resource;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/api/secure")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
public class SecureResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/user")
    @RolesAllowed("user")
    public String userEndpoint() {
        return "Access granted for user: " + jwt.getSubject();
    }

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    public String adminEndpoint() {
        return "Access granted for admin: " + jwt.getSubject();
    }
}