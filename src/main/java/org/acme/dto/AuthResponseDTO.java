package org.acme.dto;

import org.acme.dto.response.auth.UserAuthResponse;

/**
 * Created by IntelliJ IDEA.
 * Project : code-with-quarkus
 * User: Muhammad Akbar
 * GitHub: muhammadakbaar
 * Date: 1/2/25
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthResponseDTO {
    private String token;
    private UserAuthResponse user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAuthResponse getUser() {
        return user;
    }

    public void setUser(UserAuthResponse user) {
        this.user = user;
    }
}
