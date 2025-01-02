package org.acme.resource;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.AuthResponseDTO;
import org.acme.dto.request.auth.LoginRequestDTO;
import org.acme.dto.request.auth.RegisterRequestDTO;
import org.acme.dto.response.auth.ApiResponse;
import org.acme.service.AuthService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/register")
    @PermitAll
    public Uni<AuthResponseDTO> register(@Valid RegisterRequestDTO request) {
        return authService.register(request);
    }

    @POST
    @Path("/login")
    @PermitAll
    public Uni<AuthResponseDTO> login(@Valid LoginRequestDTO request) {
        return authService.login(request);
    }

    @GET
    @Path("/me")
    @Authenticated
    public Uni<AuthResponseDTO> getCurrentUser() {
        return authService.getCurrentUser(jwt.getSubject());
    }

    @POST
    @Path("/logout")
    @Authenticated
    public Uni<ApiResponse> logout() {
        return authService.logout(jwt.getSubject())
                .map(success -> new ApiResponse(true, "Logged out successfully"));
    }

    @GET
    @Path("/validate")
    @Authenticated
    public Uni<ApiResponse> validateToken() {
        return Uni.createFrom().item(
                new ApiResponse(true, "Token is valid")
        );
    }

    @POST
    @Path("/role")
    @RolesAllowed("admin")
    public Uni<ApiResponse> addRole(@QueryParam("email") String email, @QueryParam("role") String role) {
        return authService.addRole(email, role)
                .map(success -> new ApiResponse(true, "Role added successfully"));
    }

    @GET
    @Path("/roles")
    @Authenticated
    public Uni<Set<String>> getUserRoles() {
        return authService.getUserRoles(jwt.getSubject());
    }

    @DELETE
    @Path("/role")
    @RolesAllowed("admin")
    public Uni<ApiResponse> removeRole(@QueryParam("email") String email, @QueryParam("role") String role) {
        return authService.removeRole(email, role)
                .map(success -> new ApiResponse(true, "Role removed successfully"));
    }

    @POST
    @Path("/refresh")
    @Authenticated
    public Uni<AuthResponseDTO> refreshToken() {
        return authService.refreshToken(jwt.getSubject());
    }
}
