package org.acme.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class JwtService {

    public String generateToken(String username, Set<String> roles) {
        return Jwt.issuer("https://example.com")
                .subject(username)
                .groups(roles)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}
