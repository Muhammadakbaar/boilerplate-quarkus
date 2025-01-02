package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.User;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Uni<User> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Uni<Boolean> existsByEmail(String email) {
        return find("email", email).count()
                .map(count -> count > 0);
    }
}
