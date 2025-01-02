package org.acme.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dto.response.auth.UserAuthResponse;
import org.acme.entity.User;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class UserMapper {

    public UserAuthResponse toUserAuthResponse(User user) {
        if (user == null) {
            return null;
        }

        UserAuthResponse response = new UserAuthResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRoles(new HashSet<>(user.getRoles()));

        return response;
    }

    public User toEntity(String email, String hashedPassword) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        // Set default role as "user"
        user.setRoles(new HashSet<>());
        user.getRoles().add("user");

        return user;
    }

    public void updateUser(User user, String email, String hashedPassword) {
        user.setEmail(email);
        user.setPassword(hashedPassword);
    }

    public void addRole(User user, String role) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);
    }

    public void removeRole(User user, String role) {
        if (user.getRoles() != null) {
            user.getRoles().remove(role);
        }
    }

    public boolean hasRole(User user, String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
