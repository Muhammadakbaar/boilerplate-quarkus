package org.acme.service;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AuthResponseDTO;
import org.acme.dto.request.auth.LoginRequestDTO;
import org.acme.dto.request.auth.RegisterRequestDTO;
import org.acme.dto.response.auth.ApiResponse;
import org.acme.dto.response.auth.UserAuthResponse;
import org.acme.entity.User;
import org.acme.exception.CustomException;
import org.acme.mapper.UserMapper;
import org.acme.repository.UserRepository;
import org.acme.security.JwtService;
import org.acme.util.PasswordUtil;
import org.jboss.logging.Logger;

import java.util.Collections;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtService jwtService;

    @Inject
    UserMapper userMapper;

    @WithTransaction
    public Uni<AuthResponseDTO> register(RegisterRequestDTO request) {
        return userRepository.existsByEmail(request.getEmail())
                .chain(exists -> {
                    if (exists) {
                        return Uni.createFrom().failure(
                                new CustomException("Email already registered", Response.Status.CONFLICT)
                        );
                    }

                    String hashedPassword = PasswordUtil.hashPassword(request.getPassword());
                    User user = userMapper.toEntity(request.getEmail(), hashedPassword);

                    return userRepository.persist(user)
                            .map(savedUser -> {
                                AuthResponseDTO response = new AuthResponseDTO();
                                response.setToken(jwtService.generateToken(savedUser.getEmail(), savedUser.getRoles()));
                                response.setUser(userMapper.toUserAuthResponse(savedUser));
                                return response;
                            });
                });
    }

    @WithTransaction
    public Uni<AuthResponseDTO> login(LoginRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .chain(user -> {
                    if (user == null) {
                        return Uni.createFrom().failure(
                                new CustomException("Invalid credentials", Response.Status.UNAUTHORIZED)
                        );
                    }

                    if (!PasswordUtil.verifyPassword(request.getPassword(), user.getPassword())) {
                        return Uni.createFrom().failure(
                                new CustomException("Invalid credentials", Response.Status.UNAUTHORIZED)
                        );
                    }

                    AuthResponseDTO response = new AuthResponseDTO();
                    response.setToken(jwtService.generateToken(user.getEmail(), user.getRoles()));
                    response.setUser(userMapper.toUserAuthResponse(user));
                    return Uni.createFrom().item(response);
                });
    }
    public Uni<AuthResponseDTO> getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (user == null) {
                        throw new CustomException("User not found", Response.Status.NOT_FOUND);
                    }
                    AuthResponseDTO response = new AuthResponseDTO();
                    response.setToken(jwtService.generateToken(user.getEmail(), user.getRoles()));
                    response.setUser(userMapper.toUserAuthResponse(user));
                    return response;
                });
    }

    public Uni<Boolean> logout(String email) {
        // Implement token blacklisting if needed
        return Uni.createFrom().item(true);
    }

    public Uni<Boolean> addRole(String email, String role) {
        return userRepository.findByEmail(email)
                .chain(user -> {
                    if (user == null) {
                        return Uni.createFrom().failure(
                                new CustomException("User not found", Response.Status.NOT_FOUND)
                        );
                    }
                    userMapper.addRole(user, role);
                    return userRepository.persist(user)
                            .map(updatedUser -> true);
                });
    }

    public Uni<Set<String>> getUserRoles(String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (user == null) {
                        throw new CustomException("User not found", Response.Status.NOT_FOUND);
                    }
                    return user.getRoles();
                });
    }

    public Uni<Boolean> removeRole(String email, String role) {
        return userRepository.findByEmail(email)
                .chain(user -> {
                    if (user == null) {
                        return Uni.createFrom().failure(
                                new CustomException("User not found", Response.Status.NOT_FOUND)
                        );
                    }
                    userMapper.removeRole(user, role);
                    return userRepository.persist(user)
                            .map(updatedUser -> true);
                });
    }

    public Uni<AuthResponseDTO> refreshToken(String email) {
        return getCurrentUser(email);
    }

}