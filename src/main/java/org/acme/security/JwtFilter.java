package org.acme.security;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Provider
@ApplicationScoped
public class JwtFilter implements ContainerRequestFilter {

    @Context
    RoutingContext routingContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("/api/auth")) {
        }
    }
}
