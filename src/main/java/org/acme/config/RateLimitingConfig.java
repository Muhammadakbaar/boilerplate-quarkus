package org.acme.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.redis.client.impl.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Provider
@ApplicationScoped
@RegisterForReflection
public class RateLimitingConfig implements ContainerRequestFilter {

    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final Cache<String, AtomicInteger> requestCounts = Caffeine.newBuilder()
            .expireAfterWrite(WINDOW)
            .build();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String clientIp = requestContext.getHeaderString("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = requestContext.getUriInfo().getRequestUri().getHost();
        }

        AtomicInteger counter = requestCounts.get(clientIp, k -> new AtomicInteger(0));
        int currentCount = counter.incrementAndGet();

        if (currentCount > MAX_REQUESTS) {
            requestContext.abortWith(
                    Response.status(429)
                            .entity("Too many requests. Please try again later.")
                            .build()
            );
        }
    }
}