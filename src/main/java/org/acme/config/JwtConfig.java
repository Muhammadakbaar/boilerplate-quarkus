package org.acme.config;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.time.Duration;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class JwtConfig {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateToken(String username, Set<String> roles) {
        return Jwt.issuer(issuer)
                .subject(username)
                .groups(roles)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}
